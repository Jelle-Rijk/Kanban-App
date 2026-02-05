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
		bp.setCenter(stages);
		StageDTO testDTO = new StageDTO(1, "Testlist", -1, "List to test with.", 3);
		StageDTO testDTO2 = new StageDTO(2, "Stage 2", -1, "List to test with.", 3);
		stages.getChildren().add(new StageView(testDTO));
		stages.getChildren().add(new StageView(testDTO2));

	}
}
