package se.chalmers.doit.core.implementation;

import java.util.ArrayList;
import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;

public class TaskCollection implements ITaskCollection {

	private String name;
	private Collection<ITask> taskCollection;

	public TaskCollection(String name, Collection<ITask> taskCollection) {
		this.name = name;
		this.taskCollection = new ArrayList<ITask>(taskCollection);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<ITask> getTasks() {
		return new ArrayList<ITask>(taskCollection);
	}

}
