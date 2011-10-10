package se.chalmers.doit.core;

import java.util.Date;

/**
 * An interface representing a statistical data point in time, containing
 * statistics on tasks and other things
 * 
 * @author Karl Bristav
 * 
 */
public interface IStatisticalData {

	/**
	 * Adds a number of finished tasks to the number of finished tasks contained
	 * within this object
	 */
	public void addFinishedTasks(int amount);

	/**
	 * Adds a number of created tasks to the number of created tasks contained
	 * within this object
	 */
	public void addCreatedTasks(int amount);

	/**
	 * Adds a number of deleted tasks to the number of deleted tasks contained
	 * within this object
	 */
	public void addDeletedTasks(int amount);

	/**
	 * Adds a number of overdue tasks to the number of overdue tasks contained
	 * within this object
	 */
	public void addOverdueTasks(int amount);

	/**
	 * Adds a number of created lists to the number of created lists contained
	 * within this object
	 */
	public void addCreatedLists(int amount);

	/**
	 * Adds a number of deleted lists to the number of created lists contained
	 * within this object
	 */
	public void addDeletedLists(int amount);

	/**
	 * Returns the date of this object
	 * 
	 * @return A Date object containing the date (specified down to day) of this
	 *         object
	 */
	public Date getDate();

	/**
	 * Returns the number of finished tasks contained within this object
	 * 
	 * @return int representing the number of finished tasks
	 */
	public int getFinishedTasks();

	/**
	 * Returns the number of created tasks contained within this object
	 * 
	 * 
	 * @return int representing the number of created tasks
	 */
	public int getCreatedTasks();

	/**
	 * Returns the number of deleted tasks contained within this object
	 * 
	 * @return int representing the number of deleted tasks
	 */
	public int getDeletedTasks();

	/**
	 * Returns the number of overdue tasks contained within this object
	 * 
	 * @return int representing the number of overdue tasks
	 */
	public int getOverdueTasks();

	/**
	 * Returns the number of created lists contained within this object
	 * 
	 * @return int representing the number of created lists
	 */
	public int getCreatedLists();

	/**
	 * Returns the number of deleted lists contained within this object
	 * 
	 * @return int representing the number of deleted lists
	 */
	public int getDeletedLists();

	/**
	 * Checks whether the day, month and year of this IStatisticalData is the
	 * same as the day, month and year in the specified Date
	 * 
	 * @param date
	 *            The date to be compared with
	 * @return true if the day, month and years of the IStatisticalData and Date
	 *         is the same, false otherwise
	 */
	public boolean sameDay(Date date);

}
