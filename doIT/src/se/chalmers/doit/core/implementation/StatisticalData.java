package se.chalmers.doit.core.implementation;

import java.util.Date;

import se.chalmers.doit.core.IStatisticalData;

public class StatisticalData implements IStatisticalData {

	private final Date date;

	private int finishedTasks;
	private int createdTasks;
	private int deletedTasks;
	private int overdueTasks;
	private int createdLists;
	private int deletedLists;

	public StatisticalData(final Date date) {

		this.date = date;

		finishedTasks = createdTasks = deletedTasks = overdueTasks = createdLists = deletedLists = 0;
	}

	@Override
	public void addFinishedTasks(final int amount) {
		finishedTasks += amount;
	}

	@Override
	public void addCreatedTasks(final int amount) {
		createdTasks += amount;

	}

	@Override
	public void addDeletedTasks(final int amount) {
		deletedTasks += amount;

	}

	@Override
	public void addOverdueTasks(final int amount) {
		overdueTasks += amount;

	}

	@Override
	public void addCreatedLists(final int amount) {
		createdLists += amount;

	}

	@Override
	public void addDeletedLists(final int amount) {
		deletedLists += amount;

	}

	@Override
	public Date getDate() {
		return (Date) date.clone();
	}

	@Override
	public int getFinishedTasks() {
		return finishedTasks;
	}

	@Override
	public int getCreatedTasks() {
		return createdTasks;
	}

	@Override
	public int getDeletedTasks() {
		return deletedTasks;
	}

	@Override
	public int getOverdueTasks() {
		return overdueTasks;
	}

	@Override
	public int getCreatedLists() {
		return createdLists;
	}

	@Override
	public int getDeletedLists() {
		return deletedLists;
	}

	@Override
	public boolean sameDay(final Date otherDate) {
		boolean ret = false;

		if (date.getYear() == otherDate.getYear()
				&& date.getMonth() == otherDate.getMonth()
				&& date.getDate() == otherDate.getDate()) {

			ret = true;

		}

		return ret;
	}

}
