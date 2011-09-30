package se.chalmers.doit.util.implementation;

import se.chalmers.doit.core.IPriority;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.IComparatorStrategy;

/**
 * An implementation of IComparatorStrategy that compares the priorities of two
 * ITasks
 * 
 * @author Karl Bristav
 * 
 */
public class PriorityComparatorStrategy implements IComparatorStrategy {

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

		if (p1.compareTo(p2) > 0) {
			ret = 1;
		} else if (p1.compareTo(p2) < 0) {
			ret = -1;
		}

		if (invertedSortOrder) {
			ret *= -1;
		}

		return ret;
	}

}
