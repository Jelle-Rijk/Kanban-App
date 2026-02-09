package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.inserters.StageInserter;
import com.jellerijk.projects.learning.tools.kanban.persistence.loaders.StageLoader;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class StageControllerImpl implements StageController {
	private final int boardId;
	private final StageRepository stageRepo;
	private List<Subscriber> subs;

	public StageControllerImpl(int boardId) {
		if (boardId < 0)
			throw new IllegalArgumentException(String.format("Supplied boardId for StageController was %d", boardId));
		this.boardId = boardId;
		this.subs = new ArrayList<Subscriber>();
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
			e.printStackTrace();
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
		Stage stage = new StageImpl(data.number(), data.boardId(), data.title(), data.description(), data.limit());
		stageRepo.add(stage);
		notifySubs();
	}

	@Override
	public void deleteStage(int stageNumber) {
		Stage stage = stageRepo.getStage(boardId, stageNumber);
		stageRepo.remove(stage);
		stage.delete();

		// Renumber stages
		List<Stage> stages = stageRepo.getStages().stream().sorted(Comparator.comparing(Stage::getNumber))
				.collect(Collectors.toCollection(ArrayList::new));
		for (int i = 0; i < stages.size(); i++)
			stages.get(i).changeNumber(i);

		notifySubs();
	}

	@Override
	public List<StageDTO> getStages() {
		List<Stage> stages = stageRepo.getStages();
		return stages.stream().map(stage -> StageDTO.convert(stage)).collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public StageDTO getStage(int stageNumber) {
		return StageDTO.convert(stageRepo.getStage(boardId, stageNumber));
	}

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subs;
	}

	@Override
	public void renameStage(int stageNumber, String title) {
		Stage stage = stageRepo.getStage(boardId, stageNumber);
		stage.rename(title);
	}

	@Override
	public void subscribeToStage(Subscriber sub, int stageNumber) {
		Stage stage = stageRepo.getStage(boardId, stageNumber);
		stage.subscribe(sub);
	}

	@Override
	public int countStages() {
		return stageRepo.getStages().size();
	}

}
