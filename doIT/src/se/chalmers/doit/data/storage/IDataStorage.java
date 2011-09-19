package se.chalmers.doit.data.storage;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;

public interface IDataStorage {

	public void addList(ITaskCollection collection);

	public void addTask(ITask task, ITaskCollection collection);

	public void editList(ITaskCollection oldCollection,
			ITaskCollection newCollection);

	public void editTask(ITask oldTask, ITask newTask);

	public Collection<ITaskCollection> getLists();

	public void removeList(ITaskCollection collection);

	public void removeTask(ITask task);

	public void removeTasks(Collection<ITask> listOfTasksToRemove);

}
