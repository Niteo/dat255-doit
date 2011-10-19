package se.chalmers.doit.data.storage;

import java.util.Collection;

import se.chalmers.doit.core.*;

/**
 * An interface handling the storage of tasks and lists between the application
 * and an arbitrary data storage module
 * 
 * @author Karl Bristav, Robert Kaufmann
 * 
 */
public interface IDataStorage {

	/**
	 * Adds a List to the data storage. List does not get added if it already is
	 * added or if any of it's tasks are already added.
	 * 
	 * @param collection
	 *            The ITaskCollection to add
	 * 
	 * @return true if list was added, else false
	 */
	public boolean addList(ITaskCollection collection);

	/**
	 * Adds several lists to the data storage. Lists do not get added if they
	 * already are added or if any of their tasks already are.
	 * 
	 * @param collection
	 *            A Collection containing the ITaskCollections to add
	 * @return amount of lists added
	 */
	public int addLists(Collection<ITaskCollection> collection);

	/**
	 * Adds a task to a list in the data storage if the specified list exists
	 * 
	 * @param task
	 *            The ITask to be added
	 * @param collection
	 *            The ITaskCollection to which the ITask is added
	 * @return true if task was added, else false
	 */
	public boolean addTask(ITask task, ITaskCollection collection);

	/**
	 * Adds several tasks to a list in the data storage if the specified list
	 * exists
	 * 
	 * @param tasks
	 *            a Collection of ITasks to be added
	 * @param collection
	 *            the ITaskCollection to which the ITasks is added
	 * @return amount of lists added
	 */
	public int addTasks(Collection<ITask> tasks, ITaskCollection collection);

	/**
	 * Clears the data storage of all stored data
	 */
	public void clearData();

	/**
	 * "Edits" a list by replacing it with a new list
	 * 
	 * @param oldCollection
	 *            The ITaskCollection to be replaced
	 * @param newCollection
	 *            The ITaskCollection to replace the old one
	 * @return true if list was edited, false if not
	 */
	public boolean editList(ITaskCollection oldCollection,
			ITaskCollection newCollection);

	/**
	 * "Edits" a task by replacing it with a new task
	 * 
	 * @param oldTask
	 *            The ITask to be replaced
	 * @param newTask
	 *            The ITask to replace the old one
	 * @return true if task was edited, false if not
	 */
	public boolean editTask(ITask oldTask, ITask newTask);

	/**
	 * Returns a Collection of all the existing ITaskCollections
	 * 
	 * @return The Collection containing all existing ITaskCollections
	 */
	public Collection<ITaskCollection> getAllLists();

	/**
	 * Return a Collection of all the existing ITasks
	 * 
	 * @return The Collection containing all existing ITasks
	 */
	public Collection<ITask> getAllTasks();

	/**
	 * Moves a task from one list to another list
	 * 
	 * @param task
	 *            The ITask to be moved
	 * @param collection
	 *            The ITaskCollection to which the ITask shall be moved
	 * @return true if task was moved, false if not
	 */
	public boolean moveTask(ITask task, ITaskCollection collection);

	/**
	 * Removes a list from the data storage
	 * 
	 * @param collection
	 *            The ITaskCollection to be removed
	 * @return true if list was removed, false if not
	 */
	public boolean removeList(ITaskCollection collection);

	/**
	 * Removes several lists from the data storage
	 * 
	 * @param collection
	 *            A Collection containing the ITaskCollections to be removed
	 * @return amount of removed lists
	 */
	public int removeLists(Collection<ITaskCollection> collection);

	/**
	 * Remove a task from the data storage
	 * 
	 * @param task
	 *            The ITask to be removed
	 * @return true if task was removed, false if not
	 */
	public boolean removeTask(ITask task);

	/**
	 * Remove several tasks from the data storage
	 * 
	 * @param listOfTasksToRemove
	 *            A Collection containing the ITasks to be removed
	 * @return amount of removed tasks
	 */
	public int removeTasks(Collection<ITask> listOfTasksToRemove);
}
