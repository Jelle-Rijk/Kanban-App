package com.jellerijk.projects.learning.tools.kanban.gui.board;

import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.domain.stage.StageController;
import com.jellerijk.projects.learning.tools.kanban.domain.task.TaskController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class StageView extends VBox implements Subscriber {
	private final StageController sc;
	private final TaskController tc;

	private StageDTO data;

	private StageHeader header;
	private ScrollPane spTasks;
	private VBox taskList;
	private Button btnAddTask;

	public StageView(StageDTO data, StageController sc, TaskController tc) {
		this.data = data;
		this.sc = sc;
		this.tc = tc;
		tc.subscribe(this);

		sc.subscribeToStage(this, data.number(), data.boardId());

		buildGUI();
		updateStageData();
		updateTaskList();
	}

//	BUILD GUI
	private void buildGUI() {
		header = new StageHeader(this);
		taskList = new VBox();
		spTasks = new ScrollPane();
		spTasks.setContent(taskList);
		spTasks.setPrefHeight(400);
		spTasks.setMaxHeight(400);
		Node footer = buildFooter();

		getChildren().addAll(header, spTasks, footer);

	};

	// TODO: design footer
	private Node buildFooter() {
		btnAddTask = new Button("Add task");
		btnAddTask.setOnAction(e -> handleAddTask());
		return btnAddTask;
	}

//	GUI SETTERS

	public void setTitle(String title) {
		header.setTitle(title);
	}

//	private void setStageNumber(int stageNumber) {
//		if (stageNumber < 0)
//			throw new IllegalArgumentException("StageView cannot set the stage number. Needs to be positive.");
//		this.stageNumber = stageNumber;
//	}

//	CONTROLLER INTERACTION
	private void handleAddTask() {
		TaskCard card = new TaskCard(tc, data);
		taskList.getChildren().add(card);
	}

	public void handleRename(String title) {
		setTitle(title);
		sc.renameStage(data.number(), data.boardId(), title);
	}

	public void handleDelete() {
		sc.deleteStage(data.number(), data.boardId());
	}

	private void updateTaskList() {
		taskList.getChildren().clear();
		List<TaskDTO> tasks = tc.getTasksForStage(data.number(), data.boardId());
		for (TaskDTO task : tasks) {
			taskList.getChildren().add(new TaskCard(tc, task.id(), data));
		}
	}

	private void updateStageData() {
		data = sc.getStage(data.number(), data.boardId());
		setTitle(data.title());
	}

	@Override
	public void update(PublishedMessageType messageType) {
		if (messageType == PublishedMessageType.REPO_UPDATE) {
			updateTaskList();
		} else {
			updateStageData();
		}
	}
}
