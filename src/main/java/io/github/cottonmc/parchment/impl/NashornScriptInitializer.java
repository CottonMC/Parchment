package io.github.cottonmc.parchment.impl;

import javax.script.ScriptContext;
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
		//lock off access to IO, NIO, and networking from scripts
		ScriptEngine newEngine = NASHORN_FACTORY.getScriptEngine(name -> !name.startsWith("java.io") &&
				!name.startsWith("java.nio") && !name.startsWith("java.net"));
		//lock off the nashorn functions we don't want to support
		ScriptContext ctx = newEngine.getContext();
		//quits the game! no!
		ctx.removeAttribute("quit", ctx.getAttributesScope("quit"));
		ctx.removeAttribute("exit", ctx.getAttributesScope("exit"));
		//loads code from a mystery file or the internet! no!!!
		ctx.removeAttribute("load", ctx.getAttributesScope("load"));
		ctx.removeAttribute("loadWithNewGlobal", ctx.getAttributesScope("loadWithNewGlobal"));
		//reads from the console or a text file! Please do not do this!!!!!
		ctx.removeAttribute("readLine", ctx.getAttributesScope("readLine"));
		ctx.removeAttribute("readFully", ctx.getAttributesScope("readFully"));
		//prints to the command line on its own! technically fine but just use `log.info` please!
		ctx.removeAttribute("print", ctx.getAttributesScope("print"));
		ctx.removeAttribute("echo", ctx.getAttributesScope("echo"));
		//we're safe now, right?
		//...right?
		return newEngine;
	}
}
