package io.github.cottonmc.parchment.api;

import java.util.function.UnaryOperator;

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
	 * @return An operator which applies any necessary changes to an engine before it's passed off to the loader.
	 */
	UnaryOperator<ScriptEngine> getEngineInitializer();
}
