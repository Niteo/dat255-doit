package se.chalmers.doit.util;

import java.util.List;

import se.chalmers.doit.core.ITask;

/**
 * Utility for doing operations on lists containing ITask objects.
 * 
 * @author Robert Kaufmann, Karl Bristav
 */
public interface ITaskListUtility {

	/**
	 * Sorts the provided task using primary, secondary and tertiary comparator
	 * strategies.
	 * 
	 * @param list
	 *            list to sort
	 * @param primary
	 *            primary comparator strategy
	 * @param secondary
	 *            secondary comparator strategy
	 * @param tertiary
	 *            tertiary comparator strategy
	 */
	public void sortTasks(List<ITask> list, IComparatorStrategy primary,
			IComparatorStrategy secondary, IComparatorStrategy tertiary);

}
