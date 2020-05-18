package io.github.cottonmc.parchment.api;


import javax.annotation.Nullable;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.minecraft.util.Identifier;

/**
 * A script wrapper that's fully implemented for basic use and can invoke functions.
 */
public class SimpleInvocableScript extends SimpleScript implements InvocableScript {
	protected Invocable invocable;

	public SimpleInvocableScript(ScriptEngine engine, Identifier name, String contents) {
		super(engine, name, contents);
		this.invocable = (Invocable)engine;
	}

	@Nullable
	@Override
	public Object invokeFunction(String name, Object... arguments) {
		try {
			return invocable.invokeFunction(name, arguments);
		} catch (ScriptException | NoSuchMethodException e) {
			getLogger().error("Error invoking script {}: {}", getId().toString(), e.getMessage());
			hadError = true;
			return null;
		}
	}
}
