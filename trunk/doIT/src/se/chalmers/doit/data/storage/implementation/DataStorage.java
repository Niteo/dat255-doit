package se.chalmers.doit.data.storage.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.IDataSQL;
import se.chalmers.doit.data.storage.IDataStorage;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 * @author Boel
 *
 */
public class DataStorage implements IDataStorage {

	private IDataStorage cache;
	private IDataSQL sql;
	// Maps ITaskCollection to correspond to an ID
	private Map<ITaskCollection, Integer> listMap;
	// Maps ITask to correspond to an ID
	private Map<ITask, Integer> taskMap;

	public DataStorage(SQLiteDatabase database) {
		cache = new DataCache();
		sql = new DataSQL();

		sql.setDatabase(database);

		listMap = sql.getAllLists();
		taskMap = sql.getAllTasks();

		_populateCache();

	}

	@Override
	public boolean addList(final ITaskCollection collection) {
		return _addList(collection);

	}

	@Override
	public int addLists(final Collection<ITaskCollection> collection) {

		int count = 0;
		for (ITaskCollection list : collection) {
			if (_addList(list)) {
				count++;
			}
		}

		return count;
	}

	@Override
	public boolean addTask(final ITask task, final ITaskCollection collection) {

		return _addTask(task, collection);
	}

	@Override
	public int addTasks(final Collection<ITask> tasks,
			final ITaskCollection collection) {
		int count = 0;

		for (ITask t : tasks) {
			if (_addTask(t, collection)) {
				count++;
			}

		}
		return count;
	}

	@Override
	public void clearData() {

		sql.clearData();
		cache.clearData();

		listMap.clear();
		taskMap.clear();

	}

	@Override
	public boolean editList(final ITaskCollection oldCollection,
			final ITaskCollection newCollection) {

		int listID = listMap.get(oldCollection).intValue();

		if (sql.editList(listID, newCollection)) {
			_rebuildCache();
			return true;
		}
		return false;

	}

	@Override
	public boolean editTask(final ITask oldTask, final ITask newTask) {
		int taskID = taskMap.get(oldTask).intValue();

		if (sql.editTask(taskID, newTask)) {
			_rebuildCache();
			return true;
		}

		return false;
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

		int listID = listMap.get(collection).intValue();
		int taskID = taskMap.get(task).intValue();

		if (sql.moveTask(taskID, listID)) {
			_rebuildCache();
			return true;
		}

		return false;
	}

	@Override
	public boolean removeList(final ITaskCollection collection) {

		int listID = listMap.get(collection).intValue();

		if (sql.removeList(listID)) {
			// Remove the tasks in the list from the database too!
			for (ITask task : collection.getTasks()) {
				int taskID = taskMap.get(task).intValue();
				sql.removeTask(taskID);
			}
			_rebuildCache();
			return true;
		}

		return false;
	}

	@Override
	public int removeLists(final Collection<ITaskCollection> collection) {

		int count = 0;
		for (ITaskCollection l : collection) {
			removeList(l);
			count++;
		}

		return count;
	}

	@Override
	public boolean removeTask(final ITask task) {
		if (_removeTask(task)) {
			_rebuildCache();
			return true;
		}

		return false;
	}

	@Override
	public int removeTasks(final Collection<ITask> listOfTasksToRemove) {

		int count = 0;

		for (ITask t : listOfTasksToRemove) {
			if (_removeTask(t)) {
				count++;
			}
		}
		_rebuildCache();
		return count;
	}

	private void _populateCache() {
		// Get all ITaskCollections
		ITaskCollection[] collection = new ITaskCollection[listMap.keySet()
				.size()];
		collection = listMap.keySet().toArray(collection);

		int[] taskIDs = new int[collection.length];

		for (ITaskCollection c : collection) {

			taskIDs = sql.getTaskIDs(listMap.get(c).intValue());
			ITaskCollection taskCollection = new TaskCollection(c.getName(),
					_getTasksFromIDs(taskIDs));
			cache.addList(taskCollection);

			listMap.put(taskCollection, listMap.get(c));
			listMap.remove(c);

		}

	}

	private Collection<ITask> _getTasksFromIDs(int[] taskIDs) {
		Collection<ITask> ret = new ArrayList<ITask>();

		for (int i : taskIDs) {
			for (ITask task : taskMap.keySet()) {
				if (i == taskMap.get(task).intValue()) {
					ret.add(task);
				}

			}
		}
		return ret;
	}

	private void _rebuildCache() {
		cache.clearData();
		listMap = sql.getAllLists();
		taskMap = sql.getAllTasks();

		_populateCache();
	}

	private boolean _removeTask(final ITask task) {

		int taskID = taskMap.get(task).intValue();

		if (sql.removeTask(taskID)) {
			_rebuildCache();
			return true;
		}

		return false;
	}

	public boolean _addList(final ITaskCollection collection) {
		if (!cache.getAllLists().contains(collection)) {
			int id = sql.addList(collection);
			if (id != -1) {
				_rebuildCache();
				return true;
			}
		}
		return false;

	}

	public boolean _addTask(final ITask task, final ITaskCollection collection) {

		for (ITask t : cache.getAllTasks()) {
			if (t == task) {
				return false;
			} else {

				int id = sql.addTask(task, listMap.get(collection).intValue());

				if (id != -1) {
					_rebuildCache();
					break;
				}
			}
		}
		return true;

	}

}
