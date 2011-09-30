package se.chalmers.doit.util;

import java.util.List;

import se.chalmers.doit.core.ITask;

/**
 * Utility for doing operations on lists containing ITask objects.
 * 
 * @author Kaufmann
 */
public interface ITaskListUtility {

	/**
	 * Sorts the provided task using primary, secondary and tertiary sorting
	 * strategies.
	 * 
	 * @param list
	 *            list to sort
	 * @param primary
	 *            primary sorting strategy
	 * @param secondary
	 *            secondary sorting strategy
	 * @param tertiary
	 *            tertiary sorting strategy
	 * @return a list in the provided order
	 */
	public List<ITask> sortTasks(List<ITask> list, IComparatorStrategy primary,
			IComparatorStrategy secondary, IComparatorStrategy tertiary);

}
