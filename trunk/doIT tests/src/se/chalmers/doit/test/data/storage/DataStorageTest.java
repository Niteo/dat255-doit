package se.chalmers.doit.test.data.storage;

import java.util.ArrayList;
import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.IDataStorage;
import se.chalmers.doit.data.storage.implementation.DataStorage;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class DataStorageTest extends AndroidTestCase {

	private IDataStorage storage;

	@Override
	public void setUp() throws Exception {
		storage = new DataStorage(SQLiteDatabase.create(null));
	}

	public void testAddList() {
		assertTrue(storage.getAllLists().size() == 0);
		final TaskCollection t1 = new TaskCollection("TaskCol1",
				new ArrayList<ITask>());
		final TaskCollection t2 = new TaskCollection("TaskCol2",
				new ArrayList<ITask>());
		final TaskCollection t3 = new TaskCollection("TaskCol3",
				new ArrayList<ITask>());
		assertTrue(storage.addList(t1));
		assertTrue(storage.addList(t2));
		assertTrue(storage.addList(t3));
		assertTrue(storage.getAllLists().size() == 3);
		assertTrue(storage.getAllLists().contains(t1));
		assertTrue(storage.getAllLists().contains(t2));
		assertTrue(storage.getAllLists().contains(t3));
	}

	public void testAddLists() {
		assertTrue(storage.getAllLists().size() == 0);
		final TaskCollection t1 = new TaskCollection("TaskCol1",
				new ArrayList<ITask>());
		final TaskCollection t2 = new TaskCollection("TaskCol2",
				new ArrayList<ITask>());
		final TaskCollection t3 = new TaskCollection("TaskCol3",
				new ArrayList<ITask>());
		final ArrayList<ITaskCollection> colList = new ArrayList<ITaskCollection>();
		assertTrue(colList.add(t1));
		assertTrue(colList.add(t2));
		assertTrue(colList.add(t3));
		assertTrue(storage.addLists(colList) == 3);
		assertTrue(storage.addLists(colList) == 0);
		assertTrue(storage.getAllLists().size() == 3);
		assertTrue(storage.getAllLists().contains(t1));
		assertTrue(storage.getAllLists().contains(t2));
		assertTrue(storage.getAllLists().contains(t3));
	}

	public void testAddTask() {
		final ITask task = new Task("Name", "Description", false);
		final ITask task2 = new Task("Name2", "Description2", true);
		final Collection<ITask> taskList = new ArrayList<ITask>();
		taskList.add(task);
		final ITaskCollection col = new TaskCollection("Collection", taskList);

		storage.addList(col);

		assertTrue(storage.getAllTasks().size() == 1);
		assertFalse(storage.addTask(task, col));
		assertTrue(storage.getAllTasks().size() == 1);
		assertTrue(storage.addTask(task2, col));
		assertTrue(storage.getAllTasks().size() == 2);
		assertTrue(storage.getAllTasks().contains(task));
		assertTrue(storage.getAllTasks().contains(task2));
	}

	public void testAddTasks() {
		final ITask task = new Task("Name", "Description", false);
		final ITask task2 = new Task("Name2", "Description2", true);
		final Collection<ITask> taskList = new ArrayList<ITask>();
		taskList.add(task);
		taskList.add(task2);
		final ITaskCollection col = new TaskCollection("Collection",
				new ArrayList<ITask>());
		assertTrue(storage.getAllTasks().size() == 0);
		assertTrue(storage.addTasks(taskList, col) == 2);
		assertTrue(storage.addTasks(taskList, col) == 0);
		assertTrue(storage.getAllTasks().size() == 2);
		assertTrue(storage.getAllTasks().contains(task));
		assertTrue(storage.getAllTasks().contains(task2));
	}

	public void testClearData() {
		final ITask task = new Task("Name", "Description", false);
		final ITask task2 = new Task("Name2", "Description2", true);
		final Collection<ITask> taskList = new ArrayList<ITask>();
		taskList.add(task);
		taskList.add(task2);
		final ITaskCollection col = new TaskCollection("Collection",
				new ArrayList<ITask>());
		storage.addTasks(taskList, col);
		assertTrue(storage.getAllLists().size() != 0);
		assertTrue(storage.getAllTasks().size() != 0);
		storage.clearData();
		assertTrue(storage.getAllLists().size() == 0);
		assertTrue(storage.getAllTasks().size() == 0);
	}

	public void testEditList() {
		final TaskCollection tc1 = new TaskCollection("Name");
		final TaskCollection tc2 = new TaskCollection("Name2");
		storage.addList(tc1);
		assertTrue(storage.getAllLists().contains(tc1));
		storage.editList(tc1, tc2);
		assertTrue(storage.getAllLists().contains(tc2));
	}

	public void testEditTask() {
		final ITask task = new Task("Name", "Description", false);
		final ITask task2 = new Task("Name2", "Description2", true);
		final TaskCollection tc1 = new TaskCollection("Name");

		storage.addList(tc1);
		storage.addTask(task, tc1);
		assertTrue(storage.getAllTasks().contains(task));
		assertFalse(storage.getAllTasks().contains(task2));
		storage.editTask(task, task2);
		assertFalse(storage.getAllTasks().contains(task));
		assertTrue(storage.getAllTasks().contains(task2));
	}

	public void testGetAllLists() {
		assertTrue(storage.getAllLists().size() == 0);
		storage.addList(new TaskCollection("Name1"));
		storage.addList(new TaskCollection("Name2"));
		storage.addList(new TaskCollection("Name3"));
		assertTrue(storage.getAllLists().size() == 3);
	}

	public void testGetAllTasks() {
		assertTrue(storage.getAllTasks().size() == 0);
		final TaskCollection tc = new TaskCollection("TC");
		storage.addList(tc);
		final Collection<ITask> tasks = new ArrayList<ITask>();

		tasks.add(new Task("Task", "Krabba", false));
		tasks.add(new Task("Task2", "Krabba2", true));
		tasks.add(new Task("Task3", "Krabba3", false));

		storage.addTasks(tasks, tc);
		assertTrue(storage.getAllTasks().size() == 3);
	}

	public void testMoveTask() {
		final TaskCollection tc = new TaskCollection("TC");
		final TaskCollection tc2 = new TaskCollection("TC2");
		final Task t = new Task("Task", "Krabba", false);
		storage.addList(tc);
		storage.addTask(t, tc);
		storage.addList(tc2);
		storage.moveTask(t, tc2);
		for (final ITaskCollection c : storage.getAllLists()) {
			if (c.getName().equals("TC")) {
				assertFalse(c.getTasks().contains(t));
			}
			if (c.getName().equals("TC2")) {
				assertTrue(c.getTasks().contains(t));
			}
		}
	}

	public void testRemoveList() {
		final TaskCollection tc1 = new TaskCollection("tc1");
		final TaskCollection tc2 = new TaskCollection("tc2");
		final TaskCollection tc3 = new TaskCollection("tc3");
		final TaskCollection tc4 = new TaskCollection("tc4");

		storage.addList(tc1);
		storage.addList(tc2);
		storage.addList(tc3);
		storage.addList(tc4);

		storage.removeList(tc2);

		assertTrue(storage.getAllLists().contains(tc1));
		assertFalse(storage.getAllLists().contains(tc2));
		assertTrue(storage.getAllLists().contains(tc3));
		assertTrue(storage.getAllLists().contains(tc4));
	}

	public void testRemoveLists() {
		final TaskCollection tc1 = new TaskCollection("tc1");
		final TaskCollection tc2 = new TaskCollection("tc2");
		final TaskCollection tc3 = new TaskCollection("tc3");
		final TaskCollection tc4 = new TaskCollection("tc4");

		storage.addList(tc1);
		storage.addList(tc2);
		storage.addList(tc3);
		storage.addList(tc4);

		final ArrayList<ITaskCollection> list = new ArrayList<ITaskCollection>();
		list.add(tc2);
		list.add(tc4);

		storage.removeLists(list);

		assertTrue(storage.getAllLists().contains(tc1));
		assertFalse(storage.getAllLists().contains(tc2));
		assertTrue(storage.getAllLists().contains(tc3));
		assertFalse(storage.getAllLists().contains(tc4));
	}

	public void testRemoveTask() {
		final Task t1 = new Task("t1", "blubb", false);
		final Task t2 = new Task("t2", "blubb", false);
		final Task t3 = new Task("t3", "blubb", false);
		final Task t4 = new Task("t4", "blubb", false);

		final TaskCollection col = new TaskCollection("Default");

		storage.addList(col);

		final Collection<ITask> tasks = new ArrayList<ITask>();
		tasks.add(t1);
		tasks.add(t2);
		tasks.add(t3);
		tasks.add(t4);

		storage.addTasks(tasks, col);

		storage.removeTask(t2);

		assertTrue(storage.getAllTasks().contains(t1));
		assertFalse(storage.getAllTasks().contains(t2));
		assertTrue(storage.getAllTasks().contains(t3));
		assertTrue(storage.getAllTasks().contains(t4));
	}

	public void testRemoveTasks() {
		final Task t1 = new Task("t1", "blubb", false);
		final Task t2 = new Task("t2", "blubb", false);
		final Task t3 = new Task("t3", "blubb", false);
		final Task t4 = new Task("t4", "blubb", false);

		final TaskCollection col = new TaskCollection("Default");

		storage.addList(col);

		final Collection<ITask> tasks = new ArrayList<ITask>();
		tasks.add(t1);
		tasks.add(t2);
		tasks.add(t3);
		tasks.add(t4);

		storage.addTasks(tasks, col);

		final Collection<ITask> list = new ArrayList<ITask>();
		list.add(t2);
		list.add(t4);
		storage.removeTasks(list);

		assertTrue(storage.getAllTasks().contains(t1));
		assertFalse(storage.getAllTasks().contains(t2));
		assertTrue(storage.getAllTasks().contains(t3));
		assertFalse(storage.getAllTasks().contains(t4));
	}
}
