package se.chalmers.doit.logic.verification;

import java.util.Collection;

import se.chalmers.doit.core.*;

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
	 * @param newCollection
	 *            The ITaskCollection to be verified
	 * @param verifiedCollections
	 *            The ITaskCollections that already are verified
	 * @return A boolean stating if the list passed the verification or not
	 */
	public boolean verifyList(ITaskCollection newCollection,
			Collection<ITaskCollection> verifiedCollections);

	/**
	 * Verifies the contents of a task
	 * 
	 * @param task
	 *            The ITask to be verified
	 * @return A boolean stating if the task passed the verification or not
	 */
	public boolean verifyTask(ITask task);

}
