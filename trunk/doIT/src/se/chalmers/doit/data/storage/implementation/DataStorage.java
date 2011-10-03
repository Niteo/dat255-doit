package se.chalmers.doit.data.storage.implementation;

import java.util.*;

import se.chalmers.doit.core.*;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.IDataStorage;
import se.chalmers.doit.data.storage.wrappers.ITaskCollectionWrapper;
import se.chalmers.doit.data.storage.wrappers.implementation.TaskCollectionWrapper;

/**
 * Class storing cached data and delegating datachanges to ... TODO: Describe
 * better...
 * 
 * @author Kaufmann
 */

//TODO Add connections to DB
public class DataStorage implements IDataStorage {

	// TODO: Add hash maps :)
	private Collection<ITaskCollection> lists = new ArrayList<ITaskCollection>();
	private static int listIDEnumerator = 0; // TODO: Remove with db connection
	private static int taskIDEnumerator = 0; // TODO: Remove with db connection
	
	@Override
	public void addList(ITaskCollection collection) {
		lists.add(new TaskCollectionWrapper(listIDEnumerator, collection));
		listIDEnumerator++;
	}
	
	@Override
	public void addLists(Collection<ITaskCollection> collection) {
		for(ITaskCollection t : collection){
			addList(t);
		}
		// TODO Refactor this to use a private _addList method instead.
	}

	@Override
	public void addTask(ITask task, ITaskCollection collection) {
		Collection oldTasks = collection.getTasks();
		String oldName = collection.getName();
		oldTasks.add(task);
		if(lists.remove(collection)){
			lists.add(new TaskCollection(oldName, oldTasks));
		}
	}

	@Override
	public void addTasks(Collection<ITask> tasks, ITaskCollection collection) {
		Collection oldTasks = collection.getTasks();
		String oldName = collection.getName();
		oldTasks.addAll(tasks);
		if(lists.remove(collection)){
			lists.add(new TaskCollection(oldName, oldTasks));
		}
	}

	@Override
	public void clearData() {
		lists.clear();
		listIDEnumerator = 0;
		taskIDEnumerator = 0;
	}

	@Override
	public void editList(ITaskCollection oldCollection,
			ITaskCollection newCollection) {
		lists.remove(oldCollection);
		lists.add(newCollection);
	}

	@Override
	public void editTask(ITask oldTask, ITask newTask) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<ITaskCollection> getAllLists() {
		Collection<ITaskCollection> ret = new ArrayList<ITaskCollection>();
		for(ITaskCollection w : lists)
		{
			ret.add(w);
		}
		return ret;
	}

	@Override
	public Collection<ITask> getAllTasks() {
		Collection<ITask> ret = new ArrayList<ITask>();
		for(ITaskCollection w : lists){
			ret.addAll(w.getTasks());
		}
		return ret;
	}

	@Override
	public void moveTask(ITask task, ITaskCollection collection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeList(ITaskCollection collection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLists(Collection<ITaskCollection> collection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTask(ITask task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTasks(Collection<ITask> listOfTasksToRemove) {
		// TODO Auto-generated method stub

	}
}