package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.inserters.StageInserter;
import com.jellerijk.projects.learning.tools.kanban.persistence.loaders.StageLoader;

public class StageControllerImpl implements StageController {
	private final int boardId;
	private final StageRepository stageRepo;

	public StageControllerImpl(int boardId) {
		if (boardId < 0)
			throw new IllegalArgumentException(String.format("Supplied boardId for StageController was %d", boardId));
		this.boardId = boardId;
		stageRepo = new StageRepositoryImpl(initStageRepoList());
	}

	private List<Stage> initStageRepoList() {
		List<Stage> stages = new ArrayList<Stage>();
		try {
			Collection<StageDTO> stageDTOs = StageLoader.loadByBoard(boardId);
			stageDTOs.forEach(dto -> stages
					.add(new StageImpl(dto.number(), dto.boardId(), dto.title(), dto.description(), dto.limit())));
		} catch (SQLException e) {
			Logger.logError(String.format("An error occurred while retrieving the Stages for board %d", boardId));
		}
		return stages;
	}

	@Override
	public int getBoardId() {
		return boardId;
	}

	@Override
	public void createStage(StageDTO data) throws SQLException {
		StageInserter.insert(data);
	}

	@Override
	public void deleteStage(int stageNumber) {
		stageRepo.remove(boardId, stageNumber);
	}

}
