package com.jellerijk.projects.learning.tools.kanban.gui.boardselector;

import java.util.Optional;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.gui.board.BoardView;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BoardCard extends VBox implements Subscriber {
	private final BoardController boardController;

	private final int boardId;
	private String boardName;
	private String boardDescription;

	private TextField txfName;
	private Label lblDescription;
	private Label lblRename;
	private Label lblDelete;

	public BoardCard(BoardController boardController, int boardId) {
		this.boardController = boardController;
		boardController.subscribe(this, boardId);
		this.boardId = boardId;
		buildGUI();
	}

	private void buildGUI() {
		txfName = new TextField();
		lblDescription = new Label();
		lblRename = new Label();
		lblDelete = new Label("🗑️ Delete board");
		lblRename.setVisible(false);
		lblDelete.setVisible(false);

		HBox hoverRow = new HBox(lblRename, lblDelete);
		hoverRow.setSpacing(15);

		// CSS
		this.getStyleClass().add("boardCard");
		txfName.getStyleClass().add("boardCard__header");
		lblDelete.getStyleClass().addAll("boardCard__hoverElement", "clickable");
		lblRename.getStyleClass().addAll("boardCard__hoverElement", "clickable");

		// EVENTS
		lblDelete.setOnMouseClicked(e -> handleDelete(e));

		getChildren().addAll(txfName, lblDescription, hoverRow);
		disableEditing();
		update();
	}

	private void setBoardName(String boardName) {
		this.boardName = boardName;
		txfName.setText(this.boardName);
	}

	private void setBoardDescription(String boardDescription) {
		this.boardDescription = boardDescription == null ? "" : boardDescription;
		lblDescription.setText(this.boardDescription);
	}

	private void enableEditing() {
		lblDelete.setVisible(false);
		setOnMouseEntered(e -> {
		});
		setOnMouseExited(e -> {
		});
		// TODO: Clean this up
		txfName.setOnMouseClicked(e -> {
		});
		setOnMouseClicked(e -> {
		});

		txfName.setEditable(true);
		txfName.setFocusTraversable(true);
		txfName.requestFocus();

		txfName.setOnAction(e -> updateName());

		lblRename.setText("Stop editing");
		lblRename.setOnMouseClicked(e -> {
			e.consume();
			disableEditing();
			if (isHover())
				lblDelete.setVisible(true);
			txfName.setText(boardName);
		});
	};

	private void disableEditing() {
		txfName.setEditable(false);
		txfName.setFocusTraversable(false);

		setOnMouseEntered(e -> {
			lblDelete.setVisible(true);
			lblRename.setVisible(true);
		});

		setOnMouseExited(e -> {
			lblDelete.setVisible(false);
			lblRename.setVisible(false);
		});

		txfName.setOnMouseClicked(e -> handleSelect(e));
		setOnMouseClicked(e -> handleSelect(e));

		lblRename.setText("🖊️ Rename board");
		lblRename.setOnMouseClicked(e -> {
			e.consume();
			enableEditing();
		});
	}

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
		BoardView bv = new BoardView(boardId);
		Stage stage = (Stage) getScene().getWindow();
		getScene().setRoot(bv);
		stage.sizeToScene();
	}

	@Override
	public void update(PublishedMessageType messageType) {
		BoardDTO board = boardController.getBoard(boardId);
		setBoardName(board.name());
		setBoardDescription(board.description());
	}

	private void updateName() {
		if (!txfName.getText().isBlank()) {
			boardController.updateBoard(boardId, new BoardDTO(boardId, txfName.getText(), boardDescription));
		}
		disableEditing();
		if (isHover())
			lblDelete.setVisible(true);
		txfName.setText(boardName);
	}

}
