package se.chalmers.doit.util.implementation;

import se.chalmers.doit.core.IPriority;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.IComparatorStrategy;

public class PriorityComparator implements IComparatorStrategy {

	@Override
	public int compare(final ITask task1, final ITask task2) {

		final IPriority p1 = task1.getPriority();
		final IPriority p2 = task2.getPriority();

		if (p1.compareTo(p2) > 0) {
			return 1;
		} else if (p1.compareTo(p2) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
