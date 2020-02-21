package com.mec.dataBase.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class TableClassFactory {
	private static final Map<String, TableClassDefinition> definitionMap = new ConcurrentHashMap<>();
	
	static void putDefinition(String className, TableClassDefinition tcd) {
		if (definitionMap.containsKey(className)) {
			return;
		}
		
		definitionMap.put(className, tcd);
 	}
	
	static TableClassDefinition getDefinition(Class<?> klass) {
		return definitionMap.get(klass.getName());
	}
	
	static TableClassDefinition getDefinition(String klassName) {
		return definitionMap.get(klassName);
	}
}
