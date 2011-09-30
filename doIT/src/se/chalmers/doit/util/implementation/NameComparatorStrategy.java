package se.chalmers.doit.util.implementation;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.IComparatorStrategy;

/**
 * An implementation of IComparatorStrategy that compares the names of two
 * ITasks
 * 
 * @author Karl Bristav
 * 
 */
public class NameComparatorStrategy implements IComparatorStrategy {

	@Override
	public int compare(final ITask t1, final ITask t2) {

		final String n1 = t1.getName();
		final String n2 = t2.getName();

		if (n1.compareTo(n2) > 0) {
			return 1;
		} else if (n1.compareTo(n2) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
