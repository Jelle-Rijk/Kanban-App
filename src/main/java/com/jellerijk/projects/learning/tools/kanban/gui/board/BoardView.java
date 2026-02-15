package com.jellerijk.projects.learning.tools.kanban.gui.board;

import java.sql.SQLException;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.domain.stage.StageController;
import com.jellerijk.projects.learning.tools.kanban.domain.stage.StageControllerImpl;
import com.jellerijk.projects.learning.tools.kanban.domain.task.TaskController;
import com.jellerijk.projects.learning.tools.kanban.domain.task.TaskControllerImpl;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
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

	private HBox stages;
	private BoardDTO board;
	private final StageController sc;
	private final TaskController tc;

	private Node addStageButton;

	private BorderPane bp;

	public BoardView(int boardId) {
		board = BoardController.getInstance().getBoard(boardId);
		sc = new StageControllerImpl(boardId);
		tc = new TaskControllerImpl(boardId);
		sc.subscribe(this);
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

		Label boardName = new Label(board.name());
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
		int numberOfStages = sc.countStages();
		String name = String.format("Stage %d", numberOfStages + 1);
		try {
			sc.createStage(numberOfStages + 1, board.id(), name);
		} catch (SQLException e) {
			Logger.logError("Exception while adding stage");
			e.printStackTrace();
		}
		update();
	}

	@Override
	public void update(PublishedMessageType messageType) {
		stages.getChildren().clear();
		List<StageDTO> stageList = sc.getStages();
		if (stageList.size() == 0) {
			bp.setCenter(buildEmptyStageList());
			return;
		}
		bp.setCenter(stages);
		for (StageDTO stage : stageList) {
			stages.getChildren().add(new StageView(stage, sc, tc));
		}
		stages.getChildren().add(addStageButton);
	}
}
