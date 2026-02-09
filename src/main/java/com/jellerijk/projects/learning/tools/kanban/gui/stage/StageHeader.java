package com.jellerijk.projects.learning.tools.kanban.gui.stage;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class StageHeader extends HBox {
	private final static String EDIT_ICON = "⚙️";
	private final static String CANCEL_ICON = "x";
	private final StageView parent;

	private TextField txfTitle;
	private Label lblEdit;

	public StageHeader(StageView parent) {
		this.parent = parent;
		buildGUI();
	}

	private void buildGUI() {
		// CONTROLS
		txfTitle = new TextField();
		lblEdit = new Label();

		// LISTENERS
		lblEdit.setOnMouseClicked(e -> unlock());
		txfTitle.setOnAction(e -> handleRename());
		lock();

		// STYLING
		txfTitle.getStyleClass().addAll("transparent", "stageheader__title");
		lblEdit.getStyleClass().add("stageheader__editicon");

		// LAYOUT
		HBox.setHgrow(txfTitle, Priority.SOMETIMES);
		this.setSpacing(3);

		getChildren().addAll(txfTitle, lblEdit);
	}

	public void setTitle(String title) {
		txfTitle.setText(title);
	}

	// GUI STATE

	public void unlock() {
		lblEdit.setText(CANCEL_ICON);

		String oldTitle = txfTitle.getText();
		lblEdit.setOnMouseClicked(e -> cancel(oldTitle));

		txfTitle.setEditable(true);
		txfTitle.setFocusTraversable(true);

	}

	public void lock() {
		lblEdit.setText(EDIT_ICON);

		lblEdit.setOnMouseClicked(e -> unlock());

		txfTitle.setEditable(false);
		txfTitle.setFocusTraversable(false);
	}

	// HANDLERS
	private void handleRename() {
		lock();
		parent.handleRename(txfTitle.getText());
	}

	private void cancel(String oldTitle) {
		lock();
		setTitle(oldTitle);
	}
}
