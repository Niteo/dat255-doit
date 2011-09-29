package se.chalmers.doit.util.implementation;

import java.util.Comparator;

import se.chalmers.doit.core.IPriority;
import se.chalmers.doit.core.ITask;

public class PriorityComparator implements Comparator<ITask> {

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
