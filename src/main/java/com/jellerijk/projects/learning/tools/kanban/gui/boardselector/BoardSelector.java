package com.jellerijk.projects.learning.tools.kanban.gui.boardselector;

import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardController;
import com.jellerijk.projects.learning.tools.kanban.gui.boardCreator.BoardCreator;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BoardSelector extends AnchorPane implements Subscriber {
	private final BoardController bc;

	private VBox boardCards;
	private Button btnAdd;

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
		AnchorPane.setBottomAnchor(scrollPane, (double) 40);
		AnchorPane.setLeftAnchor(scrollPane, (double) 10);
		AnchorPane.setRightAnchor(scrollPane, (double) 10);
		this.getChildren().add(scrollPane);

		Button btnAdd = new Button("Add board");
		btnAdd.setOnAction(e -> openBoardCreator(e));
		this.getChildren().add(btnAdd);
		AnchorPane.setBottomAnchor(btnAdd, (double) 5);
		AnchorPane.setLeftAnchor(btnAdd, (double) 5);

		this.setPadding(new Insets(16));
		update();
	}

	@Override
	public void update(PublishedMessageType messageType) {
		boardCards.getChildren().clear();
		List<BoardDTO> boards = bc.getBoards();
		boards.forEach(board -> boardCards.getChildren().add(new BoardCard(bc, board.id())));
	}

	private void openBoardCreator(ActionEvent event) {
		Logger.log("Opening boardCreator");
		BoardCreator root = new BoardCreator();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Create New Board");
		stage.show();
	};

}
