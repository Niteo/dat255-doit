package se.chalmers.doit.core;

import java.util.Date;

/**
 * An interface for immutable classes carrying properties of a task.
 * 
 * @author Kaufmann
 * 
 */
public interface ITask {

	/**
	 * Returns the represented task's name.
	 * 
	 * @return String representing the ID of the task
	 */
	public int getCustomPosition();

	/**
	 * Returns the represented task's description.
	 * 
	 * @return String representing the description of the task
	 */
	public String getDescription();

	/**
	 * Returns the represented task's due date.
	 * 
	 * @return date representing the due date of the task
	 */
	public Date getDueDate();

	/**
	 * Returns the represented task's name.
	 * 
	 * @return String representing the ID of the task
	 */
	public String getName();

	/**
	 * Returns the represented task's priority.
	 * 
	 * @return IPriority representing the priority of the task
	 */
	public IPriority getPriority();

	/**
	 * Returns the represented task's reminder date.
	 * 
	 * @return String representing the reminder date of the task
	 */
	public Date getReminderDate();

	/**
	 * Returns true if the task is completed, else false.
	 * 
	 * @return boolean representing if the task is completed or not
	 */
	public boolean isCompleted();
}
