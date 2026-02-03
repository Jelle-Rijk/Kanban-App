package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.sql.Connection;

public interface SQLiteConnection {
	/**
	 * Creates and returns the collection with the SQLite database.
	 */
	public Connection getConnection();
}
