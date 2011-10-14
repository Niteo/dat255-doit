package se.chalmers.doit.test.data.storage;

import java.util.Collection;
import java.util.Date;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Priority;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.implementation.DataSQL;
import se.chalmers.doit.util.implementation.SQLConstants;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class DataSQLTest extends AndroidTestCase {

	DataSQL db;
	SQLiteDatabase realDb;

	public void setUp() throws Exception {
		db = new DataSQL();
		realDb = SQLiteDatabase.create(null);
		db.setDatabase(realDb);
	}

	public void tearDown() throws Exception {
		realDb.close();
	}

	public void testClearData() {
		TaskCollection list = new TaskCollection("TN1");
		TaskCollection list2 = new TaskCollection("TN2");
		Task task = new Task("Name", "Desc", false);
		db.addList(list);
		db.addList(list2);
		db.addTask(task, 1);
		db.clearData();
		assertTrue(db.getAllLists().size() == 0 && db.getAllTasks().size() == 0);
	}

	public void testGetAllLists() {
		ContentValues cv = new ContentValues();
		cv.put(SQLConstants.LIST_NAME, "TESTNAME");
		realDb.insert(SQLConstants.LIST_TABLE_NAME, null, cv);
		assertTrue(db.getAllLists().size() == 1);
		
		assertTrue(_containsEqualList(db.getAllLists().keySet(), new TaskCollection(
				"TESTNAME")));
		cv.clear();
		cv.put(SQLConstants.LIST_NAME, "TESTNAME2");
		realDb.insert(SQLConstants.LIST_TABLE_NAME, null, cv);
		assertTrue(db.getAllLists().size() == 2);
		assertTrue(_containsEqualList(db.getAllLists().keySet(), new TaskCollection(
				"TESTNAME2")));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), new TaskCollection(
				"TESTNAME")));
	}

	public void testAddList() {
		TaskCollection list = new TaskCollection("Name1");
		TaskCollection list2 = new TaskCollection("Name2");

		assertTrue(db.addList(list) == 1);
		assertTrue(db.getAllLists().size() == 1);
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list));
		assertTrue(db.addList(list2) == 2);
		assertTrue(db.getAllLists().size() == 2);
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list2));
	}

	public void testAddLists() {
		TaskCollection list = new TaskCollection("Name1");
		TaskCollection list2 = new TaskCollection("Name2");
		TaskCollection list3 = new TaskCollection("Name3");
		TaskCollection list4 = new TaskCollection("Name4");
		db.addLists(new ITaskCollection[] { list, list2 });
		assertTrue(db.getAllLists().size() == 2);
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list2));
		db.addLists(new ITaskCollection[] { list3, list4 });
		assertTrue(db.getAllLists().size() == 4);
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list2));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list3));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list4));
	}

	public void testAddTask() {
		Task task = new Task("TASK", "DESC", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);
		Task task2 = new Task("TASK2", "DESC2", new Priority((byte) 6),
				new Date(2), new Date(23), 16, true);
		assertTrue(db.addTask(task, 0) == 1);
		assertTrue(db.getAllTasks().size() == 1);
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task));
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task2));
		assertTrue(db.addTask(task2, 0) == 2);
		assertTrue(db.getAllTasks().size() == 2);
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task2));
	}

	public void testAddTasks() {
		Task task = new Task("TASK", "DESC", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);
		Task task2 = new Task("TASK2", "DESC2", new Priority((byte) 6),
				new Date(2), new Date(23), 16, true);
		db.addTasks(new Task[] { task, task2 }, 0);
		assertTrue(db.getAllTasks().size() == 2);
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task2));
	}

	public void testEditList() {
		TaskCollection list = new TaskCollection("Name1");
		TaskCollection list2 = new TaskCollection("Name2");
		db.addList(list);

		assertTrue(db.editList(1, list2));
		assertTrue(db.getAllLists().size() == 1);
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list2));
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list));

		assertTrue(db.editList(1, list));
		assertTrue(db.getAllLists().size() == 1);
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list));
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list2));
	}

	public void testEditTask() {
		Task task = new Task("TASK", "DESC", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);
		Task task2 = new Task("TASK2", "DESC2", new Priority((byte) 6),
				new Date(2), new Date(23), 16, true);

		db.addTask(task, 0);

		assertTrue(db.editTask(1, task2));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task2));
		assertTrue(db.editTask(1, task));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task));
	}

	public void testGetAllTasks() {
		Task task = new Task("TASK", "DESC", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);
		Task task2 = new Task("TASK2", "DESC2", new Priority((byte) 6),
				new Date(2), new Date(23), 16, true);
		ContentValues ret = new ContentValues();
		ret.put(SQLConstants.TASK_DESCRIPTION, task.getDescription());
		ret.put(SQLConstants.TASK_DUEDATE, task.getDueDate().getTime());
		ret.put(SQLConstants.TASK_NAME, task.getName());
		ret.put(SQLConstants.TASK_PRIORITY, task.getPriority().getValue());
		ret.put(SQLConstants.TASK_REMINDERDATE, task.getReminderDate()
				.getTime());
		ret.put(SQLConstants.TASK_COMPLETED, task.isCompleted() ? 1 : 0);
		ret.put(SQLConstants.TASK_CUSTOMPOS, task.getCustomPosition());
		ret.put(SQLConstants.TASK_CONNECTED_LIST_ID, 0);

		realDb.insert(SQLConstants.TASK_TABLE_NAME, null, ret);
		assertTrue(db.getAllTasks().size() == 1);
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task));

		ret.clear();
		ret.put(SQLConstants.TASK_DESCRIPTION, task2.getDescription());
		ret.put(SQLConstants.TASK_DUEDATE, task2.getDueDate().getTime());
		ret.put(SQLConstants.TASK_NAME, task2.getName());
		ret.put(SQLConstants.TASK_PRIORITY, task2.getPriority().getValue());
		ret.put(SQLConstants.TASK_REMINDERDATE, task2.getReminderDate()
				.getTime());
		ret.put(SQLConstants.TASK_COMPLETED, task2.isCompleted() ? 1 : 0);
		ret.put(SQLConstants.TASK_CUSTOMPOS, task2.getCustomPosition());
		ret.put(SQLConstants.TASK_CONNECTED_LIST_ID, 0);

		realDb.insert(SQLConstants.TASK_TABLE_NAME, null, ret);
		assertTrue(db.getAllTasks().size() == 2);
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task2));

		realDb.insert(SQLConstants.TASK_TABLE_NAME, null, ret);
		assertTrue(db.getAllTasks().size() == 3);
	}

	public void testMoveTask() {
		Task task = new Task("TASK", "DESC", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);

		db.addTask(task, 1);

		assertTrue(db.moveTask(1, 2));
		assertTrue(db.moveTask(1, 1));
	}

	public void testRemoveList() {
		TaskCollection list = new TaskCollection("Name1");
		TaskCollection list2 = new TaskCollection("Name2");
		TaskCollection list3 = new TaskCollection("Name3");
		TaskCollection list4 = new TaskCollection("Name4");
		db.addLists(new ITaskCollection[] { list, list2, list3, list4 });
		db.removeList(1);
		assertTrue(db.getAllLists().size() == 3);
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list2));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list3));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list4));

		db.removeList(3);
		assertTrue(db.getAllLists().size() == 2);
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list2));
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list3));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list4));
	}

	public void testRemoveLists() {
		TaskCollection list = new TaskCollection("Name1");
		TaskCollection list2 = new TaskCollection("Name2");
		TaskCollection list3 = new TaskCollection("Name3");
		TaskCollection list4 = new TaskCollection("Name4");
		db.addLists(new ITaskCollection[] { list, list2, list3, list4 });
		db.removeLists(new int[] { 1, 3 });
		assertTrue(db.getAllLists().size() == 2);
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list2));
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list3));
		assertTrue(_containsEqualList(db.getAllLists().keySet(), list4));

		db.removeLists(new int[] { 2, 4 });
		assertTrue(db.getAllLists().size() == 0);
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list));
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list2));
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list3));
		assertFalse(_containsEqualList(db.getAllLists().keySet(), list4));
	}

	public void testRemoveTask() {
		Task task = new Task("TASK", "DESC", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);
		Task task2 = new Task("TASK2", "DESC2", new Priority((byte) 6),
				new Date(2), new Date(23), 16, true);
		Task task3 = new Task("TASK3", "DESC3", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);
		Task task4 = new Task("TASK4", "DESC4", new Priority((byte) 6),
				new Date(2), new Date(23), 16, true);

		db.addTasks(new Task[] { task, task2, task3, task4 }, 1);

		db.removeTask(1);
		assertTrue(db.getAllTasks().size() == 3);
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task2));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task3));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task4));

		db.removeTask(3);
		assertTrue(db.getAllTasks().size() == 2);
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task2));
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task3));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task4));
	}

	public void testRemoveTasks() {
		Task task = new Task("TASK", "DESC", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);
		Task task2 = new Task("TASK2", "DESC2", new Priority((byte) 6),
				new Date(2), new Date(23), 16, true);
		Task task3 = new Task("TASK3", "DESC3", new Priority((byte) 3),
				new Date(7), new Date(3), 5, false);
		Task task4 = new Task("TASK4", "DESC4", new Priority((byte) 6),
				new Date(2), new Date(23), 16, true);

		db.addTasks(new Task[] { task, task2, task3, task4 }, 1);

		db.removeTasks(new int[] { 1, 3 });
		assertTrue(db.getAllTasks().size() == 2);
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task2));
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task3));
		assertTrue(_containsEqualTask(db.getAllTasks().keySet(), task4));

		db.removeTasks(new int[] { 2, 4 });
		assertTrue(db.getAllTasks().size() == 0);
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task));
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task2));
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task3));
		assertFalse(_containsEqualTask(db.getAllTasks().keySet(), task4));
	}

	private boolean _containsEqualList(Collection<ITaskCollection> toCheck,
			ITaskCollection list) {
		for (ITaskCollection t : toCheck) {
			if (t.getName().equals(list.getName())) {
				return true;
			}
		}
		return false;
	}

	private boolean _containsEqualTask(Collection<ITask> toCheck, ITask task) {
		for (ITask t : toCheck) {
			if (t.getName().equals(task.getName())
					&& t.getDescription().equals(task.getDescription())
					&& t.getDueDate().equals(task.getDueDate())
					&& t.getCustomPosition() == task.getCustomPosition()
					&& t.getPriority().equals(task.getPriority())
					&& t.getReminderDate().equals(task.getReminderDate())) {
				return true;
			}
		}
		return false;
	}
}