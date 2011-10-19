package se.chalmers.doit.util.implementation;

import java.util.*;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.*;

/**
 * 
 * @author Boel
 * 
 */
public class TaskListUtility implements ITaskListUtility {

	@Override
	public void sortTasks(final List<ITask> list,
			final IComparatorStrategy primary,
			final IComparatorStrategy secondary,
			final IComparatorStrategy tertiary) {

		Comparator<ITask> comparer = new SortingComparator(primary, secondary,
				tertiary);

		Collections.sort(list, comparer);
	}

}
