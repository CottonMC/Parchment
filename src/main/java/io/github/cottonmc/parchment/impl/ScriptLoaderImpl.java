package io.github.cottonmc.parchment.impl;

import java.util.function.UnaryOperator;

import javax.annotation.Nullable;
import javax.script.ScriptEngine;

import io.github.cottonmc.parchment.Parchment;
import io.github.cottonmc.parchment.api.Script;
import io.github.cottonmc.parchment.api.ScriptLoader;

import net.minecraft.util.Identifier;

public class ScriptLoaderImpl implements ScriptLoader {

	@Nullable
	@Override
	public Script loadScript(ScriptFactory factory, Identifier id, String contents) {
		String extension = id.getPath().substring(id.getPath().lastIndexOf('.'));
		ScriptEngine engine = Parchment.MANAGER.getEngineByExtension(extension);
		if (engine == null) throw new IllegalArgumentException("No script engine exists for extension '" + extension + "'");
		UnaryOperator<ScriptEngine> initializer = Parchment.INITIALIZERS.getOrDefault(engine.getFactory().getClass(), UnaryOperator.identity());
		engine = initializer.apply(Parchment.MANAGER.getEngineByExtension(extension));

		return factory.build(engine, id, contents);
	}
}
