package se.chalmers.doit.data.storage.wrappers;

import se.chalmers.doit.core.ITaskCollection;

/**
 * A wrapper around ITaskCollection adding an ID-number.
 *
 * @author Boel
 *
 */
public interface ITaskCollectionWrapper extends ITaskCollection {

	/**
	 * Returns the wrapper's ID.
	 *
	 * @return int ID-number
	 */
	public int getID();

}
