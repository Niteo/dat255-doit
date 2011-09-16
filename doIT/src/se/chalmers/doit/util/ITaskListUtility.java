package se.chalmers.doit.util;

import java.util.List;

import se.chalmers.doit.core.ITask;

public interface ITaskListUtility {

	public List<ITask> sortTasks(List<ITask> list, ISortingStrategy primary,
			ISortingStrategy secondary, ISortingStrategy tertiary);

}
