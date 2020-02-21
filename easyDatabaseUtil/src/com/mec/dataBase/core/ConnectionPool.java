package com.mec.dataBase.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.my.util.core.PropertiesParser;

public class ConnectionPool {
	private final ConcurrentLinkedQueue<ConnectionDataBase> connectionPool = new ConcurrentLinkedQueue<ConnectionDataBase>();
	
	private String url;
	private String user;
	private String password;
	private int maxCount = 50;
	private int minCount = 10;
	private int increaseCount = 3;
	
	ConnectionPool(String propertiesPath) {
		initConnectionPool(propertiesPath);
	}
	
	private boolean initConnectionPool(String propertiesPath) {
		PropertiesParser.readConfig(propertiesPath);
		
		String driver = PropertiesParser.getValueByKey("driver");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		url = PropertiesParser.getValueByKey("url");
		user = PropertiesParser.getValueByKey("user");
		password = PropertiesParser.getValueByKey("password");
		String maxCount = PropertiesParser.getValueByKey("maxCount");
		if (maxCount != null) {
			this.maxCount = Integer.valueOf(maxCount);
		}
		String minCount = PropertiesParser.getValueByKey("minCount");
		if (minCount != null) {
			this.minCount = Integer.valueOf(minCount);
		}
		String increaseCount = PropertiesParser.getValueByKey("increaseCount");
		if (increaseCount != null) {
			this.increaseCount = Integer.valueOf(increaseCount);
		}

		createConnection(this.minCount);
		
		return true; 
	}
	
	boolean createConnection(int count) {
		Connection connection;
		int connectionCount = connectionPool.size();
		for (int i = 0; i < count && connectionCount <= maxCount; i++) {
			try {
				connection = DriverManager.getConnection(url, user, password);
				ConnectionDataBase cdb = new ConnectionDataBase(connection);
				connectionPool.add(cdb);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (connectionCount == connectionPool.size()) {
			return false;
		}

		return true;
	}
	
	ConnectionDataBase getConnection() {
		for (ConnectionDataBase cdb : connectionPool) {
			if (!cdb.isBusy()) {
				return cdb.getConnection();
			}
		}
		
		if (createConnection(increaseCount)) {
			return getConnection();
		} else {
			return null;
		}
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public int getPassword() {
		return password.hashCode();
	}

	public int getMaxCount() {
		return maxCount;
	}

	public int getMinCount() {
		return minCount;
	}

	public int getIncreaseCount() {
		return increaseCount;
	}
	
	
}
