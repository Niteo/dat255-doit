package se.chalmers.doit.core.implementation;

import java.util.Date;

import se.chalmers.doit.core.*;
import se.chalmers.doit.util.implementation.Constants;

/**
 * Immutable class carrying properties of a task.
 * 
 * @author Marco Baxemyr, Robert Kaufmann
 *
 */

public final class Task implements ITask {
	
	final private String name;
	final private String description;
	final private IPriority priority;
	final private Date dueDate;
	final private Date reminderDate;
	final private int customPosition;
	final private boolean isCompleted;
	
	public Task(String name, String description, IPriority priority, Date dueDate, Date reminderDate, int customPosition, boolean isCompleted) {
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.dueDate = new Date(dueDate.getTime());
		this.reminderDate = new Date(reminderDate.getTime());
		this.customPosition = customPosition;
		this.isCompleted = isCompleted;
	}
	
	public Task(ITask task) {
		this.name = task.getName();
		this.description = task.getDescription();
		this.priority = task.getPriority();
		this.dueDate = task.getDueDate() == null ? null : new Date(task.getDueDate().getTime());
		this.reminderDate = task.getReminderDate() == null ? null : new Date(task.getReminderDate().getTime());
		this.customPosition = task.getCustomPosition();
		this.isCompleted = task.isCompleted();
	}
	
	public Task(String name, String description, boolean completed) {
		this.name = name;
		this.description = description;
		this.priority = new Priority(Constants.PRIORITY_DEFAULT);
		this.dueDate = null;
		this.reminderDate = null;
		this.customPosition = Constants.CUSTOMPOSITION_DEFAULT;
		this.isCompleted = completed;
	}
	                                                                                                                                                 
	
	@Override
	public int getCustomPosition() {
		return customPosition;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Date getDueDate() {
		return dueDate;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPriority getPriority() {
		return priority;
	}

	@Override
	public Date getReminderDate() {
		return reminderDate;
	}

	@Override
	public boolean isCompleted() {
		return isCompleted;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
