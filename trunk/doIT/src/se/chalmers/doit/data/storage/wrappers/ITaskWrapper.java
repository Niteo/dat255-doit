package se.chalmers.doit.data.storage.wrappers;

import se.chalmers.doit.core.ITask;

/**
 * A wrapper around ITask adding an ID-number.
 *
 * @author Boel
 *
 */

public interface ITaskWrapper extends ITask {

	/**
	 * Returns the wrapper's ID.
	 *
	 * @return int ID-number
	 */
	public int getID();

}
