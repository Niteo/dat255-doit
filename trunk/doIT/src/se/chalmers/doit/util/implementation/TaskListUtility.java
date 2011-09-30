package se.chalmers.doit.util.implementation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.IComparatorStrategy;
import se.chalmers.doit.util.ITaskListUtility;

/**
 *
 * @author Boel
 *
 */
public class TaskListUtility implements ITaskListUtility{

	@Override
	public void sortTasks(List<ITask> list, IComparatorStrategy primary,
			IComparatorStrategy secondary, IComparatorStrategy tertiary) {

		Comparator<ITask> comparer = new SortingComparator(primary, secondary, tertiary);

		Collections.sort(list, comparer);
	}

}
