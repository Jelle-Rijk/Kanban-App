package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;

public class SQLFileExecuter {
	private Connection connection;

	public SQLFileExecuter(Connection connection) {
		if (connection == null)
			throw new IllegalArgumentException("SQLFileExecuter cannot work with a null connection.");
		this.connection = connection;
	}

	public void execute(String filePath) throws IOException, SQLException {
		try {
			String sql = readFile(filePath);
			Statement statement = connection.createStatement();
			statement.execute(sql);
		} catch (Exception ex) {
			Logger.logError(String.format("%s: %s", ex.getClass().getSimpleName(), ex.getMessage()));
		}
	};

	private String readFile(String filePath) throws IOException {
		InputStream in = getClass().getResourceAsStream(filePath);
		return new String(in.readAllBytes(), StandardCharsets.UTF_8);
	}

}
