package se.chalmers.doit.core.implementation;

import java.util.Collection;
import java.util.LinkedList;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;

public class TaskCollection implements ITaskCollection {

	private String name;
	private Collection<ITask> taskCollection;

	public TaskCollection(String name){
		this.name = name;
		taskCollection = new LinkedList<ITask>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<ITask> getTaskCollection() {
		return new LinkedList<ITask>(taskCollection);
	}

}
