package io.github.cottonmc.parchment.api;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.minecraft.util.Identifier;

/**
 * A script wrapper that's fully implemented for basic use and pre-compiles scripts.
 */
public class SimpleCompilableScript extends SimpleScript implements CompilableScript {
	protected final Compilable compilable;
	protected CompiledScript compiled;
	protected boolean hadCompileError = false;


	public SimpleCompilableScript(ScriptEngine engine, Identifier name, String contents) {
		super(engine, name, contents);
		this.compilable = (Compilable)engine;
		this.compiled = getCompiledScript();
	}

	@Override
	public CompiledScript getCompiledScript() {
		try {
			return compilable.compile(getContents());
		} catch (ScriptException e) {
			getLogger().error("Error compiling script {}: {}", getId().toString(), e.getMessage());
			hadCompileError = true;
			return null;
		}
	}

	@Override
	public boolean hadCompileError() {
		return hadCompileError;
	}

	@Override
	public void run() {
		if (!hadCompileError) {
			try {
				compiled.eval();
			} catch (Exception e) {
				getLogger().error("Script {} encountered error while running: {}", getId().toString(), e.getMessage());
				e.printStackTrace();
				hadError = true;
			}
			hasRun = true;
		} else {
			getLogger().error("Script {} could not be run because it failed while compiling", getId().toString());
		}
	}

}
