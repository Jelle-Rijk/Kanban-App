package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionImpl implements SQLiteConnection {
	private Connection connection;
	private final String dbPath;

	public SQLiteConnectionImpl(String dbPath) {
		this.dbPath = dbPath;
		this.connection = createConnection();
	}

	private Connection createConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection newConnection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbPath));
			return newConnection;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} catch (Exception ex) {
			// TODO: handle exception properly
			ex.printStackTrace();
		}
		throw new IllegalArgumentException("Failed to create a database connection");

	}

	@Override
	public Connection getConnection() {
		return connection;
	}

}
