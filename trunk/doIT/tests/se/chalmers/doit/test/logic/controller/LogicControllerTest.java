package se.chalmers.doit.test.logic.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.test.AndroidTestCase;

public class LogicControllerTest extends AndroidTestCase {

	private ITask task1;
	private ITask task2;
	private Collection<ITask> tasks;
	private ITaskCollection list1;
	private ITaskCollection emptyList;
	private ILogicController controller;

	@Override
	@Before
	public void setUp() throws Exception {
		task1 = new Task("Task1", "", false);
		task2 = new Task("TooLongTaskName123456789000000000", "I'm too long!",
				false);
		tasks = new ArrayList<ITask>();
		controller = new LogicController();

		emptyList = new TaskCollection("List2", tasks);

		tasks.add(task1);

		list1 = new TaskCollection("List1", tasks);

	}

	public void testGetAllLists() {
		assertTrue(controller.getAllLists().size() == 0);
	}

	public void testGetAllTasks() {
		assertTrue(controller.getAllTasks().size() == 0);

	}

	public void testAddList() {
		assertTrue(controller.addList(list1));
		assertFalse(controller.addList(list1));

	}

	public void testAddLists() {
		Collection<ITaskCollection> lists = new ArrayList<ITaskCollection>();
		lists.add(list1);
		lists.add(emptyList);

		assertTrue(controller.addLists(lists));
		assertFalse(controller.addLists(lists));

	}

	public void testAddTask() {
		assertTrue(controller.addTask(task1, emptyList));
		assertFalse(controller.addTask(task2, emptyList));
	}

	public void testAddTasks() {
		assertTrue(controller.addTasks(tasks, emptyList));
		tasks.add(task2);
		assertFalse(controller.addTasks(tasks, emptyList));
	}

	public void testClearData() {
		ITask newTask = new Task("NewTask", "", false);
		assertTrue(controller.addList(list1));

		assertTrue(controller.getAllLists().size() == 1);
		assertTrue(controller.getAllTasks().size() == 1);

		assertTrue(controller.addTask(newTask, list1));

		assertTrue(controller.getAllLists().size() == 1);
		assertTrue(controller.getAllTasks().size() == 2);

		controller.clearData();

		assertTrue(controller.getAllLists().size() == 0);
		assertTrue(controller.getAllTasks().size() == 0);
	}

	public void testEditList() {
		ITask newTask = new Task("New task", "", false);
		Collection<ITask> newTasks = new ArrayList<ITask>();
		newTasks.add(newTask);

		ITaskCollection newList = new TaskCollection("New list", newTasks);
		assertTrue(controller.addList(list1));

		assertTrue(controller.editList(list1, newList));
	}

	public void testEditTask() {
		Collection<ITask> collection = new ArrayList<ITask>();

		ITaskCollection list3 = new TaskCollection("List3", collection);
		assertTrue(controller.addTask(task1, list3));

		assertFalse(controller.editTask(task1, task2));
		assertTrue(controller.editTask(task1, new Task("new", "", false)));
	}

	public void testEditTasks() {
		tasks.add(new Task("new", "", false));
		assertTrue(controller.addTasks(tasks, emptyList));

		Collection<ITask> newTasks = new ArrayList<ITask>();
		newTasks.add(new Task("One", "", false));
		newTasks.add(new Task("Two", "", false));

		ITaskCollection newCollection = new TaskCollection("New collection",
				newTasks);

		assertTrue(controller.editTasks(emptyList, newCollection));

		newTasks.add(task2);
		newCollection = new TaskCollection("New collection", newTasks);
		assertFalse(controller.editTasks(emptyList, newCollection));
	}

	public void testMoveTask() {
		fail("Not yet implemented");
	}

	public void testRemoveList() {
		fail("Not yet implemented");
	}

	public void testRemoveLists() {
		fail("Not yet implemented");
	}

	public void testRemoveTask() {
		fail("Not yet implemented");
	}

	public void testRemoveTasks() {
		fail("Not yet implemented");
	}

}
