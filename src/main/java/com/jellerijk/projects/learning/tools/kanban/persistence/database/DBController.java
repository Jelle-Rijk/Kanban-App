package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.io.IOException;
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
	 * 
	 * @throws IOException
	 */
	public void installDatabase() throws IOException;

	/**
	 * Queries the database.
	 * @param sql
	 * @return The result of the query.
	 */
	public ResultSet query(String sql);
	
	
}
