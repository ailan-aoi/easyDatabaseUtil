package com.mec.dataBase.core;

public class DataSource {
	
	public static void initDataSource(String propertiesPath, String packageName) {
		new ClassPathAnnotationApplicationContext(packageName);
		ConnectionPool cp = new ConnectionPool(propertiesPath);
		Query.addConnectionPool(cp.getUrl(), cp);
	}
	
}
