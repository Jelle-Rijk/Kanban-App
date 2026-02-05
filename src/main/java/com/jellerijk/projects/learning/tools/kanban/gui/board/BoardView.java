package com.jellerijk.projects.learning.tools.kanban.gui.board;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

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
		
		HBox stages = new HBox();
		bp.setCenter(stages);
		
	}
}
