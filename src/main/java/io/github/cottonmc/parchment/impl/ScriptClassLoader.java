package io.github.cottonmc.parchment.impl;

public class ScriptClassLoader extends ClassLoader {
	public static final ScriptClassLoader INSTANCE = new ScriptClassLoader();

	private ScriptClassLoader() {
		super(ScriptClassLoader.class.getClassLoader());
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if (name.contains("java.io") || name.contains("java.nio") || name.contains("java.net")) {
			throw new ClassNotFoundException("I/O is not accessible from scripts!");
		}
		return super.findClass(name);
	}
}
