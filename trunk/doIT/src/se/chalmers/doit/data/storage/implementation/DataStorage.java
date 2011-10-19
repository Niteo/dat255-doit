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
		if (cache.addList(collection)) {
			int id = sql.addList(collection);
			if (id != -1) {
				_addTasksSQL(collection.getTasks(), id);
			}
			_rebuildCache();
			return true;
		}
		return false;

	}

	@Override
	public int addLists(final Collection<ITaskCollection> collection) {
		int[] listIDs;
		int count = 0;

		ITaskCollection[] listArray = new ITaskCollection[collection.size()];
		listArray = collection.toArray(listArray);

		if (cache.addLists(collection) == collection.size()) {
			listIDs = sql.addLists(listArray);

			for (int i = 0; i < listIDs.length; i++) {
				if (listIDs[i] != -1) {
					_addTasksSQL(listArray[i].getTasks(), listIDs[i]);
					count++;
				}
			}
		}

		_rebuildCache();
		return count;
	}

	@Override
	public boolean addTask(final ITask task, final ITaskCollection collection) {
		int id = -1;
		if (cache.addTask(task, collection)) {
			id = sql.addTask(task, listMap.get(collection).intValue());
			_rebuildCache();
		}
		return id != -1;
	}

	@Override
	public int addTasks(final Collection<ITask> tasks,
			final ITaskCollection collection) {
		int[] ids = new int[0];
		if (cache.addTasks(tasks, collection) == tasks.size()) {
			ids = _addTasksSQL(tasks, listMap.get(collection).intValue());
		}
		_rebuildCache();

		int count = 0;
		for (int i : ids) {
			if (i != -1) {
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

		if (cache.removeList(collection)) {
			if (sql.removeList(listMap.get(collection).intValue())) {

				int[] ids = sql.getTaskIDs(listMap.get(collection).intValue());

				sql.removeTasks(ids);

				_rebuildCache();
				return true;
			}
		}

		return false;
	}

	@Override
	public int removeLists(final Collection<ITaskCollection> collection) {
		if (cache.removeLists(collection)==collection.size()) {
			int[] ids = new int[collection.size()];
			int index = 0;
			for(ITaskCollection c : collection){
				ids[index++] = listMap.get(c).intValue();
			}

			int count = 0;
			for (int id : ids) {
				int[] taskIds = sql.getTaskIDs(id);
				if(sql.removeList(id)){
					sql.removeTasks(taskIds);
					count++;
				}
			}

			return count;
		}

		return 0;
	}

	@Override
	public boolean removeTask(final ITask task) {
		boolean canBeRemoved = false;
		if (cache.removeTask(task)) {
			canBeRemoved = sql.removeTask(taskMap.get(task).intValue());
			_rebuildCache();
		}

		return canBeRemoved;
	}

	@Override
	public int removeTasks(final Collection<ITask> listOfTasksToRemove) {
		int[] array = new int[listOfTasksToRemove.size()];

		int loopCount = 0;
		for (ITask t : listOfTasksToRemove) {
			array[loopCount++] = taskMap.get(t).intValue();
		}

		if (cache.removeTasks(listOfTasksToRemove) == listOfTasksToRemove
				.size()) {
			boolean[] boolArray = sql.removeTasks(array);

			int count = 0;
			for (boolean i : boolArray) {
				count += i ? 1 : 0;
			}
			_rebuildCache();
			return count;
		}

		return 0;
	}

	private void _populateCache() {
		// Get all ITaskCollections
		ITaskCollection[] collection = new ITaskCollection[listMap.keySet()
				.size()];
		collection = listMap.keySet().toArray(collection);

		// Loop through each list and add it
		for (ITaskCollection c : collection) {
			// Get all ITasks
			int[] taskIDs = sql.getTaskIDs(listMap.get(c).intValue());
			ITaskCollection taskCollection = new TaskCollection(c.getName(),
					_getTasksFromIDs(taskIDs));
			cache.addList(taskCollection);

			// TODO: check this out
			Integer id = listMap.get(c);
			listMap.remove(c);
			listMap.put(taskCollection, id);

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

	private int[] _addTasksSQL(final Collection<ITask> tasks, final int listID) {
		ITask[] array = new ITask[tasks.size()];
		array = tasks.toArray(array);

		return sql.addTasks(array, listID);
	}

}
