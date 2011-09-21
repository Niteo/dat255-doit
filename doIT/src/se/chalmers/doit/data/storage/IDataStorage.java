package se.chalmers.doit.data.storage;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;

public interface IDataStorage {

	public void addList(ITaskCollection collection);

	public void addLists(Collection<ITaskCollection> collection);

	public void addTask(ITask task, ITaskCollection collection);

	public void addTasks(Collection<ITask> tasks, ITaskCollection collection);

	public void clearData();

	public void editList(ITaskCollection oldCollection,
			ITaskCollection newCollection);

	public void editTask(ITask oldTask, ITask newTask);

	public Collection<ITaskCollection> getAllLists();

	public Collection<ITask> getAllTasks();

	public void moveTask(ITask task, ITaskCollection collection);

	public void removeList(ITaskCollection collection);

	public void removeLists(Collection<ITaskCollection> collection);

	public void removeTask(ITask task);

	public void removeTasks(Collection<ITask> listOfTasksToRemove);

}
