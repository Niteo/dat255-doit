package se.chalmers.doit.logic.verification;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;

/**
 * An interface verifying that a list or a task is correctly filled in, and
 * fixes errors if any are discovered
 * 
 * @author Karl
 * 
 */
public interface IDataVerifier {

	/**
	 * Verifies the contents of a list
	 * 
	 * @param collection
	 *            The ITaskCollection to be verified
	 * @return The corrected ITaskCollection
	 */
	public ITaskCollection verifyList(ITaskCollection collection);

	/**
	 * Verifies the contents of a task
	 * 
	 * @param task
	 *            The ITask to be verified
	 * @return The corrected ITask
	 */
	public ITask verifyTask(ITask task);

}
