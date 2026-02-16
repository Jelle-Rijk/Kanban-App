package com.jellerijk.projects.learning.tools.kanban.gui.boardselector;

import java.util.Optional;

import com.jellerijk.projects.learning.tools.kanban.domain.DomainController;
import com.jellerijk.projects.learning.tools.kanban.gui.board.BoardView;
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
	private final DomainController controller;

	private final String boardId;
	private BoardDTO data;

	private TextField txfName;
	private Label lblDescription;
	private Label lblRename;
	private Label lblDelete;

	public BoardCard(DomainController controller, String boardId) {
		this.controller = controller;
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
		txfName.getStyleClass().addAll("boardCard__header", "transparent");
		lblDelete.getStyleClass().addAll("boardCard__hoverElement", "clickable");
		lblRename.getStyleClass().addAll("boardCard__hoverElement", "clickable");

		// EVENTS
		lblDelete.setOnMouseClicked(e -> handleDelete(e));

		getChildren().addAll(txfName, lblDescription, hoverRow);
		disableEditing();
		update();
	}

	private void setBoardName(String boardName) {
		txfName.setText(boardName);
	}

	private void setBoardDescription(String boardDescription) {
		lblDescription.setText(boardDescription == null ? "" : boardDescription);
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
			txfName.setText(data.title());
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
		alert.setHeaderText(String.format("You are about to delete %s.", data.title()));
		alert.setContentText("Are you sure you want to delete this board?\nThis action cannot be undone.");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK)
			controller.deleteBoard(boardId);
	}

	private void handleSelect(MouseEvent event) {
		event.consume();
		BoardView bv = new BoardView(controller);
		Stage stage = (Stage) getScene().getWindow();
		getScene().setRoot(bv);
		stage.sizeToScene();
	}

	@Override
	public void update(PublishedMessageType messageType) {
		data = controller.getBoard(boardId);
		setBoardName(data.title());
		setBoardDescription(data.description());
	}

	private void updateName() {
		if (!txfName.getText().isBlank()) {
			controller.updateBoard(boardId, txfName.getText(), data.description());
		}
		disableEditing();
		if (isHover())
			lblDelete.setVisible(true);
		data = controller.getBoard(boardId);
		txfName.setText(data.title());
	}

}
