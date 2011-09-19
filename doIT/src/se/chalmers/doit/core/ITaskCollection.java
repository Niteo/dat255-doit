package se.chalmers.doit.core;

import java.util.Collection;

/**
 * Represents a list containing a collection of ID-numbers for tasks.
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
	 * Returns the collection of IDs the collection contains.
	 *
	 * @return collection of IDs
	 */
	public Collection<Integer> getIDCollection();
}