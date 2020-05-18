package io.github.cottonmc.parchment;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import io.github.cottonmc.parchment.api.ScriptEngineInitializer;
import io.github.cottonmc.parchment.impl.ScriptClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parchment implements ModInitializer {
	public static final String MODID = "parchment";

	public static final Logger logger = LogManager.getLogger(MODID);

	public static final ScriptEngineManager MANAGER = new ScriptEngineManager(ScriptClassLoader.INSTANCE);

	public static final Map<Class<? extends ScriptEngineFactory>, UnaryOperator<ScriptEngine>> INITIALIZERS = new HashMap<>();

	@Override
	public void onInitialize() {
		FabricLoader.getInstance().getEntrypoints(MODID + ":engine_initializer", ScriptEngineInitializer.class)
				.forEach(loader -> INITIALIZERS.put(loader.getEngineFactory(), loader.getEngineInitializer()));

	}
}
