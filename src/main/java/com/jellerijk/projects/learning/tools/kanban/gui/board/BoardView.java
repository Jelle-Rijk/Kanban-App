package com.jellerijk.projects.learning.tools.kanban.gui.board;

import com.jellerijk.projects.learning.tools.kanban.domain.DomainController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class BoardView extends ScrollPane implements Subscriber {

	private final DomainController controller;
	private HBox stages;
	private BoardDTO board;

	private Node addStageButton;

	private BorderPane bp;

	public BoardView(DomainController controller) {
		this.controller = controller;
		board = controller.getSelectedBoard();
		buildGUI();
		update();
	}

	private void buildGUI() {
		bp = new BorderPane();
		setContent(bp);

		bp.setTop(buildHeader());

		buildStageList();
		bp.setCenter(stages);
		buildAddStageButton();

		this.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.setMinWidth(400);
		this.setMaxWidth(600);
	}

	private Node buildHeader() {
		VBox header = new VBox();

		Label boardName = new Label(board.title());
		boardName.getStyleClass().add("h1");
		boardName.setTooltip(new Tooltip(String.format("Board #%d", board.id())));

		Label boardDescription = new Label(board.description());

		header.getChildren().addAll(boardName, boardDescription);

		return header;
	}

	private void buildStageList() {
		stages = new HBox();
		stages.setSpacing(10);
	}

	private Node buildEmptyStageList() {
		Label lbl = new Label("Empty board.");
		lbl.setOnMouseClicked((e) -> handleAddStage());
		return lbl;
	}

	private void buildAddStageButton() {
		BorderPane pane = new BorderPane();
		pane.setBackground(Background.fill(Paint.valueOf("lightblue")));
		pane.setPrefHeight(400);
		pane.setMinHeight(400);
		pane.setPrefWidth(250);

		pane.setStyle("-fx-background-radius: 8; -fx-background-color: lightblue;");
		pane.setOpacity(0.7);

		pane.setOnMouseEntered(e -> {
			pane.setOpacity(1);
			pane.setScaleX(1.005);
			pane.setScaleY(1.005);
		});
		pane.setOnMouseExited(e -> {
			pane.setOpacity(0.7);
			pane.setScaleX(1);
			pane.setScaleY(1);
		});
		pane.setOnMouseClicked(e -> handleAddStage());

		Label lblPlus = new Label("+");
		lblPlus.setStyle("-fx-font-size: 48");
		pane.setCenter(lblPlus);

		addStageButton = pane;
	}

	private void handleAddStage() {
		throw new UnsupportedOperationException("This needs to be reimplemented.");
//		int numberOfStages = sc.countStages(board.id());
//		String name = String.format("Stage %d", numberOfStages + 1);
//		try {
//			sc.createStage(numberOfStages + 1, board.id(), name);
//		} catch (SQLException e) {
//			Logger.logError("Exception while adding stage");
//			e.printStackTrace();
//		}
//		update();
	}

	@Override
	public void update(PublishedMessageType messageType) {
		throw new UnsupportedOperationException("This needs to be reimplemented.");
//		stages.getChildren().clear();
//		List<StageDTO> stageList = sc.getStages();
//		if (stageList.size() == 0) {
//			bp.setCenter(buildEmptyStageList());
//			return;
//		}
//		bp.setCenter(stages);
//		for (StageDTO stage : stageList) {
//			stages.getChildren().add(new StageView(stage, sc, tc));
//		}
//		stages.getChildren().add(addStageButton);
	}
}
