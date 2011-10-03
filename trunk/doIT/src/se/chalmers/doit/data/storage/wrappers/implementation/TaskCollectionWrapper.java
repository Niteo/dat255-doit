package se.chalmers.doit.data.storage.wrappers.implementation;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.data.storage.wrappers.ITaskCollectionWrapper;

public class TaskCollectionWrapper implements ITaskCollectionWrapper {

	private int id;
	private ITaskCollection taskCollection;

	public TaskCollectionWrapper(int id, ITaskCollection taskCollection){
		this.id = id;
		this.taskCollection = taskCollection;
	}

	@Override
	public String getName() {
		return taskCollection.getName();
	}

	@Override
	public Collection<ITask> getTasks() {
		return taskCollection.getTasks();
	}

	@Override
	public int getID() {
		return id;
	}

}
