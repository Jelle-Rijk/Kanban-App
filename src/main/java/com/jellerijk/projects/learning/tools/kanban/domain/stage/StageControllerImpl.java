package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class StageControllerImpl implements StageController {
	private static StageController instance;

	private final StageRepository stageRepo;
	private List<Subscriber> subs;

	private StageControllerImpl() {
		this.subs = new ArrayList<Subscriber>();
		stageRepo = new StageRepositoryImpl();
	}

	public static StageController getInstance() {
		if (instance == null)
			instance = new StageControllerImpl();
		return instance;

	}

	@Override
	public void createStage(int number, int boardId, String title) throws SQLException {
		Stage stage = new StageImpl(number, boardId, title);
		stageRepo.add(stage);
		notifySubs();
	}

	@Override
	public void deleteStage(int id) {
		Stage stage = stageRepo.getStage(id);
		stageRepo.remove(stage);

		// Renumber stages
		List<Stage> stages = stageRepo.getStages().stream().sorted(Comparator.comparing(Stage::getNumber))
				.collect(Collectors.toCollection(ArrayList::new));
		for (int i = 0; i < stages.size(); i++)
			stages.get(i).setNumber(i);

		notifySubs();
	}

	@Override
	public List<StageDTO> getStages() {
		List<Stage> stages = stageRepo.getStages();
		return stages.stream().map(stage -> StageDTO.convert(stage)).collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public StageDTO getStage(int id) {
		return StageDTO.convert(stageRepo.getStage(id));
	}

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subs;
	}

	@Override
	public void renameStage(int id, String title) {
		stageRepo.rename(id, title);
	}

	@Override
	public void subscribeToStage(Subscriber sub, int id) {
		Stage stage = stageRepo.getStage(id);
		stage.subscribe(sub);
	}

	@Override
	public int countStages(int boardId) {
		return stageRepo.getStages().size();
	}

}
