package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.sql.ResultSet;

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
	 * Connects to the SQLite database
	 */
	public void connect();

	/**
	 * Installs the database if it does not exist yet.
	 */
	public void installDatabase();

	/**
	 * Queries the database.
	 * @param sql
	 * @return The result of the query.
	 */
	public ResultSet query(String sql);
	
	/**
	 * Can be used to execute SQL statements on the database. Use this for CUD operations. Use query() for operations that require a ResultSet.
	 * @param sql
	 */
	void update(String sql);
	
	
}
