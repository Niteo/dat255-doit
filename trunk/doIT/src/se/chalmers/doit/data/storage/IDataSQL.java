package se.chalmers.doit.data.storage;

import java.util.*;

import se.chalmers.doit.core.*;
import android.database.sqlite.SQLiteDatabase;

/**
 * Interface for managing tasks through an Android SQLite database
 * 
 * @author Kaufmann
 * 
 */
public interface IDataSQL {

	/**
	 * Adds a List to the SQLite database
	 * 
	 * @param list
	 *            The ITaskCollection to add
	 * 
	 * @return id of added list. -1 if list was not added
	 */
	public int addList(ITaskCollection list);

	/**
	 * Adds several lists to the SQLite database
	 * 
	 * @param lists
	 *            An array containing the ITaskCollections to add
	 * @return the id of each corresponding list. -1 for when the list was not
	 *         added
	 */
	public int[] addLists(ITaskCollection[] lists);

	/**
	 * Adds a task to a list in the SQLite database
	 * 
	 * @param task
	 *            The ITask to be added
	 * @param listID
	 *            The id of the list to which the ITask should be added
	 * @return id of the added task
	 */
	public int addTask(ITask task, int listID);

	/**
	 * Adds several tasks to a list in the SQLite database
	 * 
	 * @param tasks
	 *            an array of ITasks to be added
	 * @param listID
	 *            the list to which the ITasks are added
	 * @return the id of each corresponding task. -1 for when the task was not
	 *         added
	 */
	public int[] addTasks(ITask[] tasks, int listID);

	/**
	 * Clears the SQLite database of all stored data. Tables will not be
	 * removed.
	 */
	public void clearData();

	/**
	 * Edits a list
	 * 
	 * @param listID
	 *            The id of the list to edit
	 * @param newListProperties
	 *            The ITaskCollection which properties will be used
	 * @return true if list was edited, false if not
	 */
	public boolean editList(int listID, ITaskCollection newListProperties);

	/**
	 * Edits a task
	 * 
	 * @param taskID
	 *            The ID of the edited task
	 * @param newTaskProperties
	 *            The ITask which properties will be used
	 * @return true if task was edited, false if not
	 */
	public boolean editTask(int taskID, ITask newTaskProperties);

	/**
	 * Returns a Map of all the existing lists, mapping them to their corresponding integer-based ID
	 * 
	 * @return a map containing all existing ITaskCollections with their corresponding ID
	 */
	public Map<ITaskCollection, Integer> getAllLists();

	/**
	 * Return a Map of all the existing tasks, mapping them to their corresponding integer-based ID
	 * 
	 * @return a map containing all existing ITasks with their corresponding ID
	 */
	public Map<ITask, Integer> getAllTasks();

	/**
	 * Moves a task from one list to another list
	 * 
	 * @param taskID
	 *            The ID of the task to move
	 * @param listID
	 *            The ID of the list to move to
	 * @return true if task was moved, false if not
	 */
	public boolean moveTask(int taskID, int listID);

	/**
	 * Removes a list from the SQLite database
	 * 
	 * @param id
	 *            ID of the list to remove
	 * @return true if list was removed, false if not
	 */
	public boolean removeList(int listID);

	/**
	 * Removes several lists from the SQLite database
	 * 
	 * @param listIDs
	 *            An array of id for the lists to remove
	 * @return array of boolean indicating if the corresponding list was removed
	 */
	public boolean[] removeLists(int[] listIDs);

	/**
	 * Remove a task from the SQLite database
	 * 
	 * @param taskID
	 *            The id of the task to remove
	 * @return true if task was removed, false if not
	 */
	public boolean removeTask(int taskID);

	/**
	 * Remove several tasks from the SQLite database
	 * 
	 * @param taskIDs
	 *            A Collection containing the ITasks to be removed
	 * @return array of boolean indicating if the corresponding list was removed
	 */
	public boolean[] removeTasks(int[] taskIDs);

	/**
	 * Sets the database to use for storage. Tables will be created if they
	 * don't exist in the database.
	 * 
	 * @param database
	 *            database to add
	 */
	public void setDatabase(SQLiteDatabase database);

}
