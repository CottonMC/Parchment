package io.github.cottonmc.parchment.impl;

//TODO: does this accually work?
public class ScriptClassLoader extends ClassLoader {
	public static final ScriptClassLoader INSTANCE = new ScriptClassLoader();

	private ScriptClassLoader() {
		super(ScriptClassLoader.class.getClassLoader());
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if (name.startsWith("java.io") || name.startsWith("java.nio") || name.startsWith("java.net")) {
			throw new ClassNotFoundException("I/O is not accessible from scripts!");
		}
		return super.findClass(name);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		if (name.startsWith("java.io") || name.startsWith("java.nio") || name.startsWith("java.net")) {
			throw new ClassNotFoundException("I/O is not accessible from scripts!");
		}
		return super.loadClass(name, resolve);
	}

}
