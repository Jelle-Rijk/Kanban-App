package com.jellerijk.projects.learning.tools.kanban.gui.boardselector;

import java.util.Optional;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoardCard extends VBox implements Subscriber {
	private final BoardController boardController;

	private final int boardId;
	private String boardName;
	private String boardDescription;

	private Label lblName;
	private Label lblDescription;
	private Label lblRename;
	private Label lblDelete;

	public BoardCard(BoardController boardController, int boardId) {
		this.boardController = boardController;
		this.boardId = boardId;
		buildGUI();
	}

	private void buildGUI() {
		lblName = new Label();
		lblDescription = new Label();
		lblRename = new Label("🖊️ Rename board");
		lblDelete = new Label("🗑️ Delete board");

		HBox hoverRow = new HBox(lblRename, lblDelete);
		hoverRow.setSpacing(15);

		this.getStyleClass().add("boardCard");
		lblName.getStyleClass().add("boardCard__header");
		lblDelete.getStyleClass().addAll("boardCard__hoverElement", "clickable");
		lblRename.getStyleClass().addAll("boardCard__hoverElement", "clickable");

		lblRename.setOnMouseClicked(e -> handleRename(e));
		lblDelete.setOnMouseClicked(e -> handleDelete(e));
		this.setOnMouseClicked(e -> handleSelect(e));

		getChildren().addAll(lblName, lblDescription, hoverRow);
		update();
	}

	private void setBoardName(String boardName) {
//		if (boardName == null || boardName.isBlank())
//			throw new IllegalArgumentException("Boardname null or empty.");
		this.boardName = boardName;
		lblName.setText(this.boardName);
	}

	private void setBoardDescription(String boardDescription) {
//		if (boardDescription == null || boardDescription.isBlank())
//			throw new IllegalArgumentException("BoardDescription null or empty");
		this.boardDescription = boardDescription;
		lblDescription.setText(this.boardDescription);
	}

	// TODO: implement renaming
	private void handleRename(MouseEvent event) {
		event.consume();
		Logger.log("Activated renaming handler.");
	};

	private void handleDelete(MouseEvent event) {
		event.consume();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete board");
		alert.setHeaderText(String.format("You are about to delete %s.", boardName));
		alert.setContentText("Are you sure you want to delete this board?\nThis action cannot be undone.");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.isPresent() && result.get() == ButtonType.OK)
			boardController.deleteBoard(boardId);
	}

	// TODO: implement selecting
	private void handleSelect(MouseEvent event) {
		event.consume();
		Logger.log(String.format("Selected board %d", boardId));
	}

	@Override
	public void update(PublishedMessageType messageType) {
		BoardDTO board = boardController.getBoard(boardId);
		setBoardName(board.name());
		setBoardDescription(board.description());
	}

}
