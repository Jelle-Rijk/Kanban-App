package com.jellerijk.projects.learning.tools.kanban.gui.stage;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class StageView extends AnchorPane implements Subscriber {
	private final static double DEFAULT_WIDTH = 200;
	private final static double DEFAULT_MAX_HEIGHT = 300;
	private String title;
	private VBox taskList;
	private double width;
	private double maxHeight;

	public StageView(StageDTO stage, double width, double maxHeight) {
		this.width = width;
		this.maxHeight = maxHeight;
		title = stage.title();
		buildGUI();
	}

	public StageView(StageDTO stage) {
		this(stage, DEFAULT_WIDTH, DEFAULT_MAX_HEIGHT);
	}

	private void buildGUI() {
		Label lblTitle = new Label(title);
		AnchorPane.setTopAnchor(lblTitle, 5.0);
		AnchorPane.setLeftAnchor(lblTitle, 5.0);

		ScrollPane scrollPane = new ScrollPane();
		AnchorPane.setTopAnchor(scrollPane, 30.0);
		AnchorPane.setBottomAnchor(scrollPane, 50.0);
		scrollPane.setContent(taskList);
		scrollPane.setMinWidth(width);
		scrollPane.setFitToHeight(true);
		scrollPane.setPrefHeight(Double.MAX_VALUE);
		scrollPane.setMaxHeight(maxHeight);

		Button btnAdd = new Button("Add task");
		btnAdd.setPrefWidth(width);
		AnchorPane.setBottomAnchor(btnAdd, 5.0);

		getChildren().addAll(lblTitle, scrollPane, btnAdd);

	}

	@Override
	public void update(PublishedMessageType messageType) {
		// TODO Auto-generated method stub
		
	}

}
