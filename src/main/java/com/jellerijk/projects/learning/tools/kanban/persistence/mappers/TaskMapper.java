package com.jellerijk.projects.learning.tools.kanban.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.domain.Task;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseReadException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseUpdateException;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;

public class TaskMapper implements Mapper<Task> {
	private final DBController dbc;

	private static final String TABLE = "Task";
	private static final String COL_ID = "TaskId";
	private static final String COL_DESCRIPTION = "Description";
	private static final String COL_STAGE = "Stage";
	private static final String COL_BOARD = "BoardId";
	private static final String COL_COMPLETED = "Completed";

	private static final String INSERT_TASK = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)", TABLE,
			COL_DESCRIPTION, COL_STAGE, COL_BOARD, COL_COMPLETED);
	private static final String QUERY_ALL = String.format("SELECT * FROM %s", TABLE);
	private static final String DELETE_TASK = String.format("DELETE FROM %s WHERE %s = ?", TABLE, COL_ID);

	public TaskMapper() {
		this.dbc = DBController.getInstance();
	}

	@Override
	public void insert(Task task) throws DatabaseInsertException {
		throw new UnsupportedOperationException("Needs to be reimplemented.");
//		try (Connection conn = dbc.getConnection();
//				PreparedStatement query = conn.prepareStatement(INSERT_TASK, Statement.RETURN_GENERATED_KEYS)) {
//
//			query.setString(1, task.getDescription());
//			query.setInt(2, task.getStageNumber());
//			query.setInt(3, task.getBoardId());
//			query.setInt(4, task.isCompleted() ? 1 : 0);
//
//			query.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DatabaseInsertException("Failed to insert Task", e);
//		}
	}

	@Override
	public List<Task> getAll() throws DatabaseReadException {
		throw new UnsupportedOperationException("Needs to be reimplemented.");
//		Collection<Task> tasks = new ArrayList<>();
//		try (Connection conn = dbc.getConnection(); PreparedStatement query = conn.prepareStatement(QUERY_ALL)) {
//			ResultSet results = query.executeQuery();
//			tasks = mapResults(results);
//			return tasks;
//		} catch (SQLException e) {
//			Logger.logError("Something went wrong while retrieving all Boards from database.");
//			Logger.logError(e);
//		}
//		throw new DatabaseReadException();
	}

	// HELPER METHODS
	private Collection<Task> mapResults(ResultSet results) throws SQLException {
		throw new UnsupportedOperationException("Needs to be reimplemented.");
//		Collection<Task> tasks = new ArrayList<>();
//		while (results.next()) {
//			int id = results.getInt(COL_ID);
//			String description = results.getString(COL_DESCRIPTION);
//			int stage = results.getInt(COL_STAGE);
//			int board = results.getInt(COL_BOARD);
//			boolean completed = results.getInt(COL_COMPLETED) == 1;
//
//			Task task = new TaskImpl(id, description, board, stage, completed);
//			tasks.add(task);
//		}
//		return tasks;
	}

	@Override
	public void delete(Task task) {
		throw new UnsupportedOperationException("Needs to be reimplemented.");
//		try (Connection conn = dbc.getConnection(); PreparedStatement query = conn.prepareStatement(DELETE_TASK)) {
//			query.setInt(1, task.getId());
//			int rows = query.executeUpdate();
//			String log = rows == 0 ? String.format("No task found with id %d", task.getId())
//					: String.format("Removed task %d from database", task.getId());
//			Logger.log(log);
//		} catch (SQLException e) {
//			Logger.logError("Something went wrong while deleting Task from database.");
//			Logger.logError(e);
//		}

	}

	@Override
	public void update(Task object) throws DatabaseUpdateException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet.");
	}

}
