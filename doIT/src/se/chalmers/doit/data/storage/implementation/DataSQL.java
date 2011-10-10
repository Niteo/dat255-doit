package se.chalmers.doit.data.storage.implementation;

import java.util.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.*;

import se.chalmers.doit.core.*;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.IDataStorage;
import se.chalmers.doit.util.implementation.SQLConstants;

/**
 * Persistent data class, storing and retrieving task/list data from a SQLite database on Android mobile platform.
 * @author Kaufmann
 *
 */
public class DataSQL implements IDataStorage {

	private SQLiteDatabase db = null;
	
	/**
	 * Sets the database to use for storage. Tables will be created if they don't exist in the database.
	 * @param database database to add
	 */
	public void setDatabase(SQLiteDatabase database)
	{
		this.db = database;
		this.db.execSQL(SQLConstants.CREATE_TABLE_TASKS);
		this.db.execSQL(SQLConstants.CREATE_TABLE_LISTS);
	}
	
	@Override
	public boolean addList(ITaskCollection collection) {
		ContentValues cv = new ContentValues();
		cv.put(SQLConstants.LIST_NAME, collection.getName());
		return db.insert(SQLConstants.LIST_TABLE_NAME, "", cv) != -1;
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
		Collection<ITaskCollection> ret = new ArrayList<ITaskCollection>();
		Cursor cur = db.rawQuery("SELECT * FROM " + SQLConstants.LIST_TABLE_NAME, null);
		if(cur.moveToFirst())
		{
			for(int i = 0; i < cur.getCount(); i++){
				ret.add(new TaskCollection(cur.getString(1)));
				cur.moveToNext();
			}
		}
		return ret;
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
