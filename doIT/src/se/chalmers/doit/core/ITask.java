package se.chalmers.doit.core;

import java.util.Date;

public interface ITask {

	public int getID();

	public String getName();

	public String getDescription();

	public Date getDueDate();

	public IPriority getPriority();

	public Date getReminderDate();

	public int getCustomPosition();

	public boolean isCompleted();

}
