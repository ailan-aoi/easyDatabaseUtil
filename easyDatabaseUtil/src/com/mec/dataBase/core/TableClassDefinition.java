package com.mec.dataBase.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class TableClassDefinition {
	private Class<?> klass;
	private String tableName;
	private Map<String, PropertyDefinition> propertys;
	private PropertyDefinition id;
	private String columnNames;
	
	TableClassDefinition() {
		propertys = new HashMap<>();
	}
	
	TableClassDefinition(Class<?> klass, String tableName) {
		this();
		setKlass(klass);
		setTableName(tableName);
	}

	Class<?> getKlass() {
		return klass;
	}

	void setKlass(Class<?> klass) {
		this.klass = klass;
	}

	String getTableName() {
		return tableName;
	}

	void setTableName(String table) {
		this.tableName = table;
	}

	PropertyDefinition getId() {
		return id;
	}

	void setId(PropertyDefinition id) {
		this.id = id;
	}
	
	boolean isIdNull() {
		return id==null;
	}
	
	void putPropertyDefinition(String fieldName, PropertyDefinition pd) {
		if (propertys.containsKey(fieldName)) {
			return;
		}
		propertys.put(fieldName, pd);
	}
	
	PropertyDefinition getProperty(String fieldName) {
		return propertys.get(fieldName);
	}
	
	void setValueToObject(ResultSet rs, Object obj) {
		try {
			for (PropertyDefinition pd : propertys.values()) {
				pd.setValue(obj, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	String getColumnNames() {
		StringBuffer res = new StringBuffer();
		
		if (columnNames == null) {
			for (PropertyDefinition pd : propertys.values()) {
				res.append(", ").append(pd.getColumn());
			}
			
			columnNames =  res.toString().substring(2);
		}
		return columnNames;
	}
	
	String getValues(Object obj) {
		StringBuffer res = new StringBuffer();
		
		for (PropertyDefinition pd : propertys.values()) {
			boolean flag = pd.getValue(obj) instanceof String;
			res.append(", ").append(flag == true ? "'" + pd.getValue(obj) + "'" : pd.getValue(obj));
		}
		
		return res.toString().substring(2);
	}
	
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		
		res.append("class:").append(klass.getSimpleName()).append("\ntable:").append(tableName)
		.append("\nfieldsColumn:\n");
		
		for (PropertyDefinition pd : propertys.values()) {
			res.append(pd).append("\n");
		}
		res.append("id:").append(id).append("\n");
		
		return res.toString();
	}
	
}
