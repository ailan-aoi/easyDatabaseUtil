package com.mec.dataBase.core;

public class Expression {
	
	static String selectOneById(Class<?> klass, Object id) throws Exception {
		TableClassDefinition tcd = TableClassFactory.getDefinition(klass);
		if (tcd == null) {
			throw new Exception("没有与您输入的类映射的表");
		}
		
		StringBuffer res = new StringBuffer("SELECT ");
		res.append("*").append(" FROM ").append(tcd.getTableName()).append(" WHERE ")
		.append(tcd.getId().getColumn()).append("=").append(id);
		
		return res.toString();
	}
	
	static String selectAll(Class<?> klass) throws Exception{
		TableClassDefinition tcd = TableClassFactory.getDefinition(klass);
		if (tcd == null) {
			throw new Exception("没有与您输入的类映射的表");
		}
		
		StringBuffer res = new StringBuffer("SELECT ");
		res.append("*").append(" FROM ").append(tcd.getTableName());
		
		return res.toString();
	}
	
	static String insertEmtry(Object obj) throws Exception{
		Class<?> klass = obj.getClass();
		TableClassDefinition tcd = TableClassFactory.getDefinition(klass);
		if (tcd == null) {
			throw new Exception("没有与您输入的对象的类映射的表");
		}
		
		StringBuffer res = new StringBuffer("INSERT INTO ").append(tcd.getTableName()).append(" (");
		res.append(tcd.getColumnNames()).append(") VALUES (").append(tcd.getValues(obj)).append(");");
		
		return res.toString();
	}
	
}
