package com.jellerijk.projects.learning.tools.kanban.persistence.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jellerijk.projects.learning.tools.kanban.config.GlobalVars;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.installer.DBInstaller;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.installer.DBInstallerImpl;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
	public void installDatabase() {
		DBInstaller installer = new DBInstallerImpl(databaseLocation);
		try {
			installer.buildDatabase();
		} catch (IOException e) {
			handleDatabaseException(e);
		}
		;
		Logger.log("Succesfully installed the database.");
	}

	// TODO: Convert to Optional<ResultSet>
	@Override
	public ResultSet query(String sql) {
		try {
			Statement stmt = connection.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			handleDatabaseException(e);
		}
		throw new IllegalArgumentException("Could not query the database.");
	}

	@Override
	public void update(String sql) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			handleDatabaseException(e);
		}
	}

	private void handleDatabaseException(Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Database Exception");
		alert.setHeaderText(String.format("%s thrown", ex.getClass().getSimpleName()));
		alert.setContentText(ex.getMessage());
		ex.printStackTrace();
		alert.showAndWait();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	@Override
	public int getLastInserted(String tableName) throws SQLException {
		PreparedStatement pstmt = prepareStatement("SELECT seq FROM sqlite_sequence WHERE name=?");
		pstmt.setString(1, tableName);
		ResultSet rs = pstmt.executeQuery();
		return rs.getInt(0);
	};

}
