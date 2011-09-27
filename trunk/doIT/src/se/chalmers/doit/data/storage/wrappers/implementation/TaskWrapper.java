package se.chalmers.doit.data.storage.wrappers.implementation;

import java.util.Date;

import se.chalmers.doit.core.IPriority;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.data.storage.wrappers.ITaskWrapper;

public class TaskWrapper implements ITaskWrapper {

	private int id;
	private ITask task;

	public TaskWrapper(int id, ITask task) {
		this.id = id;
		this.task = task;
	}

	@Override
	public String getName() {
		return task.getName();
	}

	@Override
	public String getDescription() {
		return task.getDescription();
	}

	@Override
	public Date getDueDate() {
		return task.getDueDate();
	}

	@Override
	public IPriority getPriority() {
		return task.getPriority();
	}

	@Override
	public Date getReminderDate() {
		return task.getReminderDate();
	}

	@Override
	public int getCustomPosition() {
		return task.getCustomPosition();
	}

	@Override
	public boolean isCompleted() {
		return task.isCompleted();
	}

	@Override
	public int getID() {
		return id;
	}

}
