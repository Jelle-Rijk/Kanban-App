package com.jellerijk.projects.learning.tools.kanban.persistence.database.installer;

import java.io.IOException;

public interface DBInstaller {
	/**
	 * Builds the main SQLite database.
	 * @throws IOException
	 */
	public void buildDatabase() throws IOException;
}
