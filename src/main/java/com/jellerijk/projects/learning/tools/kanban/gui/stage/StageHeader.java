package com.jellerijk.projects.learning.tools.kanban.gui.stage;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class StageHeader extends HBox {
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
		lblEdit = new Label("⚙️");
		
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
		txfTitle.setEditable(true);
		txfTitle.setFocusTraversable(true);
		lblEdit.setVisible(false);
	}

	public void lock() {
		txfTitle.setEditable(false);
		txfTitle.setFocusTraversable(false);

		lblEdit.setVisible(true);
	}

	// HANDLERS
	private void handleRename() {
		lock();
		parent.handleRename(txfTitle.getText());
	}
}
