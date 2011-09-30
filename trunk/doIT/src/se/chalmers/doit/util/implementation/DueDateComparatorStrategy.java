package se.chalmers.doit.util.implementation;

import java.util.Date;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.IComparatorStrategy;

/**
 * An implementation of IComparatorStrategy that compares the due dates of two
 * ITasks
 * 
 * @author Karl Bristav
 * 
 */
public class DueDateComparatorStrategy implements IComparatorStrategy {

	@Override
	public int compare(final ITask t1, final ITask t2) {

		final Date d1 = t1.getDueDate();
		final Date d2 = t2.getDueDate();

		if (d1.compareTo(d2) > 0) {
			return 1;
		} else if (d1.compareTo(d2) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
