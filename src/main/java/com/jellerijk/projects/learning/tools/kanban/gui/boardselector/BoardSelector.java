package com.jellerijk.projects.learning.tools.kanban.gui.boardselector;

import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.domain.DomainController;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BoardSelector extends AnchorPane implements Subscriber {
	private final DomainController controller;

	private VBox boardCards;

	public BoardSelector() {
		controller = DomainController.getInstance();
		buildGUI();
	}

	private void buildGUI() {
		boardCards = new VBox();
		ScrollPane scrollPane = new ScrollPane(boardCards);
		scrollPane.setFitToWidth(true);
		scrollPane.setMaxHeight(200);
		AnchorPane.setTopAnchor(scrollPane, (double) 10);
		AnchorPane.setBottomAnchor(scrollPane, (double) 40);
		AnchorPane.setLeftAnchor(scrollPane, (double) 10);
		AnchorPane.setRightAnchor(scrollPane, (double) 10);
		this.getChildren().add(scrollPane);

		Button btnAdd = new Button("Add board");
		btnAdd.setOnAction(e -> openBoardCreator(e));
		this.getChildren().add(btnAdd);
		AnchorPane.setBottomAnchor(btnAdd, (double) 5);
		AnchorPane.setLeftAnchor(btnAdd, (double) 5);

		this.setPadding(new Insets(16));
		update();
	}

	@Override
	public void update(PublishedMessageType messageType) {
		boardCards.getChildren().clear();
		List<BoardDTO> boards = controller.getAllBoards();
		boards.forEach(board -> boardCards.getChildren().add(new BoardCard(controller, board.id())));
	}

	private void openBoardCreator(ActionEvent event) {
		Logger.log("Opening boardCreator");

		BoardCreator root = new BoardCreator();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

		// TODO: make BoardCreator responsive
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Create New Board");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinWidth(450);
		stage.setMinHeight(500);
		stage.show();
	};

}
