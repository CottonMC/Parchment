package io.github.cottonmc.parchment.impl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

import io.github.cottonmc.parchment.api.ScriptEngineInitializer;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

public class NashornScriptInitializer implements ScriptEngineInitializer {
	private static final NashornScriptEngineFactory NASHORN_FACTORY = new NashornScriptEngineFactory();

	@Override
	public Class<? extends ScriptEngineFactory> getEngineFactory() {
		return NashornScriptEngineFactory.class;
	}

	//necessary thanks to Nashorn's *incredible* compliance with JSR223 /s
	@Override
	public ScriptEngine initialize(ScriptEngine engine) {
		return NASHORN_FACTORY.getScriptEngine(name -> !name.startsWith("java.io") && !name.startsWith("java.nio")
				&& !name.startsWith("java.net"));
	}
}
