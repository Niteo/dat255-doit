package se.chalmers.doit.logic.controller;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;

/**
 * An interface to delegate instructions from the GUI to the logic layer.
 *
 * @author Boel
 *
 */
public interface ILogicController {

	/**
	 * Adds a list to the data storage.
	 *
	 * @param collection
	 *            The ITaskCollection to add
	 * @return true if the list was successfully added, false if not
	 */
	public boolean addList(ITaskCollection taskCollection);

	/**
	 * Adds several lists to the data storage.
	 *
	 * @param collection
	 *            The lists to add
	 * @return the number of lists that were successfully added
	 */
	public int addLists(Collection<ITaskCollection> collection);

	/**
	 * Adds a task to the data storage.
	 *
	 * @param task
	 *            The task to add
	 * @param collection
	 *            The list to add the task to
	 * @return true if the task was successfully added, false if not
	 */
	public boolean addTask(ITask task, ITaskCollection collection);

	/**
	 * Adds several tasks to the data storage.
	 *
	 * @param tasks
	 *            The tasks to add
	 * @param collection
	 *            The TaskCollection to add the tasks to
	 * @return the number tasks that were successfully added
	 */
	public int addTasks(Collection<ITask> tasks, ITaskCollection collection);

	/**
	 * Clears all data from the data storage
	 */
	public void clearData();

	/**
	 * Edits a list.
	 *
	 * @param oldCollection
	 *            The old ITaskCollection
	 * @param newCollection
	 *            The new ITaskCollection
	 * @return true if the list was successfully edited, false if not
	 */
	public boolean editList(ITaskCollection oldCollection,
			ITaskCollection newCollection);

	/**
	 * Edits a task.
	 *
	 * @param oldTask
	 *            The old task
	 * @param newTask
	 *            The new task
	 * @return true if the task was successfully edited, false if not
	 */
	public boolean editTask(ITask oldTask, ITask newTask);

	/**
	 * Edits several tasks.
	 *
	 * @param oldCollection
	 *            The old ITaskCollection
	 * @param newCollection
	 *            The new ITaskCollection
	 * @return true if the tasks were successfully edited, false if not
	 */
	public boolean editTasks(ITaskCollection oldCollection,
			ITaskCollection newCollection);

	/**
	 * Returns all lists.
	 *
	 * @return all ITaskCollections in the data storage
	 */
	public Collection<ITaskCollection> getAllLists();

	/**
	 * Returns all tasks.
	 *
	 * @return all ITasks in the data storage
	 */
	public Collection<ITask> getAllTasks();

	/**
	 * Moves a task to a new location.
	 *
	 * @param task
	 *            The task to move
	 * @param taskCollection
	 *            The location to move the task to
	 *
	 * @return true if the task was moved successfully, false if not
	 */
	public boolean moveTask(ITask task, ITaskCollection taskCollection);

	/**
	 * Removes a list from the data storage.
	 *
	 * @param collection
	 *            The list to remove
	 *
	 * @return true if the list was removed successfully, false if not
	 */
	public boolean removeList(ITaskCollection collection);

	/**
	 * Removes several lists from the data storage.
	 *
	 * @param collection
	 *            The lists to remove
	 *
	 * @return the number of lists successfully removed
	 */
	public int removeLists(Collection<ITaskCollection> collection);

	/**
	 * Removes a task from the data storage.
	 *
	 * @param task
	 *            The task to remove
	 *
	 * @return true if the task was removed successfully, false if not
	 */
	public boolean removeTask(ITask task);

	/**
	 * Removes several tasks from the data storage.
	 *
	 * @param listOfTasksToRemove
	 *            The tasks to remove
	 *
	 * @return the number of tasks successfully removed
	 */
	public int removeTasks(Collection<ITask> listOfTasksToRemove);

}
