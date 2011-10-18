package se.chalmers.doit.core.implementation;

import java.util.ArrayList;
import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;

public class TaskCollection implements ITaskCollection {

	private String name;
	private Collection<ITask> taskCollection;

	public TaskCollection(String name) {
		this.name = name;
		this.taskCollection = new ArrayList<ITask>();
	}

	public TaskCollection(String name, Collection<ITask> taskCollection) {
		this(name);
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

	@Override
	public boolean equals(final Object o) {
		if (o instanceof ITaskCollection) {
			ITaskCollection collection = (ITaskCollection) o;

			if (collection.getName().equals(this.name)
					&& collection.getTasks().size() == this.taskCollection
							.size()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 101;
		hash = hash * 31 + name.hashCode();
		hash = hash * 19 + taskCollection.hashCode();
		return hash;
	}
}