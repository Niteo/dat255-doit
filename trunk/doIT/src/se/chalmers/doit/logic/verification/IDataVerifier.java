package se.chalmers.doit.logic.verification;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;

public interface IDataVerifier {

	public ITaskCollection verifyList(ITaskCollection collection);

	public ITask verifyTask(ITask task);

}
