package se.chalmers.doit.util;

import java.util.Comparator;

import se.chalmers.doit.core.ITask;

/**
 * An interface for sorting ITasks according to a primary, secondary and
 * tertiary IComparatorStrategy
 * 
 * @author Karl
 * 
 */
public interface ISortingComparator extends Comparator<ITask> {

	@Override
	public int compare(ITask t1, ITask t2);

	/**
	 * Sets the sorting order of the SortingComparator
	 * 
	 * @param primary
	 *            The primary sorting criteria
	 * @param secondary
	 *            The secondary sorting criteria
	 * @param tertiary
	 *            The tertiary sorting criteria
	 */
	public void setSortingOrder(IComparatorStrategy primary,
			IComparatorStrategy secondary, IComparatorStrategy tertiary);

}
