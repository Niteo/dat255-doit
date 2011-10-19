package se.chalmers.doit.data.storage.implementation;

import java.util.*;

import se.chalmers.doit.core.*;
import se.chalmers.doit.core.implementation.*;
import se.chalmers.doit.data.storage.IDataSQL;
import se.chalmers.doit.util.implementation.SQLConstants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Persistent data class, storing and retrieving task/list data from a SQLite
 * database on Android mobile platform.
 * 
 * @author Kaufmann
 * 
 */
public class DataSQL implements IDataSQL {

	private SQLiteDatabase db = null;

	@Override
	public int addList(final ITaskCollection list) {
		return _addLists(new ITaskCollection[] { list })[0];
	}

	@Override
	public int[] addLists(final ITaskCollection[] lists) {
		return _addLists(lists);
	}

	@Override
	public int addTask(final ITask task, final int listID) {
		return _addTasks(new ITask[] { task }, listID)[0];
	}

	@Override
	public int[] addTasks(final ITask[] tasks, final int listID) {
		return _addTasks(tasks, listID);
	}

	@Override
	public void clearData() {
		db.delete(SQLConstants.LIST_TABLE_NAME, null, null);
		db.delete(SQLConstants.TASK_TABLE_NAME, null, null);
	}

	@Override
	public boolean editList(final int listID,
			final ITaskCollection newListProperties) {
		int nAffected = db.update(SQLConstants.LIST_TABLE_NAME,
				_getContentValuesList(newListProperties), SQLConstants.LIST_ID
						+ "=" + listID, null);
		switch (nAffected) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				throw new IllegalStateException(
						"More than one line was modified. Database corrupt!");
		}
	}

	@Override
	public boolean editTask(final int taskID, final ITask newTaskProperties) {
		int nAffected = db.update(SQLConstants.TASK_TABLE_NAME,
				_getContentValuesTask(newTaskProperties), SQLConstants.TASK_ID
						+ "=" + taskID, null);
		switch (nAffected) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				throw new IllegalStateException(
						"More than one line was modified. Database corrupt!");
		}
	}

	@Override
	public Map<ITaskCollection, Integer> getAllLists() {
		Map<ITaskCollection, Integer> ret = new HashMap<ITaskCollection, Integer>();
		Cursor cur = _getListCursor();
		if (cur.moveToFirst()) {
			do {
				ret.put(new TaskCollection(cur.getString(cur
						.getColumnIndex(SQLConstants.LIST_NAME))), Integer
						.valueOf(cur.getInt(cur
								.getColumnIndex(SQLConstants.LIST_ID))));
			} while (cur.moveToNext());
		}
		cur.close();
		return ret;
	}

	@Override
	public Map<ITask, Integer> getAllTasks() {
		Map<ITask, Integer> ret = new HashMap<ITask, Integer>();
		Cursor cur = _getTaskCursor();

		if (cur.moveToFirst()) {
			do {

				Date dueDate = null;
				Date reminderDate = null;

				if (!cur.isNull(cur.getColumnIndex(SQLConstants.TASK_DUEDATE))) {
					dueDate = new Date(cur.getLong(cur
							.getColumnIndex(SQLConstants.TASK_DUEDATE)));
				}
				if (!cur.isNull(cur
						.getColumnIndex(SQLConstants.TASK_REMINDERDATE))) {
					reminderDate = new Date(cur.getLong(cur
							.getColumnIndex(SQLConstants.TASK_REMINDERDATE)));
				}

				Task key = new Task(
						cur.getString(cur
								.getColumnIndex(SQLConstants.TASK_NAME)),
						cur.getString(cur
								.getColumnIndex(SQLConstants.TASK_DESCRIPTION)),
						new Priority((byte) cur.getInt(cur
								.getColumnIndex(SQLConstants.TASK_PRIORITY))),
						dueDate,
						reminderDate,
						cur.getInt(cur
								.getColumnIndex(SQLConstants.TASK_CUSTOMPOS)),
						cur.getInt(cur
								.getColumnIndex(SQLConstants.TASK_COMPLETED)) == 1);

				Integer value = Integer.valueOf(cur.getInt(cur
						.getColumnIndex(SQLConstants.TASK_ID)));
				ret.put(key, value);
			} while (cur.moveToNext());
		}
		cur.close();
		return ret;
	}

	@Override
	public int[] getTaskIDs(final int listID) {
		Cursor cur = db.rawQuery("SELECT * FROM "
				+ SQLConstants.TASK_TABLE_NAME + " WHERE "
				+ SQLConstants.TASK_CONNECTED_LIST_ID + "=" + listID, null);

		ArrayList<Integer> tempList = new ArrayList<Integer>();
		if (cur.moveToFirst()) {
			int colIndex = cur.getColumnIndex(SQLConstants.TASK_ID);
			do {
				tempList.add(Integer.valueOf(cur.getInt(colIndex)));
			} while (cur.moveToNext());
		}
		int[] ret = new int[tempList.size()];
		int counter = 0;
		for (Integer i : tempList) {
			ret[counter] = i.intValue();
			counter++;
		}
		cur.close();
		return ret;
	}

	@Override
	public boolean moveTask(final int taskID, final int listID) {
		ContentValues cv = new ContentValues();
		cv.put(SQLConstants.TASK_CONNECTED_LIST_ID, Integer.valueOf(listID));
		int nAffected = db.update(SQLConstants.TASK_TABLE_NAME, cv,
				SQLConstants.TASK_ID + "=" + taskID, null);
		switch (nAffected) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				throw new IllegalStateException(
						"More than one line was modified. Database corrupt!");
		}
	}

	@Override
	public boolean removeList(final int listID) {
		return _removeList(listID);
	}

	@Override
	public boolean[] removeLists(final int[] listIDs) {
		boolean[] ret = new boolean[listIDs.length];
		for (int i = 0; i < listIDs.length; i++) {
			ret[i] = _removeList(listIDs[i]);
		}
		return ret;
	}

	@Override
	public boolean removeTask(final int taskID) {
		return _removeTask(taskID);
	}

	@Override
	public boolean[] removeTasks(final int[] taskIDs) {
		boolean[] ret = new boolean[taskIDs.length];
		for (int i = 0; i < taskIDs.length; i++) {
			ret[i] = _removeTask(taskIDs[i]);
		}
		return ret;
	}

	@Override
	public void setDatabase(final SQLiteDatabase database) {
		this.db = database;
		this.db.execSQL(SQLConstants.CREATE_TABLE_TASKS);
		this.db.execSQL(SQLConstants.CREATE_TABLE_LISTS);
	}

	private int[] _addLists(final ITaskCollection[] lists) {
		boolean[] rowAdded = new boolean[lists.length];

		// Add each list to the database and retrieve it's row's value
		for (int i = 0; i < lists.length; i++) {
			rowAdded[i] = db.insert(SQLConstants.LIST_TABLE_NAME, null,
					_getContentValuesList(lists[i])) != -1;
		}

		return _getLastAddedIDs(SQLConstants.LIST_TABLE_NAME, rowAdded,
				SQLConstants.LIST_ID);
	}

	private int[] _addTasks(final ITask[] tasks, final int listID) {
		boolean[] rowAdded = new boolean[tasks.length];

		// Add each task to the database and retrieve it's row's value
		for (int i = 0; i < tasks.length; i++) {
			ContentValues cv = _getContentValuesTask(tasks[i]);
			cv.put(SQLConstants.TASK_CONNECTED_LIST_ID, Integer.valueOf(listID));
			rowAdded[i] = db.insert(SQLConstants.TASK_TABLE_NAME, null, cv) != -1;
		}

		return _getLastAddedIDs(SQLConstants.TASK_TABLE_NAME, rowAdded,
				SQLConstants.TASK_ID);
	}

	private ContentValues _getContentValuesList(final ITaskCollection list) {
		ContentValues ret = new ContentValues();
		ret.put(SQLConstants.LIST_NAME, list.getName());
		return ret;
	}

	private ContentValues _getContentValuesTask(final ITask task) {
		// Takes all data in a task and puts it into a ContentValues
		ContentValues ret = new ContentValues();
		ret.put(SQLConstants.TASK_DESCRIPTION, task.getDescription());
		if (task.getDueDate() == null) {
			ret.putNull(SQLConstants.TASK_DUEDATE);
		} else {
			ret.put(SQLConstants.TASK_DUEDATE,
					Long.valueOf(task.getDueDate().getTime()));
		}
		ret.put(SQLConstants.TASK_NAME, task.getName());
		ret.put(SQLConstants.TASK_PRIORITY,
				Byte.valueOf(task.getPriority().getValue()));
		if (task.getReminderDate() == null) {
			ret.putNull(SQLConstants.TASK_REMINDERDATE);
		} else {
			ret.put(SQLConstants.TASK_REMINDERDATE,
					Long.valueOf(task.getReminderDate().getTime()));
		}
		ret.put(SQLConstants.TASK_COMPLETED,
				Integer.valueOf(task.isCompleted() ? 1 : 0));
		ret.put(SQLConstants.TASK_CUSTOMPOS,
				Integer.valueOf(task.getCustomPosition()));

		return ret;
	}

	private int[] _getLastAddedIDs(final String tableName,
			final boolean[] idsToGet, final String idName) {
		int[] idArray = new int[idsToGet.length];
		Cursor cur = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY "
				+ idName, null);
		cur.moveToLast();
		for (int i = 0, j = idArray.length - 1; i < idArray.length; i++, j--) {
			if (idsToGet[j]) {
				idArray[i] = cur.getInt(cur.getColumnIndex(idName));
			} else {
				idArray[i] = -1;
			}
			if (!cur.moveToPrevious()) {
				break;
			}
		}
		cur.close();
		return idArray;
	}

	private Cursor _getListCursor() {
		// Returns a cursor pointing to all rows in the list table
		return db.rawQuery(SQLConstants.SELECT_ALL_LISTS, null);
	}

	private Cursor _getTaskCursor() {
		// Returns a cursor pointing to all rows in the task table
		return db.rawQuery(SQLConstants.SELECT_ALL_TASKS, null);
	}

	private boolean _removeList(final int id) {
		int nAffected = db.delete(SQLConstants.LIST_TABLE_NAME,
				SQLConstants.LIST_ID + "=" + id, null);
		switch (nAffected) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				throw new IllegalStateException(
						"More than one line was modified. Database corrupt!");
		}
	}

	private boolean _removeTask(final int id) {
		int nAffected = db.delete(SQLConstants.TASK_TABLE_NAME,
				SQLConstants.LIST_ID + "=" + id, null);
		switch (nAffected) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				throw new IllegalStateException(
						"More than one line was modified. Database corrupt!");
		}
	}
}