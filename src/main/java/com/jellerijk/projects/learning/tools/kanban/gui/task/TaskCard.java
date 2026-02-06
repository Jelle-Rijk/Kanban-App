package com.jellerijk.projects.learning.tools.kanban.gui.task;

import com.jellerijk.projects.learning.tools.kanban.domain.task.TaskController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TaskCard extends VBox implements Subscriber {
	private TaskController tc;

	// Data
	private final int taskId;
	private String description;

	// JavaFX controls
	private TextField txfDescription;

	public TaskCard(int taskId, TaskController tc) {
		if (tc == null)
			throw new IllegalArgumentException("TaskController is null.");
		this.taskId = taskId;
		buildGUI();
	}

	private void buildGUI() {
		txfDescription = new TextField();
		getChildren().add(txfDescription);

	}

	/**
	 * Sets this TaskCard's description.
	 * 
	 * @param description
	 */
	private void setDescription(String description) {
		if (description.equals(this.description))
			return;
		this.description = description;
		txfDescription.setText(description);
	}

	@Override
	public void update(PublishedMessageType messageType) {
		TaskDTO data = tc.getTask(taskId);
		setDescription(data.description());
	};

}
