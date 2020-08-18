package io.github.cottonmc.parchment.api;

import javax.annotation.Nullable;
import javax.script.CompiledScript;

/**
 * A script which can be pre-compiled so it can be run faster when needed.
 * This can either pre-compile or compile lazily.
 */
public interface CompilableScript extends Script {
	/**
	 * @return The compiled form of the script, or null if errored. If it hasn't been compiled yet, compile it now.
	 */
	@Nullable
	CompiledScript getCompiledScript();

	/**
	 * @return Whether the script encountered an error while compiling.
	 */
	boolean hadCompileError();
}
