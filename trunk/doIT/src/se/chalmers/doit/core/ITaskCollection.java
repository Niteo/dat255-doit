package se.chalmers.doit.core;

import java.util.Collection;

/**
 * Represents a list containing a collection of tasks.
 *
 * @author Kaufmann
 *
 */
public interface ITaskCollection {
	/**
	 * Returns the list's name.
	 *
	 * @return a string representing the list's name
	 */
	public String getName();

	/**
	 * Returns the collection of ITasks the collection contains.
	 *
	 * @return collection of ITasks
	 */
	public Collection<ITask> getTaskCollection();
}