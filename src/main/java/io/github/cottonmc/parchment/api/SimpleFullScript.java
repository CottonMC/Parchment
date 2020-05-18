package io.github.cottonmc.parchment.api;

import javax.annotation.Nullable;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.minecraft.util.Identifier;

/**
 * A script wrapper that's fully implemented for basic use, pre-compiles scripts, and can invoke functions.
 */
public class SimpleFullScript extends SimpleCompilableScript implements InvocableScript {
	protected final Invocable invocable;

	public SimpleFullScript(ScriptEngine engine, Identifier name, String contents) {
		super(engine, name, contents);
		this.invocable = (Invocable)engine;
		this.compiled = getCompiledScript();
	}

	@Nullable
	@Override
	public Object invokeFunction(String name, Object... arguments) {
		try {
			return invocable.invokeFunction(name, arguments);
		} catch (ScriptException | NoSuchMethodException e) {
			getLogger().error("Error invoking script {}: {}", getId().toString(), e.getMessage());
			e.printStackTrace();
			hadError = true;
			return null;
		}
	}

}
