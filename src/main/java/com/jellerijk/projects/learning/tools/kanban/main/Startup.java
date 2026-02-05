package com.jellerijk.projects.learning.tools.kanban.main;

import java.util.Optional;

import com.jellerijk.projects.learning.tools.kanban.gui.boardselector.BoardSelector;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBControllerImpl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Startup extends Application {
	public static void main(String[] args) {
		System.out.println("Kanban booting up.");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		connectToDatabase();

		BoardSelector root = new BoardSelector();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Kanban Board");
		primaryStage.show();
//		Platform.exit();
	}

	public void connectToDatabase() {
		DBController dbc = DBControllerImpl.getInstance();
		if (!dbc.verify()) {
			String error = "Database not found";
			Logger.logError(error);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(error);
			alert.setHeaderText(error);
			alert.setContentText("Would you like to install the database?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get().equals(ButtonType.OK)) {
				dbc.installDatabase();
			}
		}
		dbc.connect();
	}

}
