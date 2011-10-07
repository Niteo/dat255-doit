package se.chalmers.doit.data.storage;

import java.util.Collection;
import java.util.Date;

import se.chalmers.doit.core.IStatisticalData;

/**
 * An interface handling the storage of statistics data between the application
 * and an arbitrary data storage module
 * 
 * @author Karl Bristav
 * 
 */
public interface IStatisticsDataStorage {

	/**
	 * Reports that a number of tasks have been marked as finished on a given
	 * date to the data storage
	 * 
	 * @param numberOfFinishedTasks
	 *            The number of finished tasks that is reported
	 * @param date
	 *            The date on which the tasks was marked as finished
	 */
	public void reportFinishedTasks(int numberOfFinishedTasks, Date date);

	/**
	 * Reports that a number of tasks have been created on a given date to the
	 * data storage
	 * 
	 * @param numberOFCreatedTasks
	 *            The number of tasks that is reported
	 * @param date
	 *            The date on which the tasks was created
	 */
	public void reportCreatedTasks(int numberOFCreatedTasks, Date date);

	/**
	 * Reports that a number of tasks have been deleted on a given date to the
	 * data storage
	 * 
	 * @param numberOfCreatedTasks
	 *            The number of tasks that is reported
	 * @param date
	 *            The date on which the tasks was deleted
	 */
	public void reportDeletedTasks(int numberOfDeletedTasks, Date date);

	/**
	 * Reports that a number of tasks have not met their due date on a given
	 * date to the data storage
	 * 
	 * @param numberOfOverdueTasks
	 *            the number of tasks that is reported
	 * @param date
	 *            The date on which the tasks was overdue
	 */
	public void reportOverdueTasks(int numberOfOverdueTasks, Date date);

	/**
	 * Reports that a number of lists have been created on a given date to the
	 * data storage
	 * 
	 * @param numberOfCreatedLists
	 *            The number of lists that is reported
	 * @param date
	 *            The date on which the lists was created
	 */
	public void reportCreatedLists(int numberOfCreatedLists, Date date);

	/**
	 * Reports that a number of lists have been deleted on a given date to the
	 * data storage
	 * 
	 * @param numberOfCreatedLists
	 *            The number of lists that is reported
	 * @param date
	 *            The date on which the lists was deleted
	 */
	public void reportDeletedLists(int numberOfDeletedLists, Date date);

	/**
	 * Returns all statistics data as a collection of IStatisticalData
	 * 
	 * @return a Collection of IStatisticalData
	 */
	public Collection<IStatisticalData> getStatisticsData();

	/**
	 * Clear all data from the data storage
	 */
	public void clearData();

}
