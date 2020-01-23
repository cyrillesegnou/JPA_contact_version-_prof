package org.antislashn.contacts.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
	private String url;
	private String user;
	private String pswd;
	
	public Dao(String driver, String url, String user, String pswd) throws ClassNotFoundException {
		this.url = url;
		this.user = user;
		this.pswd = pswd;
		Class.forName(driver);
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, pswd);
	}
	
	public void close(Connection connection) throws SQLException {
		connection.close();
	}
}
