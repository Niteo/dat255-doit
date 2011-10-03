package se.chalmers.doit.logic.verification.implementation;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.logic.verification.IDataVerifier;

public class DataVerifier implements IDataVerifier {

	@Override
	public boolean verifyList(ITaskCollection collection) {
		//TODO fix me!
		return false;
	}

	@Override
	public boolean verifyTask(ITask task) {
		if(task.getName().length() <=32){
			return true;
		}
		return false;
	}

}
