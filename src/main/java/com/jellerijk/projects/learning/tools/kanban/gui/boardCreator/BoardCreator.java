package com.jellerijk.projects.learning.tools.kanban.gui.boardCreator;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoardCreator extends ScrollPane {
	TextField txfName;
	Label lblNameError;
	TextArea txaDescription;
	Label lblDescriptionError;
	Button btnCreate;

	public BoardCreator() {
		buildGUI();
	}

	private void buildGUI() {
		GridPane gp = new GridPane();
		this.setBackground(Background.fill(Color.AQUA));
		// SET COLUMNS
		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();

		col1.setMinWidth(200);
		col1.setMaxWidth(200);
		col2.setMinWidth(200);
		col2.setMaxWidth(Double.MAX_VALUE);

		gp.getColumnConstraints().addAll(col1, col2);

		// Header
		Label lblTitle = new Label("Create New Board");
		Label lblSubtitle = new Label("Set up a new Kanban Board");
		lblTitle.setPrefWidth(Double.MAX_VALUE);
		lblTitle.setAlignment(Pos.CENTER);
		lblSubtitle.setPrefWidth(Double.MAX_VALUE);
		lblSubtitle.setAlignment(Pos.CENTER);

		// Board name fields
		Label lblName = new Label("Board Name");
		txfName = new TextField();
		lblNameError = new Label();
		txfName.setPromptText("e.g. Personal Tasks, Project Phoenix");
		txfName.setOnKeyTyped(e -> handleNameUpdate(e));

		// Description fields
		Label lblDescription = new Label("Description (Optional)");
		txaDescription = new TextArea();
		txaDescription.setPromptText("What is this board for?");
		lblDescriptionError = new Label();

		// Footer
		btnCreate = new Button("Create Board");
		btnCreate.setOnAction(e -> createBoard());
		btnCreate.setDisable(true);
		Label lblCancel = new Label("Cancel");
		lblCancel.setOnMouseClicked(e -> {
			e.consume();
			closeCreator();
		});
		HBox footerButtons = new HBox(lblCancel, btnCreate);
		footerButtons.setAlignment(Pos.CENTER_RIGHT);
		footerButtons.setSpacing(20);

		// Grid layout
		gp.add(lblTitle, 0, 0, 2, 1);
		gp.add(lblSubtitle, 0, 1, 2, 1);
		GridPane.setHalignment(lblTitle, HPos.CENTER);
		GridPane.setHalignment(lblSubtitle, HPos.CENTER);

		gp.add(lblName, 0, 2);
		gp.add(txfName, 1, 2);
		gp.add(lblNameError, 1, 3);

		gp.add(lblDescription, 0, 4);
		gp.add(txaDescription, 0, 5, 2, 1);
		gp.add(lblDescriptionError, 0, 6);

		gp.add(footerButtons, 1, 7);

		gp.setPadding(new Insets(20));
		gp.setHgap(5);
		gp.setVgap(10);

		// CSS
		lblTitle.getStyleClass().add("dialog__title");
		lblSubtitle.getStyleClass().add("dialog__subtitle");
		lblName.getStyleClass().add("dialog__inputlabel");
		lblDescription.getStyleClass().add("dialog__inputlabel");
		lblNameError.getStyleClass().add("dialog__errormessage");
		lblDescriptionError.getStyleClass().add("dialog__errormessage");
		lblCancel.getStyleClass().addAll("dialog__cancellabel", "clickable");

		setContent(gp);
		setFitToHeight(true);
		setFitToWidth(true);
		setMinWidth(500);
	}

	private void createBoard() {
		String name = txfName.getText();
		String description = txaDescription.getText();
		BoardDTO dto = new BoardDTO(-1, name, description);
		BoardController.getInstance().addBoard(dto);
		closeCreator();
	}

	private void closeCreator() {
		Stage stage = (Stage) this.getScene().getWindow();
		stage.close();
	};

	private void handleNameUpdate(Event event) {
		lblNameError.setText("");
		if (txfName.getText().isBlank()) {
			btnCreate.setDisable(true);
			lblNameError.setText("Name cannot be empty.");
			return;
		}
		// TODO: check input for SQL injection
		btnCreate.setDisable(false);
	}
}
