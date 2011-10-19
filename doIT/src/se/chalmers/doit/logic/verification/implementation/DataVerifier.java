package se.chalmers.doit.logic.verification.implementation;

import java.util.Collection;

import se.chalmers.doit.core.*;
import se.chalmers.doit.logic.verification.IDataVerifier;

public class DataVerifier implements IDataVerifier {

	@Override
	public boolean verifyList(final ITaskCollection newCollection,
			final Collection<ITaskCollection> verifiedCollections) {
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

	@Override
	public boolean verifyTask(final ITask task) {
		if (task.getName().length() <= 32) {
			return true;
		}
		return false;
	}

}
