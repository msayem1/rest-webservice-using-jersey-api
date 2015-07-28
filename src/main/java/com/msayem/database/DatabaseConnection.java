package com.msayem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Setup Database Connection.
 * 
 * @author MSAYEM
 * 
 */
public class DatabaseConnection {

	private String url;
	private static DatabaseConnection instance;

	private DatabaseConnection() {
		
		String driver = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://db4free.net:3306/dbmsayem?user=msayem&password=asdfasdf";
			ResourceBundle bundle = ResourceBundle.getBundle("database");
			driver = bundle.getString("jdbc.driver");
			Class.forName(driver);
			url=bundle.getString("jdbc.url");
		} 
		catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {

		if (instance == null) {

			instance = new DatabaseConnection();
		}
		try {

			return DriverManager.getConnection(instance.url);
		} 
		catch (SQLException e) {

			throw e;
		}
	}

	public static void close(Connection connection) {

		try {

			if (connection != null) {

				connection.close();
			}
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
	}
}