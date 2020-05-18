package io.github.cottonmc.parchment.api;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.Logger;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;

/**
 * A resource reload listener which loads a collection of scripts.
 */
public abstract class ScriptDataLoader implements SimpleResourceReloadListener<Map<Identifier, Script>> {
	private ScriptLoader.ScriptFactory factory;
	private String dataType;

	/**
	 * @param factory The factory for assembling Script objects.
	 * @param dataType The subfolder to search in for scripts.
	 */
	public ScriptDataLoader(ScriptLoader.ScriptFactory factory, String dataType) {
		this.factory = factory;
		this.dataType = dataType;
	}

	/**
	 * Apply the loaded and prepared scripts, running them if necessary.
	 * @param scripts The scripts that were loaded.
	 * @param resourceManager The resource manager used for loading these scripts.
	 * @param profiler The profiler used for debugging this listener.
	 * @param executor The executor for running things on the main thread.
	 */
	@Override
	public abstract CompletableFuture<Void> apply(Map<Identifier, Script> scripts, ResourceManager resourceManager, Profiler profiler, Executor executor);

	/**
	 * @return The ID of this reload listener.
	 */
	@Override
	public abstract Identifier getFabricId();

	/**
	 * @return The logger used to print errors.
	 */
	abstract Logger getLogger();

	//doesn't throw so that it doesn't cause cascading failure
	@Override
	public CompletableFuture<Map<Identifier, Script>> load(ResourceManager resourceManager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			Map<Identifier, Script> scripts = new HashMap<>();
			int startIndex = dataType.length() + 1;
			Collection<Identifier> resources = resourceManager.findResources(dataType, name -> true);

			for (Identifier id : resources) {
				Identifier scriptId = new Identifier(id.getNamespace(), id.getPath().substring(startIndex));
				try {
					Resource resource = resourceManager.getResource(id);
					Script script = ScriptLoader.INSTANCE.loadScript(factory, scriptId, resource.getInputStream());
					Script oldScript = scripts.put(scriptId, script);
					if (oldScript != null) {
						getLogger().error("Duplicate script file ignored with ID {}", id.toString());
					}
				} catch (IOException | IllegalArgumentException e) {
					getLogger().error("Could not load script file {}: {}", id.toString(), e.getMessage());
				}
			}
			return scripts;
		});
	}
}
