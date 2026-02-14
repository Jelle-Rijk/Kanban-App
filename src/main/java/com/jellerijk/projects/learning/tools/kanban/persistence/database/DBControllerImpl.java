package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jellerijk.projects.learning.tools.kanban.config.GlobalVars;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.installer.DBInstaller;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.installer.DBInstallerImpl;

public class DBControllerImpl implements DBController {
	private static DBController instance;
	private final String databaseLocation;
	private final String databaseURL;

	private DBControllerImpl() {
		this.databaseLocation = GlobalVars.DB_PATH;
		this.databaseURL = String.format("jdbc:sqlite:%s", databaseLocation);
	}

	public static DBController getInstance() {
		if (instance == null)
			instance = new DBControllerImpl();
		return instance;
	}

	@Override
	public boolean verify() {
		return Files.exists(Path.of(databaseLocation));
	}

	@Override
	public void installDatabase() throws IOException {
		DBInstaller installer = new DBInstallerImpl(databaseLocation);
		try {
			installer.buildDatabase();
		} catch (IOException e) {
			throw new IOException("Something went wrong while installing the database.", e);
		}
		Logger.log("Succesfully installed the database.");
	}

	@Override
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			Logger.logError("Made a mistake in the forName declaration within getConnection in DBControllerImpl");
		}
		Connection conn = DriverManager.getConnection(databaseURL);
		return conn;

	};

}
