package se.chalmers.doit.logic.controller;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.data.storage.IDataStorage;
import se.chalmers.doit.data.storage.IStatisticsDataStorage;

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
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean addList(ITaskCollection taskCollection)
			throws IllegalStateException;

	/**
	 * Adds several lists to the data storage.
	 *
	 * @param collection
	 *            The lists to add
	 * @return the number of lists that were successfully added
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int addLists(Collection<ITaskCollection> collection)
			throws IllegalStateException;

	/**
	 * Adds a task to the data storage.
	 *
	 * @param task
	 *            The task to add
	 * @param collection
	 *            The list to add the task to
	 * @return true if the task was successfully added, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean addTask(ITask task, ITaskCollection collection)
			throws IllegalStateException;

	/**
	 * Adds several tasks to the data storage.
	 *
	 * @param tasks
	 *            The tasks to add
	 * @param collection
	 *            The TaskCollection to add the tasks to
	 * @return the number tasks that were successfully added
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int addTasks(Collection<ITask> tasks, ITaskCollection collection)
			throws IllegalStateException;

	/**
	 * Clears all task- and list-related data from the data storage
	 *
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public void clearData() throws IllegalStateException;

	/**
	 * Marks a task as completed.
	 *
	 * @param task
	 *            The task to complete
	 * @return true if the task was successfully completed, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean completeTask(ITask task) throws IllegalStateException;

	/**
	 * Marks a task as decompleted.
	 *
	 * @param task
	 *            The task to decomplete
	 * @return true if the task was successfully decompleted, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean decompleteTask(ITask task) throws IllegalStateException;

	/**
	 * Edits a list.
	 *
	 * @param oldCollection
	 *            The old ITaskCollection
	 * @param newCollection
	 *            The new ITaskCollection
	 * @return true if the list was successfully edited, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean editList(ITaskCollection oldCollection,
			ITaskCollection newCollection) throws IllegalStateException;

	/**
	 * Edits a task.
	 *
	 * @param oldTask
	 *            The old task
	 * @param newTask
	 *            The new task
	 * @return true if the task was successfully edited, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean editTask(ITask oldTask, ITask newTask)
			throws IllegalStateException;

	/**
	 * Returns all lists.
	 *
	 * @return all ITaskCollections in the data storage
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public Collection<ITaskCollection> getAllLists()
			throws IllegalStateException;

	/**
	 * Returns all tasks.
	 *
	 * @return all ITasks in the data storage
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public Collection<ITask> getAllTasks() throws IllegalStateException;

	/**
	 * Moves a task to a new location.
	 *
	 * @param task
	 *            The task to move
	 * @param taskCollection
	 *            The location to move the task to
	 *
	 * @return true if the task was moved successfully, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean moveTask(ITask task, ITaskCollection taskCollection)
			throws IllegalStateException;

	/**
	 * Removes a list from the data storage.
	 *
	 * @param collection
	 *            The list to remove
	 *
	 * @return true if the list was removed successfully, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean removeList(ITaskCollection collection)
			throws IllegalStateException;

	/**
	 * Removes several lists from the data storage.
	 *
	 * @param collection
	 *            The lists to remove
	 *
	 * @return the number of lists successfully removed
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int removeLists(Collection<ITaskCollection> collection)
			throws IllegalStateException;

	/**
	 * Removes a task from the data storage.
	 *
	 * @param task
	 *            The task to remove
	 *
	 * @return true if the task was removed successfully, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean removeTask(ITask task) throws IllegalStateException;

	/**
	 * Removes several tasks from the data storage.
	 *
	 * @param listOfTasksToRemove
	 *            The tasks to remove
	 *
	 * @return the number of tasks successfully removed
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int removeTasks(Collection<ITask> listOfTasksToRemove)
			throws IllegalStateException;

	/**
	 * Returns the number of created tasks between the current day and a
	 * specified number of days in the past.
	 *
	 * @param pastDays
	 *            The number of days in the pasts the interval will reach to
	 * @return The number of created tasks for the interval
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int getNumberOfCreatedTasks(int pastDays)
			throws IllegalStateException;

	/**
	 * Returns the number of finished tasks between the current day and a
	 * specified number of days in the past.
	 *
	 * @param pastDays
	 *            The number of days in the pasts the interval will reach to
	 * @return The number of finished tasks for the interval
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int getNumberOfFinishedTasks(int pastDays)
			throws IllegalStateException;

	/**
	 * Returns the number of overdue tasks between the current day and a
	 * specified number of days in the past.
	 *
	 * @param pastDays
	 *            The number of days in the pasts the interval will reach to
	 * @return The number of overdue tasks for the interval
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int getNumberOfOverdueTasks(int pastDays)
			throws IllegalStateException;

	/**
	 * Returns the number of deleted tasks between the current day and a
	 * specified number of days in the past.
	 *
	 * @param pastDays
	 *            The number of days in the pasts the interval will reach to
	 * @return The number of deleted tasks for the interval
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int getNumberOfDeletedTasks(int pastDays)
			throws IllegalStateException;

	/**
	 * Returns the number of created lists between the current day and a
	 * specified number of days in the past.
	 *
	 * @param pastDays
	 *            The number of days in the pasts the interval will reach to
	 * @return The number of created lists for the interval
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int getNumberOfCreatedLists(int pastDays)
			throws IllegalStateException;

	/**
	 * Returns the number of deleted lists between the current day and a
	 * specified number of days in the past.
	 *
	 * @param pastDays
	 *            The number of days in the pasts the interval will reach to
	 * @return The number of deleted lists for the interval
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public int getNumberOfDeletedLists(int pastDays)
			throws IllegalStateException;

	/**
	 * Increments the number of created tasks by a given amount
	 *
	 * @param amount
	 *            the amount to increment
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public void incrementNumberOfCreatedTasks(int amount)
			throws IllegalStateException;

	/**
	 * Increments the number of finished tasks by a given amount
	 *
	 * @param amount
	 *            the amount to increment
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public void incrementNumberOfFinishedTasks(int amount)
			throws IllegalStateException;

	/**
	 * Increments the number of overdue tasks by a given amount
	 *
	 * @param amount
	 *            the amount to increment
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public void incrementNumberOfOverdueTasks(int amount)
			throws IllegalStateException;

	/**
	 * Increments the number of deleted tasks by a given amount
	 *
	 * @param amount
	 *            the amount to increment
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public void incrementNumberOfDeletedTasks(int amount)
			throws IllegalStateException;

	/**
	 * Increments the number of created lists by a given amount
	 *
	 * @param amount
	 *            the amount to increment
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public void incrementNumberOfCreatedLists(int amount)
			throws IllegalStateException;

	/**
	 * Increments the number of deleted lists by a given amount
	 *
	 * @param amount
	 *            the amount to increment
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public void incrementNumberOfDeletedLists(int amount)
			throws IllegalStateException;

	/**
	 * Clears all statistics-related data from the data storage
	 *
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public void clearStatisticsData() throws IllegalStateException;

	/**
	 * Toggles the completion state of a task.
	 *
	 * @param task
	 *            The task to toggle
	 * @return task true if the task's completion state was successfully
	 *         changed, false if not
	 * @throws IllegalStateException
	 *             if storage strategy is not set
	 */
	public boolean toggleTaskCompleted(ITask task) throws IllegalStateException;

	/**
	 * Sets the storage strategy.
	 *
	 * @param dataStorage
	 *            The storage strategy
	 *
	 */
	public void setStorageStrategy(IDataStorage dataStorage);

	/**
	 * Sets the statistics strategy.
	 *
	 * @param statisticsStorage
	 *            The statistics strategy
	 */
	public void setStatisticsStrategy(IStatisticsDataStorage statisticsStorage);
}
