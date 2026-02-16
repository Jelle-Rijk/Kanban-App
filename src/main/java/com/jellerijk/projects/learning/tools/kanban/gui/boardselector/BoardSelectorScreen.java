package com.jellerijk.projects.learning.tools.kanban.gui.boardselector;

import java.util.List;
import java.util.Optional;

import com.jellerijk.projects.learning.tools.kanban.domain.DomainController;
import com.jellerijk.projects.learning.tools.kanban.gui.board.BoardView;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BoardSelectorScreen extends VBox {
	private final DomainController controller;

	private VBox boardCards;

	public BoardSelectorScreen() {
		controller = DomainController.getInstance();
		buildGUI();
		populateBoardCards();
	}

	private void buildGUI() {
		Label lblSelect = new Label("Please select a board.");
		lblSelect.getStyleClass().add("dialog__title");

		boardCards = new VBox();
		ScrollPane scrollPane = new ScrollPane(boardCards);
		scrollPane.setFitToWidth(true);
		scrollPane.setMaxHeight(200);

		Button btnAdd = new Button("Add board");
		HBox buttonBox = new HBox(btnAdd);
		buttonBox.setAlignment(Pos.CENTER);
		btnAdd.setOnAction(e -> openBoardCreator(e));

		this.setFillWidth(true);
		this.setPadding(new Insets(16));
		this.setSpacing(16);
		this.getChildren().addAll(lblSelect, scrollPane, buttonBox);
	}

	/**
	 * Populates the list of Cards using all Boards in the DomainController's
	 * BoardRepository.
	 */
	public void populateBoardCards() {
		boardCards.getChildren().clear();
		List<BoardDTO> boards = controller.getAllBoards();
		boards.forEach(board -> boardCards.getChildren().add(new Card(board.id())));
	}

	/**
	 * Deletes the Board and then removes the associated Card from the
	 * BoardSelector.
	 * 
	 * @param card    - Card to remove.
	 * @param boardId - Id of the Board to delete.
	 */
	private void handleDelete(Card card, String boardId) {
		controller.deleteBoard(boardId);
		boardCards.getChildren().remove(card);
	};

	/**
	 * Creates a new Board and adds it as a Card.
	 * 
	 * @param title
	 * @param description
	 */
	private void handleCreate(String title, String description) {
		String boardId = controller.createBoard(title, description);
		boardCards.getChildren().add(new Card(boardId));
	}

	/**
	 * Opens a new BoardCreator. If the BoardCreator ends with a succesful submit,
	 * this function will call handleCreate().
	 * 
	 * @param event
	 */
	private void openBoardCreator(ActionEvent event) {
		BoardCreator root = new BoardCreator(controller);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Create New Board");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinWidth(450);
		stage.setMinHeight(500);
		stage.showAndWait();

		BoardDTO result = root.getSubmittedData();
		if (result != null)
			handleCreate(result.title(), result.description());
	};

	private class Card extends VBox {
		private final String boardId;
		private BoardDTO data;

		private TextField txfName;
		private Label lblDescription;
		private Label lblRename;
		private Label lblDelete;

		public Card(String boardId) {
			this.boardId = boardId;
			buildGUI();
			update();
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
			makeEditable(false);
		}

		private void makeEditable(boolean editable) {
			txfName.setEditable(editable);
			txfName.setFocusTraversable(editable);

			// Updates name only when editing.
			txfName.setOnAction(editable ? evt -> updateName() : evt -> {
			});

			// Can select only when not editing.
			setOnMouseClicked(editable ? evt -> {
			} : evt -> handleSelect());

			// Toggle label action
			lblRename.setOnMouseClicked(evt -> {
				evt.consume();
				makeEditable(!editable);
			});
			lblRename.setText(editable ? "Stop editing" : "🖊️ Rename board");

			// Toggle hover behaviour
			setOnMouseEntered(editable ? evt -> {
			} : evt -> showHoverIcons(true));
			setOnMouseExited(editable ? evt -> {
			} : evt -> showHoverIcons(false));

			if (editable)
				Platform.runLater(() -> txfName.requestFocus());

		}

		private void showHoverIcons(boolean hover) {
			lblDelete.setVisible(hover);
			lblRename.setVisible(hover);
		}

		private void handleDelete(MouseEvent event) {
			event.consume();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete board");
			alert.setHeaderText(String.format("You are about to delete %s.", data.title()));
			alert.setContentText("Are you sure you want to delete this board?\nThis action cannot be undone.");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK)
				BoardSelectorScreen.this.handleDelete(this, boardId);
		}

		private void handleSelect() {
			BoardView bv = new BoardView(controller);
			Stage stage = (Stage) getScene().getWindow();
			getScene().setRoot(bv);
			stage.sizeToScene();
		}

		private void updateName() {
			if (!txfName.getText().isBlank()) {
				controller.updateBoard(boardId, txfName.getText(), data.description());
			}
			makeEditable(false);
			update();
		}

		private void update() {
			data = controller.getBoard(boardId);
			txfName.setText(data.title());
			lblDescription.setText(data.description() == null ? "" : data.description());
		}
	}

}
