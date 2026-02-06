package com.jellerijk.projects.learning.tools.kanban.persistence.inserters;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;

public abstract class TaskInserter {
	private static String sql = "INSERT INTO Task(Description, Stage, Board, Completed) VALUES (?,?,?,?)";

	public static void insert(TaskDTO task) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement(sql);
		executePreparedStatement(pstmt, task);
	}

	public static void insert(Collection<TaskDTO> tasks) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement(sql);
		for (TaskDTO task : tasks)
			executePreparedStatement(pstmt, task);
	}

	private static void executePreparedStatement(PreparedStatement pstmt, TaskDTO task) throws SQLException {
		pstmt.setString(1, task.description());
		pstmt.setInt(2, task.stageNumber());
		pstmt.setInt(3, task.boardId());
		pstmt.setInt(2, task.completed() ? 1 : 0);
		pstmt.execute();
	}
}
