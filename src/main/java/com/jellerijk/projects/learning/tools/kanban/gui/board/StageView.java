package com.jellerijk.projects.learning.tools.kanban.gui.board;

import com.jellerijk.projects.learning.tools.kanban.domain.stage.StageController;
import com.jellerijk.projects.learning.tools.kanban.domain.task.TaskController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class StageView extends VBox implements Subscriber {
	private final StageController sc;
	private final TaskController tc;
	private int stageNumber;

	private StageHeader header;
	private ScrollPane spTasks;
	private VBox taskList;
	private Button btnAddTask;

	public StageView(StageDTO stage, StageController sc, TaskController tc) {
		this.sc = sc;
		this.tc = tc;
		this.stageNumber = stage.number();

		sc.subscribeToStage(this, stageNumber);

		buildGUI();
		update();
	}

//	BUILD GUI
	private void buildGUI() {
		header = new StageHeader(this);
		taskList = new VBox();
		spTasks = new ScrollPane();
		spTasks.setContent(taskList);
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
		//TODO add auto-scrolling
		TaskCard card = new TaskCard(tc, stageNumber);
		taskList.getChildren().add(card);
	}

	public void handleRename(String title) {
		setTitle(title);
		sc.renameStage(stageNumber, title);
	}

	public void handleDelete() {
		sc.deleteStage(stageNumber);
	}

	@Override
	public void update(PublishedMessageType messageType) {
		StageDTO data = sc.getStage(stageNumber);
		setTitle(data.title());
	}
}
