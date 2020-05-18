package io.github.cottonmc.parchment.api;

import javax.annotation.Nullable;
import javax.script.ScriptEngine;

import net.minecraft.util.Identifier;

/**
 * A wrapper that has all the necessary information for a script to be run and accessed.
 */
public interface Script {

	/**
	 * @return The engine used to run this script.
	 */
	ScriptEngine getEngine();

	/**
	 * @return The ID of this script.
	 */
	Identifier getId();

	/**
	 * @return The string contents of this script.
	 */
	String getContents();

	/**
	 * Get the value of a variable defined in the script.
	 * @param name The name of the variable.
	 * @return The value of the variable, or null if it doesn't exist.
	 */
	@Nullable
	Object getVar(String name);

	void run();

	boolean hasRun();

	boolean hadError();
}
