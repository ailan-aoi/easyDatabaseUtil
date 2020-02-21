package com.mec.dataBase.core;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

class PropertyDefinition {
	private Field field;
	private String column;

	PropertyDefinition() {
	}

	public PropertyDefinition(Field field, String column) {
		setField(field);
		setColumn(column);
	}	
 
	void setValue(Object obj, ResultSet rs) throws SQLException, IllegalArgumentException, IllegalAccessException {
		Object value = null;
		field.setAccessible(true);
		value = rs.getObject(column);
		field.set(obj, value);
	}
	
	Field getField() {
		return field;
	}

	void setField(Field field) {
		this.field = field;
	}

	String getColumn() {
		return column;
	}

	void setColumn(String column) {
		this.column = column;
	}

	Object getValue(Object obj) {
		Object value = null;
		try {
			field.setAccessible(true);
			value = field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	@Override
	public String toString() {
		return "field:" + field.getName() + ", column:" + column;
	}
	
}
