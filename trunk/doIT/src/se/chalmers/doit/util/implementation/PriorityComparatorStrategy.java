package se.chalmers.doit.util.implementation;

import se.chalmers.doit.core.*;
import se.chalmers.doit.util.IComparatorStrategy;

/**
 * An implementation of IComparatorStrategy that compares the priorities of two
 * ITasks
 * 
 * Default sorting order for priorities is descending, i.e for a priority p1
 * with lower priority than another priority p2, (p1 > p2) is true. In layman's
 * terms; high priorities is considered lower than low priorities.
 * 
 * @author Karl Bristav
 * 
 */
public class PriorityComparatorStrategy implements IComparatorStrategy {

	private static final long serialVersionUID = 1175320577897010528L;
	private final boolean invertedSortOrder;

	/**
	 * @param invertedSortOrder
	 *            Boolean stating whether the sorting order is to be reversed
	 */
	public PriorityComparatorStrategy(final boolean invertedSortOrder) {
		this.invertedSortOrder = invertedSortOrder;
	}

	@Override
	public int compare(final ITask task1, final ITask task2) {

		int ret = 0;

		final IPriority p1 = task1.getPriority();
		final IPriority p2 = task2.getPriority();

		if (p1.compareTo(p2) < 0) {
			ret = 1;
		} else if (p1.compareTo(p2) > 0) {
			ret = -1;
		}

		if (invertedSortOrder) {
			ret *= -1;
		}

		return ret;
	}

}
