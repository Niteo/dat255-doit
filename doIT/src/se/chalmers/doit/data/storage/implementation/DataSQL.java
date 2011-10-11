package se.chalmers.doit.data.storage.implementation;

import java.util.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.*;

import se.chalmers.doit.core.*;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.*;
import se.chalmers.doit.util.implementation.SQLConstants;

/**
 * Persistent data class, storing and retrieving task/list data from a SQLite
 * database on Android mobile platform.
 * 
 * @author Kaufmann
 * 
 */
public class DataSQL implements ISQLData {

	private SQLiteDatabase db = null;

	@Override
	public void setDatabase(SQLiteDatabase database) {
		this.db = database;
		this.db.execSQL(SQLConstants.CREATE_TABLE_TASKS);
		this.db.execSQL(SQLConstants.CREATE_TABLE_LISTS);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<ITaskCollection> getAllLists() {
		Collection<ITaskCollection> ret = new ArrayList<ITaskCollection>();
		Cursor cur = db.rawQuery("SELECT * FROM "
				+ SQLConstants.LIST_TABLE_NAME, null);

		while (cur.moveToNext()) {
			ret.add(new TaskCollection(cur.getString(1)));
		}

		return ret;
	}

	@Override
	public int addList(ITaskCollection list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] addLists(ITaskCollection[] lists) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addTask(ITask task, int listID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] addTasks(ITask[] tasks, int listID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editList(int listID, ITaskCollection newListProperties) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editTask(ITask taskID, ITask newTaskProperties) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<ITask> getAllTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveTask(int taskID, int listID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeList(int listID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean[] removeLists(int[] listIDs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeTask(int taskID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean[] removeTasks(boolean[] taskIDs) {
		// TODO Auto-generated method stub
		return null;
	}

}