package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.io.IOException;
import java.sql.Connection;
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
	 * Returns a connection to the database.
	 * 
	 * @return Connection object to connect to the database.
	 */
	public Connection getConnection() throws SQLException;

	/**
	 * Installs the database if it does not exist yet.
	 */
	public void installDatabase() throws IOException;

}
