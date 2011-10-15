package se.chalmers.doit.test.data.storage;

import java.util.ArrayList;
import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.implementation.DataCache;
import android.test.AndroidTestCase;

/**
 * Test class for testing DataCache.java
 * 
 * @author Kaufmann
 * 
 */
public class DataCacheTest extends AndroidTestCase {

	private DataCache cache;

	@Override
	public void setUp() throws Exception {
		cache = new DataCache();
	}

	public void testAddList() {
		assertTrue(cache.getAllLists().size() == 0);
		final TaskCollection t1 = new TaskCollection("TaskCol1",
				new ArrayList<ITask>());
		final TaskCollection t2 = new TaskCollection("TaskCol2",
				new ArrayList<ITask>());
		final TaskCollection t3 = new TaskCollection("TaskCol3",
				new ArrayList<ITask>());
		assertTrue(cache.addList(t1));
		assertTrue(cache.addList(t2));
		assertTrue(cache.addList(t3));
		assertTrue(cache.getAllLists().size() == 3);
		assertTrue(cache.getAllLists().contains(t1));
		assertTrue(cache.getAllLists().contains(t2));
		assertTrue(cache.getAllLists().contains(t3));
	}

	public void testAddLists() {
		assertTrue(cache.getAllLists().size() == 0);
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
		assertTrue(cache.addLists(colList) == 3);
		assertTrue(cache.addLists(colList) == 0);
		assertTrue(cache.getAllLists().size() == 3);
		assertTrue(cache.getAllLists().contains(t1));
		assertTrue(cache.getAllLists().contains(t2));
		assertTrue(cache.getAllLists().contains(t3));
	}

	public void testAddTask() {
		final ITask task = new Task("Name", "Description", false);
		final Collection<ITask> taskList = new ArrayList<ITask>();
		final ITaskCollection col = new TaskCollection("Collection", taskList);
		cache.addList(col);
		assertTrue(cache.getAllTasks().size() == 0);
		assertTrue(cache.addTask(task, col));
		assertFalse(cache.addTask(task, col));
		assertTrue(cache.getAllTasks().size() == 1);
		assertTrue(cache.getAllTasks().contains(task));
	}

	public void testAddTasks() {
		final ITask task = new Task("Name", "Description", false);
		final ITask task2 = new Task("Name2", "Description2", true);
		final Collection<ITask> taskList = new ArrayList<ITask>();
		taskList.add(task);
		taskList.add(task2);
		final ITaskCollection col = new TaskCollection("Collection",
				new ArrayList<ITask>());
		cache.addList(col);
		assertTrue(cache.getAllTasks().size() == 0);
		assertTrue(cache.addTasks(taskList, col) == 2);
		assertTrue(cache.addTasks(taskList, col) == 0);
		assertTrue(cache.getAllTasks().size() == 2);
		assertTrue(cache.getAllTasks().contains(task));
		assertTrue(cache.getAllTasks().contains(task2));
	}

	public void testClearData() {
		final ITask task = new Task("Name", "Description", false);
		final ITask task2 = new Task("Name2", "Description2", true);
		final Collection<ITask> taskList = new ArrayList<ITask>();
		taskList.add(task);
		taskList.add(task2);
		final ITaskCollection col = new TaskCollection("Collection",
				new ArrayList<ITask>());
		cache.addList(col);
		cache.addTasks(taskList, col);
		assertTrue(cache.getAllLists().size() != 0);
		assertTrue(cache.getAllTasks().size() != 0);
		cache.clearData();
		assertTrue(cache.getAllLists().size() == 0);
		assertTrue(cache.getAllTasks().size() == 0);
	}

	public void testEditList() {
		final TaskCollection tc1 = new TaskCollection("Name");
		final TaskCollection tc2 = new TaskCollection("Name2");
		cache.addList(tc1);
		assertTrue(cache.getAllLists().contains(tc1));
		cache.editList(tc1, tc2);
		assertTrue(cache.getAllLists().contains(tc2));
	}

	public void testEditTask() {
		final ITask task = new Task("Name", "Description", false);
		final ITask task2 = new Task("Name2", "Description2", true);
		final TaskCollection tc1 = new TaskCollection("Name");
		cache.addList(tc1);
		cache.addTask(task, tc1);
		assertTrue(cache.getAllTasks().contains(task));
		assertFalse(cache.getAllTasks().contains(task2));
		cache.editTask(task, task2);
		assertFalse(cache.getAllTasks().contains(task));
		assertTrue(cache.getAllTasks().contains(task2));
	}

	public void testGetAllLists() {
		assertTrue(cache.getAllLists().size() == 0);
		cache.addList(new TaskCollection("Name1"));
		cache.addList(new TaskCollection("Name2"));
		cache.addList(new TaskCollection("Name3"));
		assertTrue(cache.getAllLists().size() == 3);
	}

	public void testGetAllTasks() {
		assertTrue(cache.getAllTasks().size() == 0);
		final TaskCollection tc = new TaskCollection("TC");
		final TaskCollection tc2 = new TaskCollection("TC");
		final TaskCollection tc3 = new TaskCollection("TC");
		cache.addList(tc);
		cache.addList(tc2);
		cache.addList(tc3);
		cache.addTask(new Task("Task", "Krabba", false), tc);
		cache.addTask(new Task("Task2", "Krabba2", true), tc2);
		cache.addTask(new Task("Task3", "Krabba3", false), tc3);
		assertTrue(cache.getAllTasks().size() == 3);
	}

	public void testMoveTask() {
		final TaskCollection tc = new TaskCollection("TC");
		final TaskCollection tc2 = new TaskCollection("TC2");
		final Task t = new Task("Task", "Krabba", false);
		cache.addList(tc);
		cache.addTask(t, tc);
		cache.addList(tc2);
		cache.moveTask(t, tc2);
		for (final ITaskCollection c : cache.getAllLists()) {
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

		cache.addList(tc1);
		cache.addList(tc2);
		cache.addList(tc3);
		cache.addList(tc4);

		cache.removeList(tc2);

		assertTrue(cache.getAllLists().contains(tc1));
		assertFalse(cache.getAllLists().contains(tc2));
		assertTrue(cache.getAllLists().contains(tc3));
		assertTrue(cache.getAllLists().contains(tc4));
	}

	public void testRemoveLists() {
		final TaskCollection tc1 = new TaskCollection("tc1");
		final TaskCollection tc2 = new TaskCollection("tc2");
		final TaskCollection tc3 = new TaskCollection("tc3");
		final TaskCollection tc4 = new TaskCollection("tc4");

		cache.addList(tc1);
		cache.addList(tc2);
		cache.addList(tc3);
		cache.addList(tc4);

		final ArrayList<ITaskCollection> list = new ArrayList<ITaskCollection>();
		list.add(tc2);
		list.add(tc4);

		cache.removeLists(list);

		assertTrue(cache.getAllLists().contains(tc1));
		assertFalse(cache.getAllLists().contains(tc2));
		assertTrue(cache.getAllLists().contains(tc3));
		assertFalse(cache.getAllLists().contains(tc4));
	}

	public void testRemoveTask() {
		final Task t1 = new Task("t1", "blubb", false);
		final Task t2 = new Task("t2", "blubb", false);
		final Task t3 = new Task("t3", "blubb", false);
		final Task t4 = new Task("t4", "blubb", false);

		final TaskCollection col = new TaskCollection("Default");
		final ArrayList<ITask> taskList = new ArrayList<ITask>();
		taskList.add(t1);
		taskList.add(t2);
		taskList.add(t3);
		taskList.add(t4);

		cache.addList(col);
		cache.addTasks(taskList, col);

		assertTrue(cache.removeTask(t2));

		assertTrue(cache.getAllTasks().contains(t1));
		assertFalse(cache.getAllTasks().contains(t2));
		assertTrue(cache.getAllTasks().contains(t3));
		assertTrue(cache.getAllTasks().contains(t4));
	}

	public void testRemoveTasks() {
		final Task t1 = new Task("t1", "blubb", false);
		final Task t2 = new Task("t2", "blubb", false);
		final Task t3 = new Task("t3", "blubb", false);
		final Task t4 = new Task("t4", "blubb", false);

		final TaskCollection col = new TaskCollection("Default");
		final ArrayList<ITask> taskList = new ArrayList<ITask>();
		taskList.add(t1);
		taskList.add(t2);
		taskList.add(t3);
		taskList.add(t4);

		cache.addList(col);
		cache.addTasks(taskList, col);

		final ArrayList<ITask> list = new ArrayList<ITask>();
		list.add(t2);
		list.add(t4);
		assertTrue(cache.removeTasks(list) == 2);

		assertTrue(cache.getAllTasks().contains(t1));
		assertFalse(cache.getAllTasks().contains(t2));
		assertTrue(cache.getAllTasks().contains(t3));
		assertFalse(cache.getAllTasks().contains(t4));
	}
}