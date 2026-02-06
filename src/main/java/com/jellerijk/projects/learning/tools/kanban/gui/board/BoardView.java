package com.jellerijk.projects.learning.tools.kanban.gui.board;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.gui.stage.StageView;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BoardView extends ScrollPane {

	BoardDTO board;

	public BoardView(int boardId) {
		board = BoardController.getInstance().getBoard(boardId);
		buildGUI();
	}

	private void buildGUI() {
		BorderPane bp = new BorderPane();
		setContent(bp);

		bp.setTop(new Label(board.name()));

		HBox stages = new HBox();
		stages.setSpacing(10);
		bp.setCenter(stages);
		StageDTO testDTO = new StageDTO(1, "Stage 1", -1, "List to test with.", 3);
		StageDTO testDTO2 = new StageDTO(2, "Stage 2", -1, "List to test with.", 3);
//		StageDTO testDTO3 = new StageDTO(3, "Stage 2", -1, "List to test with.", 3);
//		StageDTO testDTO4 = new StageDTO(4, "Stage 2", -1, "List to test with.", 3);
//		StageDTO testDTO5 = new StageDTO(5, "Stage 2", -1, "List to test with.", 3);
//		StageDTO testDTO6 = new StageDTO(6, "Stage 2", -1, "List to test with.", 3);
		stages.getChildren().add(new StageView(testDTO));
		stages.getChildren().add(new StageView(testDTO2));
//		stages.getChildren().add(new StageView(testDTO3));
//		stages.getChildren().add(new StageView(testDTO4));
//		stages.getChildren().add(new StageView(testDTO5));
//		stages.getChildren().add(new StageView(testDTO6));

		this.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.setMaxWidth(600);
	}
}
