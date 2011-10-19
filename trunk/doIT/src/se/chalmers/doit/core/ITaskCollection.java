package se.chalmers.doit.core;

import java.util.Collection;

/**
 * Represents a list containing tasks.
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
	 * Returns a list of the collection's ITasks.
	 * 
	 * @return Collection of ITasks
	 */
	public Collection<ITask> getTasks();
}