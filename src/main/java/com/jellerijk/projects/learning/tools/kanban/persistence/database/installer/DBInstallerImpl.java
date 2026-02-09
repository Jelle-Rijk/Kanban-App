package com.jellerijk.projects.learning.tools.kanban.persistence.database.installer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.jellerijk.projects.learning.tools.kanban.config.GlobalVars;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.SQLFileExecuter;

public class DBInstallerImpl implements DBInstaller {
	private final String dbURL;
	private Connection conn;

	public DBInstallerImpl() {
		this(GlobalVars.DB_PATH);
	}

	public DBInstallerImpl(String dbURL) {
		this.dbURL = dbURL;
	}

	@Override
	public void buildDatabase() throws IOException {
		try {
			conn = createDatabase();
			createTables();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Creates the database file and its parent directories if they do not exist.
	 * 
	 * @throws IOException
	 * @return The connection to the new database
	 */
	private Connection createDatabase() throws IOException, SQLException {
		Path dbPath = Path.of(dbURL);
		Path dbDirectory = dbPath.getParent();

		if (!Files.exists(dbDirectory) || !Files.isDirectory(dbDirectory)) {
			Files.createDirectories(dbDirectory);
		}
		if (Files.exists(dbPath)) {
			throw new IllegalArgumentException("Tried to create database when it already exists.");
		}

		String url = String.format("jdbc:sqlite:%s", dbURL);
		Connection connection = DriverManager.getConnection(url);
		if (connection != null) {
			Logger.log(String.format("New database created at %s", dbURL));
			return connection;
		}
		throw new IllegalArgumentException("The connection was null");
	};

	private void createTables() {
		// BOARD
		try {
			SQLFileExecuter fe = new SQLFileExecuter(conn);
			String directory = "/sql/tablecreators/";
			for (String fileName : new String[] { "board.sql", "stage.sql", "task.sql" }) {
				fe.execute(directory + fileName);
			}
		} catch (IOException | SQLException e) {
			Logger.logError(e.getMessage());
			e.printStackTrace();
		}
	}

}
