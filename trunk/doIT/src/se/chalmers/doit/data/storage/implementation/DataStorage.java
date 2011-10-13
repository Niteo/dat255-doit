package se.chalmers.doit.data.storage.implementation;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.data.storage.IDataStorage;
import se.chalmers.doit.data.storage.IDataSQL;

public class DataStorage implements IDataStorage {

	IDataStorage cache;
	IDataSQL sql;

	// TODO: Hashmap here

	public DataStorage() {
		cache = new DataCache();
		sql = new DataSQL();
	}

	@Override
	public boolean addList(final ITaskCollection collection) {

		// TODO: Add SQL
		return cache.addList(collection);
	}

	@Override
	public int addLists(final Collection<ITaskCollection> collection) {
		// TODO: Add SQL
		return cache.addLists(collection);
	}

	@Override
	public boolean addTask(final ITask task, final ITaskCollection collection) {
		// TODO: Add SQL
		return cache.addTask(task, collection);
	}

	@Override
	public int addTasks(final Collection<ITask> tasks,
			final ITaskCollection collection) {
		// TODO: Add SQL
		return cache.addTasks(tasks, collection);
	}

	@Override
	public void clearData() {

		// sql.clearData();
		cache.clearData();

	}

	@Override
	public boolean editList(final ITaskCollection oldCollection,
			final ITaskCollection newCollection) {
		// TODO: Add SQL
		return cache.editList(oldCollection, newCollection);
	}

	@Override
	public boolean editTask(final ITask oldTask, final ITask newTask) {
		// TODO: Add SQL
		return cache.editTask(oldTask, newTask);
	}

	@Override
	public Collection<ITaskCollection> getAllLists() {
		return cache.getAllLists();
	}

	@Override
	public Collection<ITask> getAllTasks() {
		return cache.getAllTasks();
	}

	@Override
	public boolean moveTask(final ITask task, final ITaskCollection collection) {
		// TODO: Add SQL
		return cache.moveTask(task, collection);
	}

	@Override
	public boolean removeList(final ITaskCollection collection) {
		// TODO: Add SQL
		return cache.removeList(collection);
	}

	@Override
	public int removeLists(final Collection<ITaskCollection> collection) {
		// TODO: Add SQL
		return cache.removeLists(collection);
	}

	@Override
	public boolean removeTask(final ITask task) {
		// TODO: Add SQL
		return cache.removeTask(task);
	}

	@Override
	public int removeTasks(final Collection<ITask> listOfTasksToRemove) {
		// TODO: Add SQL
		return cache.removeTasks(listOfTasksToRemove);
	}

}
