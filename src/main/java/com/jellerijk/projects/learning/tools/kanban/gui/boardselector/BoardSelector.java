package com.jellerijk.projects.learning.tools.kanban.gui.boardselector;

import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class BoardSelector extends AnchorPane implements Subscriber {
	private final BoardController bc;
	private VBox boardCards;

	public BoardSelector() {
		bc = BoardController.getInstance();
		bc.subscribe(this);
		buildGUI();
		bc.addBoard(new BoardDTO(1, "Awesome Board", "This is a super awesome board, I swear."));
		bc.addBoard(new BoardDTO(2, "Awesome Board", "This is a super awesome board, I swear."));
		bc.addBoard(new BoardDTO(3, "Awesome Board", "This is a super awesome board, I swear."));
		bc.addBoard(new BoardDTO(4, "Awesome Board", "This is a super awesome board, I swear."));
	}

	private void buildGUI() {

		boardCards = new VBox();
		ScrollPane scrollPane = new ScrollPane(boardCards);
		scrollPane.setFitToWidth(true);
		scrollPane.setMaxHeight(200);
		AnchorPane.setTopAnchor(scrollPane, (double) 10);
		AnchorPane.setBottomAnchor(scrollPane, (double) 10);
		AnchorPane.setLeftAnchor(scrollPane, (double) 10);
		AnchorPane.setRightAnchor(scrollPane, (double) 10);
		this.getChildren().add(scrollPane);

		this.setPadding(new Insets(16));
		update();
	}

	@Override
	public void update(PublishedMessageType messageType) {
		Logger.log("Boardselector updated");
		boardCards.getChildren().clear();
		List<BoardDTO> boards = bc.getBoards();
		boards.forEach(board -> boardCards.getChildren().add(new BoardCard(bc, board.id())));
	}

}
