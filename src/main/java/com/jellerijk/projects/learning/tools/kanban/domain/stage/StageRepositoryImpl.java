package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.util.ArrayList;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseUpdateException;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.mappers.StageMapper;

public class StageRepositoryImpl implements StageRepository {
	private StageMapper mapper;
	private List<Stage> stages;

	public StageRepositoryImpl() {
		mapper = new StageMapper();
		setStages((ArrayList<Stage>) mapper.getAll());
	};

	@Override
	public void setStages(List<Stage> stages) {
		if (stages == null)
			throw new IllegalArgumentException("Supplied Stages repository was null.");
		this.stages = stages;
	}

	@Override
	public int add(Stage stage) {
		try {
			int id = mapper.insert(stage);
			stages.add(stage);
			return id;
		} catch (Exception ex) {
			throw new DatabaseInsertException("An exception occured while adding a new Stage.", ex);
		}
	}

	@Override
	public void remove(Stage stage) {
		try {
			mapper.delete(stage);
			stages.remove(stage);
		} catch (Exception ex) {
			throw new DatabaseUpdateException("An exception occured while deleting a Stage", ex);
		}
	}

	@Override
	public Stage getStage(int id) {
		return stages.stream().filter(stage -> stage.getId() == id).findFirst().orElseThrow();
	}

	@Override
	public List<Stage> getStages() {
		return stages;
	}

	@Override
	public void rename(int id, String name) {
		try {
			Stage stage = getStage(id);
			mapper.updateTitle(stage, name);
			stage.setTitle(name);
		} catch (Exception ex) {
			Logger.logError(ex);
		}
	}

	@Override
	public void changeStageNumber(int id, int newStageNumber) {
		throw new UnsupportedOperationException();
	}

}
