package io.github.cottonmc.parchment.impl;


import javax.annotation.Nullable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

import io.github.cottonmc.parchment.Parchment;
import io.github.cottonmc.parchment.api.Script;
import io.github.cottonmc.parchment.api.ScriptEngineInitializer;
import io.github.cottonmc.parchment.api.ScriptLoader;

import net.minecraft.util.Identifier;

public class ScriptLoaderImpl implements ScriptLoader {

	@Nullable
	@Override
	public <T extends Script> T loadScript(ScriptFactory<T> factory, Identifier id, String contents) {
		String extension = id.getPath().substring(id.getPath().lastIndexOf('.') + 1);
		ScriptEngine engine = Parchment.MANAGER.getEngineByExtension(extension);
		if (engine == null) throw new IllegalArgumentException("No script engine exists for extension '" + extension
				+ "'");
		ScriptEngineInitializer initializer = Parchment.INITIALIZERS.getOrDefault(engine.getFactory().getClass(),
				NoOpEngineInitializer.INSTANCE);
		engine = initializer.initialize(engine);

		return factory.build(engine, id, contents);
	}

	private static class NoOpEngineInitializer implements ScriptEngineInitializer {
		private static final NoOpEngineInitializer INSTANCE = new NoOpEngineInitializer();

		@Override
		public Class<? extends ScriptEngineFactory> getEngineFactory() {
			return null;
		}

		@Override
		public ScriptEngine initialize(ScriptEngine engine) {
			return engine;
		}
	}
}
