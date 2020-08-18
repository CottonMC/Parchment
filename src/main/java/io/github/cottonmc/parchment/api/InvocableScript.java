package io.github.cottonmc.parchment.api;

import javax.annotation.Nullable;

/**
 * A script which can have functions invoked from Java.
 */
public interface InvocableScript extends Script {
	/**
	 * Invoke a function defined in the script. Should only be used on invocable ScriptEngines.
	 * @param name The name of the function to invoke.
	 * @param arguments The arguments passed to this function.
	 * @return The result of the function, or null if the function doesn't exist or exists and returns nothing.
	 */
	//TODO: throw if the function doesn't exist? Is that possible?
	@Nullable
	Object invokeFunction(String name, Object...arguments);
}
