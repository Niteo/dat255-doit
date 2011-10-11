package se.chalmers.doit.data.storage.implementation;

import java.util.Collection;

import se.chalmers.doit.core.*;
import se.chalmers.doit.data.storage.*;

public class DataStorage implements IDataStorage {

	IDataStorage cache;
	ISQLData sql;
	// TODO: Hashmap here

	public DataStorage() {
	}

	@Override
	public boolean addList(ITaskCollection collection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int addLists(Collection<ITaskCollection> collection) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addTask(ITask task, ITaskCollection collection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int addTasks(Collection<ITask> tasks, ITaskCollection collection) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean editList(ITaskCollection oldCollection,
			ITaskCollection newCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editTask(ITask oldTask, ITask newTask) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<ITaskCollection> getAllLists() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ITask> getAllTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveTask(ITask task, ITaskCollection collection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeList(ITaskCollection collection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int removeLists(Collection<ITaskCollection> collection) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean removeTask(ITask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int removeTasks(Collection<ITask> listOfTasksToRemove) {
		// TODO Auto-generated method stub
		return 0;
	}

}
