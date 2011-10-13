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
	public void setDatabase(SQLiteDatabase database) {
		this.db = database;
		// Creates tasks and list tables if they do not exist
		this.db.execSQL(SQLConstants.CREATE_TABLE_TASKS);
		this.db.execSQL(SQLConstants.CREATE_TABLE_LISTS);
	}

	@Override
	public void clearData() {
		// Deletes all lists and tasks but keeps the tables
		db.delete(SQLConstants.LIST_TABLE_NAME, null, null);
		db.delete(SQLConstants.TASK_TABLE_NAME, null, null);
	}

	@Override
	public Collection<ITaskCollection> getAllLists() {
		Collection<ITaskCollection> ret = new ArrayList<ITaskCollection>();
		Cursor cur = _getListCursor();
		if (cur.moveToFirst()) {
			do {
				ret.add(new TaskCollection(cur.getString(1)));
			} while (cur.moveToNext());
		}
		return ret;
	}

	@Override
	public int addList(ITaskCollection list) {
		return _addLists(new ITaskCollection[] { list })[0];
	}

	@Override
	public int[] addLists(ITaskCollection[] lists) {
		return _addLists(lists);
	}

	@Override
	public int addTask(ITask task, int listID) {
		return _addTasks(new ITask[] { task }, listID)[0];
	}

	@Override
	public int[] addTasks(ITask[] tasks, int listID) {
		return _addTasks(tasks, listID);
	}

	@Override
	public boolean editList(int listID, ITaskCollection newListProperties) {
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
	public boolean editTask(int taskID, ITask newTaskProperties) {
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
	public Collection<ITask> getAllTasks() {
		ArrayList<ITask> ret = new ArrayList<ITask>();
		Cursor cur = _getTaskCursor();

		if (cur.moveToFirst()) {
			do {
				ret.add(new Task(
						cur.getString(cur
								.getColumnIndex(SQLConstants.TASK_NAME)),
						cur.getString(cur
								.getColumnIndex(SQLConstants.TASK_DESCRIPTION)),
						new Priority((byte) cur.getInt(cur
								.getColumnIndex(SQLConstants.TASK_PRIORITY))),
						new Date(cur.getInt(cur
								.getColumnIndex(SQLConstants.TASK_DUEDATE))),
						new Date(
								cur.getInt(cur
										.getColumnIndex(SQLConstants.TASK_REMINDERDATE))),
						cur.getInt(cur
								.getColumnIndex(SQLConstants.TASK_CUSTOMPOS)),
						cur.getInt(cur
								.getColumnIndex(SQLConstants.TASK_COMPLETED)) == 1));
			} while (cur.moveToNext());
		}
		return ret;
	}

	@Override
	public boolean moveTask(int taskID, int listID) {
		ContentValues cv = new ContentValues();
		cv.put(SQLConstants.TASK_CONNECTED_LIST_ID, listID);
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
	public boolean removeList(int listID) {
		return _removeList(listID);
	}

	@Override
	public boolean[] removeLists(int[] listIDs) {
		boolean[] ret = new boolean[listIDs.length];
		for (int i = 0; i < listIDs.length; i++) {
			ret[i] = _removeList(listIDs[i]);
		}
		return ret;
	}

	@Override
	public boolean removeTask(int taskID) {
		return _removeTask(taskID);
	}

	@Override
	public boolean[] removeTasks(int[] taskIDs) {
		boolean[] ret = new boolean[taskIDs.length];
		for (int i = 0; i < taskIDs.length; i++) {
			ret[i] = _removeTask(taskIDs[i]);
		}
		return ret;
	}

	private boolean _removeList(int id) {
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

	private boolean _removeTask(int id) {
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

	private int[] _addTasks(ITask[] tasks, int listID) {
		int[] rowIDs = new int[tasks.length];

		// Add each task to the database and retrieve it's row's value
		for (int i = 0; i < tasks.length; i++) {
			ContentValues cv = _getContentValuesTask(tasks[i]);
			cv.put(SQLConstants.TASK_CONNECTED_LIST_ID, listID);
			rowIDs[i] = (int) db.insert(SQLConstants.TASK_TABLE_NAME, null, cv);
		}

		return _getIDsfromRows(_getTaskCursor(), rowIDs);
	}

	private int[] _addLists(ITaskCollection[] lists) {
		int[] rowIDs = new int[lists.length];

		// Add each list to the database and retrieve it's row's value
		for (int i = 0; i < lists.length; i++) {
			rowIDs[i] = (int) db.insert(SQLConstants.LIST_TABLE_NAME, null,
					_getContentValuesList(lists[i]));
		}

		return _getIDsfromRows(_getListCursor(), rowIDs);
	}

	private int[] _getIDsfromRows(Cursor cur, int[] row) {
		int[] idArray = new int[row.length];

		cur.moveToFirst();
		// Move cursor to each specified row and pull the id
		for (int i = 0; i < row.length; i++) {
			if (cur.moveToPosition(row[i])) {
				idArray[i] = cur.getInt(0);
			} else {
				idArray[i] = -1;
			}
		}

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

	private ContentValues _getContentValuesList(ITaskCollection list) {
		ContentValues ret = new ContentValues();
		ret.put(SQLConstants.LIST_NAME, list.getName());
		return ret;
	}

	private ContentValues _getContentValuesTask(ITask task) {
		ContentValues ret = new ContentValues();
		ret.put(SQLConstants.TASK_DESCRIPTION, task.getDescription());
		if(task.getDueDate() == null){
			ret.putNull(SQLConstants.TASK_DUEDATE);
		}
		else {
			ret.put(SQLConstants.TASK_DUEDATE, task.getDueDate().getTime());
		}
		ret.put(SQLConstants.TASK_NAME, task.getName());
		ret.put(SQLConstants.TASK_PRIORITY, task.getPriority().getValue());
		if(task.getReminderDate() == null){
			ret.putNull(SQLConstants.TASK_REMINDERDATE);
		}
		else {
			ret.put(SQLConstants.TASK_REMINDERDATE, task.getReminderDate()
					.getTime());	
		}
		ret.put(SQLConstants.TASK_COMPLETED, task.isCompleted() ? 1 : 0);
		ret.put(SQLConstants.TASK_CUSTOMPOS, task.getCustomPosition());

		return ret;
	}
}