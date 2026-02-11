package com.jellerijk.projects.learning.tools.kanban.gui.board;

import java.sql.SQLException;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.domain.stage.StageController;
import com.jellerijk.projects.learning.tools.kanban.domain.stage.StageControllerImpl;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class BoardView extends ScrollPane implements Subscriber {

	HBox stages;
	BoardDTO board;
	StageController sc;
	Button testAdd;

	public BoardView(int boardId) {
		board = BoardController.getInstance().getBoard(boardId);
		sc = new StageControllerImpl(boardId);
		sc.subscribe(this);
		buildGUI();
		update();
	}

	private void buildGUI() {
		BorderPane bp = new BorderPane();
		setContent(bp);

		Label boardName = new Label(board.name());
		boardName.getStyleClass().add("h1");
		bp.setTop(boardName);

		stages = new HBox();
		stages.setSpacing(10);
		bp.setCenter(stages);

		// TODO: remove when done
		createTestButton(bp);

		this.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.setMinWidth(400);
		this.setMaxWidth(600);
	}

	private void createTestButton(BorderPane bp) {
		testAdd = new Button("TEST - ADD STAGE");
		HBox buttons = new HBox(testAdd);
		BorderPane.setAlignment(buttons, Pos.CENTER);
		HBox.setHgrow(testAdd, Priority.ALWAYS);

		testAdd.setOnAction(e -> {
			StageDTO dto = StageDTO.create(sc.countStages(), String.format("Stage %d", sc.countStages()), board.id(),
					"This is a stage", 5);
			try {
				Logger.log(String.format("Creating stage number %d", dto.number()));
				sc.createStage(dto);
				Logger.log("Created a new stage.");
			} catch (SQLException ex) {
				Logger.logError("Creating failed");
				ex.printStackTrace();
			}
		});

		bp.setBottom(buttons);
	}

	@Override
	public void update(PublishedMessageType messageType) {
		stages.getChildren().clear();
		List<StageDTO> stageList = sc.getStages();
		for (StageDTO stage : stageList) {
			stages.getChildren().add(new StageView(stage, sc));
		}
		stages.getChildren().add(testAdd);
	}
}
