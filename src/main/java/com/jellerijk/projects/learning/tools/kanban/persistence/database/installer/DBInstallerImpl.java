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
		String sql = "CREATE TABLE IF NOT EXISTS \"Board\" (\n" + "	\"BoardId\"	INTEGER,\n"
				+ " \"Name\" TEXT NOT NULL,\n" + " \"Description\" TEXT, \n"
				+ "PRIMARY KEY(\"BoardId\" AUTOINCREMENT)\n" + ");";
		executeSQL(sql);

		// STAGE
		sql = "CREATE TABLE IF NOT EXISTS \"Stage\" (\n" + "	\"StageNumber\"	INTEGER,\n" + "	\"BoardId\"	INTEGER,\n"
				+ "	\"Description\"	TEXT NOT NULL,\n" + "	\"Limit\"	INTEGER,\n"
				+ "	FOREIGN KEY(\"BoardId\") REFERENCES \"Board\"(\"BoardId\"),\n"
				+ "	PRIMARY KEY(\"StageNumber\",\"BoardId\")\n" + ");";
		executeSQL(sql);

		// TASK
		sql = "CREATE TABLE \"Task\" (\n" + "	\"TaskId\"	INTEGER,\n" + "	\"Description\"	TEXT NOT NULL,\n"
				+ "	\"Stage\"	INTEGER NOT NULL,\n" + "	\"Board\"	INTEGER NOT NULL,\n"
				+ "	FOREIGN KEY(\"Stage\") REFERENCES \"Stage\"(\"StageNumber\"),\n"
				+ "	PRIMARY KEY(\"TaskId\" AUTOINCREMENT),\n"
				+ "	FOREIGN KEY(\"Board\") REFERENCES \"Board\"(\"BoardId\")\n" + ");";
		executeSQL(sql);
	}

	private void executeSQL(String sql) {
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
