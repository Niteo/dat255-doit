package se.chalmers.doit.util.implementation;

import java.util.Date;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.IComparatorStrategy;

/**
 * An implementation of IComparatorStrategy that compares the due dates of two
 * ITasks
 *
 * Default sorting order for due dates is ascending.
 *
 * @author Karl Bristav
 *
 */
public class DueDateComparatorStrategy implements IComparatorStrategy {

	private static final long serialVersionUID = 7961359519951096927L;
	private final boolean invertedSortOrder;

	/**
	 * @param invertedSortOrder
	 *            Boolean stating whether the sorting order is to be reversed
	 */
	public DueDateComparatorStrategy(final boolean invertedSortOrder) {
		this.invertedSortOrder = invertedSortOrder;
	}

	@Override
	public int compare(final ITask t1, final ITask t2) {

		int ret = 0;

		final Date d1 = t1.getDueDate();
		final Date d2 = t2.getDueDate();

		if (d1.compareTo(d2) > 0) {
			ret = 1;
		} else if (d1.compareTo(d2) < 0) {
			ret = -1;
		}

		if (invertedSortOrder) {
			ret *= -1;
		}

		return ret;
	}

}
