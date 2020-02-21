package com.mec.dataBase.core;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Query {
	private static final Map<String, ConnectionPool> databaseConnections = new ConcurrentHashMap<>();
	private static String dataBaseUrl;
	
	Query() { }
	
	static void addConnectionPool(String url, ConnectionPool cp) {
		if (dataBaseUrl == null) {
			dataBaseUrl = url;
		}
		if (databaseConnections.containsKey(url)) {
			return;
		}
		
		databaseConnections.put(url, cp);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> selectAll(Class<?> klass) throws Exception {
		List<T> resultList = new ArrayList<>();
		
		String sql = Expression.selectAll(klass);
		ConnectionDataBase cdb = databaseConnections.get(dataBaseUrl).getConnection();
		TableClassDefinition tcd = TableClassFactory.getDefinition(klass);
		ResultSet rs = cdb.save(sql);
		
		while (rs.next()) {
			Object obj = klass.newInstance();
			tcd.setValueToObject(rs, obj);
			resultList.add((T)obj);
		}
		
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T selectOneById(Class<?> klass, Object id) throws Exception {
		String sql = Expression.selectOneById(klass, id);
		ConnectionDataBase cdb = databaseConnections.get(dataBaseUrl).getConnection();
		TableClassDefinition tcd = TableClassFactory.getDefinition(klass);
		ResultSet rs = cdb.save(sql);
		
		Object obj =klass.newInstance();
		if (rs.next()) {
			System.out.println(rs.getObject("sno"));
			tcd.setValueToObject(rs, obj);
		}
		
		return (T) obj;
	}

	public static int insert(Object obj) throws Exception {
		
		String sql = Expression.insertEmtry(obj);
		System.out.println(sql);
		ConnectionDataBase cdb = databaseConnections.get(dataBaseUrl).getConnection();
		return cdb.update(sql);
	}
	
	public static String getDataBaseUrl() {
		return dataBaseUrl;
	}

	public static void setDataBaseUrl(String defaultUrl) throws Exception {
		if (databaseConnections.containsKey(defaultUrl)) {
			Query.dataBaseUrl = defaultUrl;		
		} else {
			throw new Exception("该url的数据库连接池不存在！!");
		}
	}
	
}
