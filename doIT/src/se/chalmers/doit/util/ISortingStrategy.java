package se.chalmers.doit.util;

import java.util.List;
import se.chalmers.doit.core.ITask;

/**
 * Strategy for sorting a list containing classes implementing ITask. 
 * @author Kaufmann
 */
public interface ISortingStrategy {
	/**
	 * Sorts the given list.
	 * @param list - the list to sort
	 * @return a sorted version of the input list
	 */
	public List<ITask> sort(List<ITask> list);
}