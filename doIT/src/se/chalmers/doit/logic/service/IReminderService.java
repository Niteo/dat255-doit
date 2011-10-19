package se.chalmers.doit.logic.service;

import java.util.Collection;

import se.chalmers.doit.core.ITask;

/**
 * An interface to set reminder-events.
 *
 * @author Boel
 *
 */
public interface IReminderService {

	/**
	 * Sets reminders for all ITasks in the Collection.
	 *
	 * @param tasks
	 *            The ITasks to set the reminders for
	 */
	public void setReminders(Collection<ITask> tasks);

	/**
	 * Clears all reminders.
	 */
	public void clearReminders();

}
