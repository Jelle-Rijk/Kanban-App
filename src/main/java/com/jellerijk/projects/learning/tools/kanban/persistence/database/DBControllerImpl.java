package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;

import com.jellerijk.projects.learning.tools.kanban.config.GlobalVars;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.installer.DBInstaller;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.installer.DBInstallerImpl;

public class DBControllerImpl implements DBController {
	private static DBController instance;
	private final String databaseLocation;
	private Connection connection;

	private DBControllerImpl() {
		databaseLocation = GlobalVars.DB_PATH;
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
	public void connect() {
		SQLiteConnection sqlConn = new SQLiteConnectionImpl(databaseLocation);
		connection = sqlConn.getConnection();
		Logger.log(String.format("Connection %s", connection == null ? "failed" : "succeeded"));
	}

	@Override
	public void installDatabase() throws IOException {
		DBInstaller installer = new DBInstallerImpl(databaseLocation);
		installer.buildDatabase();
		Logger.log("Succesfully installed the database.");
	}

}
