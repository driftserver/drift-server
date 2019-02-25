package io.drift.ui.infra;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

public class GroovyConfigLoader {

	private GroovyScriptEngine engine;

	public GroovyConfigLoader(String groovyDir, ClassLoader parentClassLoader) throws Exception {
		engine = new GroovyScriptEngine(groovyDir, parentClassLoader);
	}
	
	public Object load(String fileName) throws Exception {
		Binding binding = new Binding();
		Object obj = engine.run(fileName, binding);
		return obj;
	}
	
}
