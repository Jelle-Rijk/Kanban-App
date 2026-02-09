package com.jellerijk.projects.learning.tools.kanban.gui.stage;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
	private Label lblDelete;

	public StageHeader(StageView parent) {
		this.parent = parent;
		buildGUI();
	}

	private void buildGUI() {
		// CONTROLS
		txfTitle = new TextField();
		lblEdit = new Label();
		lblDelete = new Label("🗑️");

		// LISTENERS
		txfTitle.setOnAction(e -> handleRename());
		lblDelete.setOnMouseClicked(e -> handleDelete());
		setOnMouseEntered(e -> showIcons(true));
		setOnMouseExited(e -> showIcons(false));

		// STYLING
		txfTitle.getStyleClass().addAll("transparent", "stageheader__title");
		lblEdit.getStyleClass().add("stageheader__editicon");
		lblDelete.getStyleClass().add("stageheader__editicon");

		// LAYOUT
		HBox.setHgrow(txfTitle, Priority.SOMETIMES);
		this.setSpacing(3);

		getChildren().addAll(txfTitle, lblEdit, lblDelete);

		// INITIAL STATE
		lock();
		showIcons(false);
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
		txfTitle.requestFocus();
	}

	public void lock() {
		lblEdit.setText(EDIT_ICON);

		lblEdit.setOnMouseClicked(e -> unlock());

		txfTitle.setEditable(false);
		txfTitle.setFocusTraversable(false);
	}

	private void showIcons(boolean visible) {
		lblEdit.setVisible(visible);
		lblDelete.setVisible(visible);
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

	private void handleDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete stage");
		alert.setHeaderText(String.format("Deleting %s", txfTitle.getText()));
		alert.setContentText("This action cannot be undone.\nAre you sure you want to do this?");

		Optional<ButtonType> response = alert.showAndWait();

		if (response.isPresent() && response.get() == ButtonType.OK) {
			parent.handleDelete();
		}
	}
}
