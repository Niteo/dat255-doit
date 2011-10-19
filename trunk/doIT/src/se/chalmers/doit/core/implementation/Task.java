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

	public static boolean isTasksEqual(final ITask t1, final ITask t2) {

		if (t1.getCustomPosition() == t2.getCustomPosition()
				&& t1.isCompleted() == t2.isCompleted()
				&& t1.getDescription().equals(t2.getDescription())
				&& t1.getName().equals(t2.getName())
				&& t1.getPriority().getValue() == t2.getPriority().getValue()) {

			if (t2.getDueDate() != null) {
				t1.getDueDate().equals(t2.getDueDate());

				if (t2.getReminderDate() != null) {
					t1.getReminderDate().equals(t2.getReminderDate());
				} else {
					if (t1.getDueDate() == null) {
						return true;
					}
				}

			} else {
				if (t1.getDueDate() == null) {

					if (t2.getReminderDate() != null) {
						t1.getReminderDate().equals(t2.getReminderDate());
					} else {
						if (t1.getDueDate() == null) {
							return true;
						}
					}

				}
			}
		}
		return false;
	}

	final private int customPosition;
	final private String description;
	final private Date dueDate;
	final private boolean isCompleted;
	final private String name;
	final private IPriority priority;

	final private Date reminderDate;

	public Task(final ITask task) {
		this.name = task.getName();
		this.description = task.getDescription();
		this.priority = task.getPriority();
		this.dueDate = task.getDueDate() == null ? null : new Date(task
				.getDueDate().getTime());
		this.reminderDate = task.getReminderDate() == null ? null : new Date(
				task.getReminderDate().getTime());
		this.customPosition = task.getCustomPosition();
		this.isCompleted = task.isCompleted();
	}

	public Task(final ITask task, final boolean completed) {
		this.name = task.getName();
		this.description = task.getDescription();
		this.priority = task.getPriority();
		this.dueDate = task.getDueDate() == null ? null : new Date(task
				.getDueDate().getTime());
		this.reminderDate = task.getReminderDate() == null ? null : new Date(
				task.getReminderDate().getTime());
		this.customPosition = task.getCustomPosition();
		this.isCompleted = completed;
	}

	public Task(final String name, final String description,
			final boolean completed) {
		this.name = name;
		this.description = description;
		this.priority = new Priority(Constants.PRIORITY_DEFAULT);
		this.dueDate = null;
		this.reminderDate = null;
		this.customPosition = Constants.CUSTOMPOSITION_DEFAULT;
		this.isCompleted = completed;
	}

	public Task(final String name, final String description,
			final IPriority priority, final Date dueDate,
			final Date reminderDate, final int customPosition,
			final boolean isCompleted) {
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
		this.reminderDate = reminderDate == null ? null : new Date(
				reminderDate.getTime());
		this.customPosition = customPosition;
		this.isCompleted = isCompleted;
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
