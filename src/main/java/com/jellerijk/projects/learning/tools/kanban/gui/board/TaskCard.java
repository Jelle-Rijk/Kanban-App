package com.jellerijk.projects.learning.tools.kanban.gui.board;

import com.jellerijk.projects.learning.tools.kanban.domain.task.TaskController;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class TaskCard extends VBox {
	private final TaskController tc;
	private int taskId;
	private StageDTO stageData;

	private State currState;

	private TextField txfDescription;
	private Label editIcon;

	public TaskCard(TaskController tc, StageDTO stageData) {
		this(tc, -1, stageData);
	}

	public TaskCard(TaskController tc, int taskId, StageDTO stageData) {
		this.taskId = taskId;
		this.stageData = stageData;
		this.tc = tc;
		buildGUI();
		switchToState(taskId == -1 ? State.CREATING : State.LOCKED);
	};

	private void buildGUI() {
		// CREATE COMPONENTS
		txfDescription = new TextField();
		editIcon = new Label("🖊️");

		// SET LISTENERS
		txfDescription.setOnMouseClicked(event -> handleDescriptionClick(event));
		this.setOnMouseEntered(e -> showEditIcon(true));
		this.setOnMouseExited(e -> showEditIcon(false));

		// STYLING
		txfDescription.getStyleClass().add("transparent");
	}

	private void makeDescriptionEditable(boolean editable) {
		txfDescription.setEditable(editable);
		txfDescription.setFocusTraversable(editable);
		if (editable)
			Platform.runLater(() -> txfDescription.requestFocus());
	};

	private void showEditIcon(boolean show) {
		editIcon.setVisible(show);
	};

	// EVENT HANDLERS
	private void handleDescriptionClick(MouseEvent event) {
		if (currState != State.LOCKED || event.getClickCount() < 2)
			return;
		switchToState(State.EDITING);
	}

	private void handleCreateTask() {
		String description = txfDescription.getText();
		try {
			this.taskId = tc.createTask(description, stageData.boardId(), stageData.number());
			switchToState(State.LOCKED);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleRenameTask() {
		Logger.logDebug("Rename task");
	}

	// STATE

	private void setCurrState(State currState) {
		if (currState == null)
			throw new IllegalArgumentException("Passed null to currState");
		this.currState = currState;
	}

	private void switchToState(State state) {
		setCurrState(state);
		switch (currState) {
		case State.LOCKED -> initLockedState();
		case State.CREATING -> initCreatingState();
		case State.EDITING -> initEditingState();
		}
	}

	private void initLockedState() {
		makeDescriptionEditable(false);
		TaskDTO data = tc.getTask(this.taskId);
		txfDescription.setText(data.description());
	}

	private void initCreatingState() {
		makeDescriptionEditable(true);
		txfDescription.setOnAction((evt) -> handleCreateTask());
	}

	private void initEditingState() {
		makeDescriptionEditable(true);
		txfDescription.setOnAction((evt) -> handleRenameTask());
	}

	private enum State {
		LOCKED, CREATING, EDITING
	}
}
