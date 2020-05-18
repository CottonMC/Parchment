package io.github.cottonmc.parchment.impl;

import java.util.function.UnaryOperator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

import io.github.cottonmc.parchment.api.ScriptEngineInitializer;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

//TODO: do we need to do anything to prep Nashorn? Right now it's not even worth putting in the fabric.mod.json...
public class NashornScriptInitializer implements ScriptEngineInitializer {
	@Override
	public Class<? extends ScriptEngineFactory> getEngineFactory() {
		return NashornScriptEngineFactory.class;
	}

	@Override
	public ScriptEngine initialize(ScriptEngine engine) {
		return engine;
	}
}
