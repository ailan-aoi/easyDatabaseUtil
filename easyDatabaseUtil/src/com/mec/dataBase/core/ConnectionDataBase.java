package com.mec.dataBase.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ConnectionDataBase {
	public static final int STATUS_BUSY = 0;
	public static final int STATUS_READY = 1;
	
	private Connection connection;
	private int status;
	
	ConnectionDataBase(Connection connection) {
		this.connection = connection;
		this.status = STATUS_READY;
	}
	
	ConnectionDataBase getConnection() {
		this.status = STATUS_BUSY;
		return this;
	}
	
	void close() {
		this.status = STATUS_READY;
	}
	
	boolean isBusy() {
		return status == STATUS_BUSY;
	}
	
	ResultSet save(String sql) {
		try {
			PreparedStatement statue = connection.prepareStatement(sql);
			return statue.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	int update(String sql) {
		try {
			PreparedStatement statue = connection.prepareStatement(sql);
			return statue.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}
