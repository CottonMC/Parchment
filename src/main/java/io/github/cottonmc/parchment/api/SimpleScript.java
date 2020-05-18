package io.github.cottonmc.parchment.api;

import javax.annotation.Nullable;
import javax.script.ScriptEngine;

import io.github.cottonmc.parchment.Parchment;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Identifier;

/**
 * A script wrapper that's fully implemented for basic use.
 */
public class SimpleScript implements Script {
	private final ScriptEngine engine;
	private final Identifier name;
	private final String contents;
	protected boolean hasRun = false;
	protected boolean hadError = false;

	public SimpleScript(ScriptEngine engine, Identifier name, String contents) {
		this.engine = engine;
		this.name = name;
		this.contents = contents;
	}

	/**
	 * @return The logger used to report erros.
	 */
	protected Logger getLogger() {
		return Parchment.logger;
	}

	@Override
	public ScriptEngine getEngine() {
		return engine;
	}

	@Override
	public Identifier getId() {
		return name;
	}

	@Override
	public String getContents() {
		return contents;
	}

	@Nullable
	@Override
	public Object getVar(String name) {
		return engine.getContext().getAttribute(name);
	}

	@Override
	public void run() {
		try {
			engine.eval(contents);
		} catch (Exception e) {
			getLogger().error("Script {} encountered error while running: {}", name.toString(), e.getMessage());
			e.printStackTrace();
			hadError = true;
		}
		hasRun = true;
	}

	@Override
	public boolean hasRun() {
		return hasRun;
	}

	@Override
	public boolean hadError() {
		return hadError;
	}
}
