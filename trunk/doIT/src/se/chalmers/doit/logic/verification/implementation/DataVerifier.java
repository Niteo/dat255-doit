package se.chalmers.doit.logic.verification.implementation;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.logic.verification.IDataVerifier;

public class DataVerifier implements IDataVerifier {

	@Override
	public boolean verifyTask(ITask task) {
		if(task.getName().length() <=32){
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyList(ITaskCollection newCollection,
			Collection<ITaskCollection> verifiedCollections) {
		if (verifiedCollections.size() == 0) {
			return true;
		}

		for (final ITaskCollection list : verifiedCollections) {
			if (list.getName().equals(newCollection.getName())) {
				return false;
			}
		}
		return true;
	}

}
