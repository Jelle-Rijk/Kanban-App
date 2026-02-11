package com.jellerijk.projects.learning.tools.kanban.gui.board;

import com.jellerijk.projects.learning.tools.kanban.domain.task.TaskController;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TaskCard extends HBox implements Subscriber {
	private final TaskController tc;

	private GUICardState state;
	private int taskId;
	private int currentStage;

	private TextField txfDescription;
	private Button btnPrevStage;
	private Button btnNextStage;

	/**
	 * Adds an empty TaskCard that needs to be filled in.
	 * 
	 * @param tc
	 */
	public TaskCard(TaskController tc, int currentStage) {
		this(tc, currentStage, -1, GUICardState.CREATING);
	}

	public TaskCard(TaskController tc, int currentStage, int taskId) {
		this(tc, currentStage, taskId, GUICardState.DEFAULT);
	}

	public TaskCard(TaskController tc, int currentStage, int taskId, GUICardState state) {
		setState(state);
		this.tc = tc;
		this.currentStage = currentStage;
		buildGUI();
		if (this.state == GUICardState.CREATING)
			unlock();
		else {
			setTaskId(taskId);
			lock();
			update();
		}
	}

//	BUILD GUI
	private void buildGUI() {
		txfDescription = new TextField();
		btnPrevStage = new Button();
		btnNextStage = new Button();

		btnPrevStage.setOnAction(e -> handleMoveStage(currentStage - 1));
		btnNextStage.setOnAction(e -> handleMoveStage(currentStage + 1));

		getChildren().addAll(btnPrevStage, txfDescription, btnNextStage);
	}

//	SETTERS - GETTERS
	private void setDescription(String description) {
		if (description == null || description.isBlank())
			throw new IllegalArgumentException("Task description was blank.");
		txfDescription.setText(description);
	};

	private String getDescription() {
		return txfDescription.getText();
	}

	private void setTaskId(int taskId) {
		this.taskId = taskId;
	}

//	EVENT HANDLERS
	private void handleChangeName() {
		Logger.logDebug(this, "Changing task name.");
	}

	private void handleCreateTask() {
		Logger.logDebug(this, "Creating Task");
	}

	private void handleMoveStage(int newStage) {
		Logger.logDebug(this, String.format("Trying to move to stage %d", newStage));
	}

	private void lock() {
		Logger.logDebug(this, "Locking task");
	}

	private void unlock() {
		Logger.logDebug(this, "Unlocking task");
	}

	public void setState(GUICardState state) {
		if (state == null)
			throw new IllegalArgumentException("TaskCard state cannot be null");
		this.state = state;
	}

//	UPDATES

	@Override
	public void update(PublishedMessageType messageType) {
		TaskDTO data = tc.getTask(taskId);
	}
}
