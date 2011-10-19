package se.chalmers.doit.data.storage.implementation;

import java.util.*;

import se.chalmers.doit.core.IStatisticalData;
import se.chalmers.doit.core.implementation.StatisticalData;
import se.chalmers.doit.data.storage.IStatisticsDataStorage;
import android.content.SharedPreferences;

public class StatisticsDataStorage implements IStatisticsDataStorage {

	private SharedPreferences pref;
	private final String TASKS_FINISHED = "FINISHEDTASKS";
	private final String TASKS_CREATED = "CREATEDTASKS";
	private final String TASKS_DELETED = "DELETEDTASKS";
	private final String TASKS_OVERDUE = "OVERDUETASKS";
	private final String LISTS_CREATED = "CREATEDLISTS";
	private final String LISTS_DELETED = "DELETELISTS";
	
	public StatisticsDataStorage(SharedPreferences pref){
		this.pref = pref;
	}
	
	@Override
	public void reportFinishedTasks(int numberOfFinishedTasks, Date date) {
		_addToVariable(TASKS_FINISHED, numberOfFinishedTasks);
	}

	@Override
	public void reportCreatedTasks(int numberOFCreatedTasks, Date date) {
		_addToVariable(TASKS_CREATED, numberOFCreatedTasks);
	}

	@Override
	public void reportDeletedTasks(int numberOfDeletedTasks, Date date) {
		_addToVariable(TASKS_DELETED, numberOfDeletedTasks);
	}

	@Override
	public void reportOverdueTasks(int numberOfOverdueTasks, Date date) {
		_addToVariable(TASKS_OVERDUE, numberOfOverdueTasks);
	}

	@Override
	public void reportCreatedLists(int numberOfCreatedLists, Date date) {
		_addToVariable(LISTS_CREATED, numberOfCreatedLists);
	}

	@Override
	public void reportDeletedLists(int numberOfDeletedLists, Date date) {
		_addToVariable(LISTS_DELETED, numberOfDeletedLists);
	}

	@Override
	public Collection<IStatisticalData> getStatisticsData() {
		Collection<IStatisticalData> ret = new ArrayList<IStatisticalData>();
		StatisticalData dat = new StatisticalData(new Date());
		dat.addCreatedLists(pref.getInt(LISTS_CREATED, 0));
		dat.addCreatedTasks(pref.getInt(TASKS_CREATED, 0));
		dat.addDeletedLists(pref.getInt(LISTS_DELETED, 0));
		dat.addDeletedTasks(pref.getInt(TASKS_DELETED, 0));
		dat.addFinishedTasks(pref.getInt(TASKS_FINISHED, 0));
		dat.addOverdueTasks(pref.getInt(TASKS_OVERDUE, 0));
		ret.add(dat);
		return ret;
	}

	@Override
	public void clearData() {
		_clearVariable(LISTS_CREATED);
		_clearVariable(LISTS_DELETED);
		_clearVariable(TASKS_CREATED);
		_clearVariable(TASKS_DELETED);
		_clearVariable(TASKS_FINISHED);
		_clearVariable(TASKS_OVERDUE);
	}
	
	private void _addToVariable(String name, int value){
		int toSet = pref.getInt(name, 0) + value;
		SharedPreferences.Editor ed = pref.edit();
		ed.putInt(name, toSet);
		ed.commit();
	}
	
	private void _clearVariable(String name){
		SharedPreferences.Editor ed = pref.edit();
		ed.putInt(name, 0);
		ed.commit();
	}
}
