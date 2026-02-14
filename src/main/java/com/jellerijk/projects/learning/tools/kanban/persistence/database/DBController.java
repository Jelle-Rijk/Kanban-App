package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBController {
	public static DBController getInstance() {
		return DBControllerImpl.getInstance();
	}

	/**
	 * Verifies whether the SQLite database exists.
	 * 
	 * @return
	 */
	public boolean verify();
	
	/**
	 * Returns the connection to the database.
	 * @return Connection object to connect to the database.
	 */
	public Connection getConnection();

	/**
	 * Connects to the SQLite database
	 */
	public void connect();

	/**
	 * Installs the database if it does not exist yet.
	 */
	public void installDatabase();

	/**
	 * Queries the database.
	 * 
	 * @param sql
	 * @return The result of the query.
	 */
	public ResultSet query(String sql);

	/**
	 * Can be used to execute SQL statements on the database. Use this for CUD
	 * operations. Use query() for operations that require a ResultSet.
	 * 
	 * @param sql
	 */
	public void update(String sql);

	/**
	 * Returns a PreparedStatement that can be used to Query the database safely.
	 * 
	 * @param statement
	 * @return
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException;

	/**
	 * Returns the last inserted autoincrement number for a given table.
	 * 
	 * @param tableName
	 * @return the last autoincrement value in this table
	 */
	public int getLastInserted(String tableName) throws SQLException;

}
