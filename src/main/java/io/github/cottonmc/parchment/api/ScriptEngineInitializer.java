package io.github.cottonmc.parchment.api;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * An optional interface for defining any necessary code in a script engine before the proper script is loaded.
 */
public interface ScriptEngineInitializer {
	/**
	 * @return The class of the JSR-223 ScriptEngineFactory for this engine.
	 */
	Class<? extends ScriptEngineFactory> getEngineFactory();

	/**
	 * Prepare a script engine to be used.
	 * @param engine An engine to be used to initialize a script.
	 * @return The same script engine passed in, with bindings set.
	 */
	ScriptEngine initialize(ScriptEngine engine);
}
