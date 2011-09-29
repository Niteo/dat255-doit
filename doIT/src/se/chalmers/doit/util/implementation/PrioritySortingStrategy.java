package se.chalmers.doit.util.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.ISortingStrategy;

/**
 * Represents a sorting strategy that sorts a list by its priority with the
 * highest on top
 * 
 * @author Karl
 * 
 */
public class PrioritySortingStrategy implements ISortingStrategy {

	@Override
	public List<List<ITask>> sort(final List<ITask> list) {

		final ArrayList<List<ITask>> listList = new ArrayList<List<ITask>>();
		final ArrayList<ITask> tempList = new ArrayList<ITask>();

		Collections.sort(list, new PriorityComparator());

		final ITask preTask = list.get(0);
		tempList.add(list.get(0));

		for (int i = 1; i < list.size(); i++) {

			if (list.get(i).getPriority().compareTo(preTask.getPriority()) < 1) {
				listList.add(tempList);
				tempList.clear();
			}

			tempList.add(list.get(i));

		}

		listList.add(tempList);

		return listList;
	}

}
