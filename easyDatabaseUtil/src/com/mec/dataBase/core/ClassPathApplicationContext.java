package com.mec.dataBase.core;

import java.lang.reflect.Field;

import com.mec.dataBase.exceptions.PrimaryKeyHasMoreThanOne;

public class ClassPathApplicationContext {
	
	static void scanFields(Class<?> klass, TableClassDefinition tcd) {
		Field[] fields = klass.getDeclaredFields();
		
		for (Field field : fields) {
			String fieldName = field.getName();
			PropertyDefinition pd = new PropertyDefinition(field, fieldName);
			
			tcd.putPropertyDefinition(fieldName, pd);
		}
	}
	
	void setId(TableClassDefinition tcd, PropertyDefinition pd) throws PrimaryKeyHasMoreThanOne {
		if (!tcd.isIdNull()) {
			throw new PrimaryKeyHasMoreThanOne();
		}
		
		tcd.setId(pd);
	}
}
