package se.chalmers.doit.test.logic.controller;

import java.util.ArrayList;
import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.test.AndroidTestCase;

/**
 * Test class for LogicController.
 *
 * @author Boel
 *
 */
public class LogicControllerTest extends AndroidTestCase {

	private ITask task1;
	private ITask task2;
	private Collection<ITask> tasks;
	private ITaskCollection list1;
	private ITaskCollection emptyList;
	private ILogicController controller;

	@Override
	public void setUp() throws Exception {
		task1 = new Task("Task1", "", false);
		task2 = new Task("TooLongTaskName123456789000000000", "I'm too long!",
				false);
		tasks = new ArrayList<ITask>();
		controller = LogicController.getInstance();

		emptyList = new TaskCollection("List2", tasks);

		tasks.add(task1);

		list1 = new TaskCollection("List1", tasks);

	}

	@Override
	public void tearDown() throws Exception {
		controller.clearData();
		controller.clearStatisticsData();
	}

	public void testGetAllLists() {
		assertTrue(controller.getAllLists().size() == 0);
	}

	public void testGetAllTasks() {
		assertTrue(controller.getAllTasks().size() == 0);

	}

	public void testAddList() {
		assertTrue(controller.addList(list1));
		int size = controller.getAllLists().size();
		assertFalse(controller.addList(list1));
		assertTrue(controller.getAllLists().size() == size);

	}

	public void testAddLists() {
		final Collection<ITaskCollection> lists = new ArrayList<ITaskCollection>();
		lists.add(list1);
		lists.add(emptyList);

		int size = controller.getAllLists().size();

		assertTrue(controller.addLists(lists) == lists.size());
		assertTrue(controller.getAllLists().size() == size + 2);
		assertTrue(controller.addLists(lists) == 0);
		assertTrue(controller.getAllLists().size() == size + 2);

	}

	public void testAddTask() {
		assertTrue(controller.addTask(task1, emptyList));
		int size = controller.getAllTasks().size();
		assertFalse(controller.addTask(task2, emptyList));
		assertTrue(controller.getAllTasks().size() == size);
	}

	public void testAddTasks() {
		assertTrue(controller.addTasks(tasks, emptyList) == tasks.size());
		int size = controller.getAllTasks().size();
		tasks.add(task2);
		assertTrue(controller.addTasks(tasks, emptyList) == 0);
		assertTrue(controller.getAllTasks().size() == size);
	}

	public void testClearData() {
		final ITask newTask = new Task("NewTask", "", false);
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
		final ITask newTask = new Task("New task", "", false);
		final Collection<ITask> newTasks = new ArrayList<ITask>();
		newTasks.add(newTask);

		final ITaskCollection newList = new TaskCollection("New list", newTasks);
		assertTrue(controller.addList(list1));

		assertTrue(controller.editList(list1, newList));
	}

	public void testEditTask() {
		final Collection<ITask> collection = new ArrayList<ITask>();

		final ITaskCollection list3 = new TaskCollection("List3", collection);
		assertTrue(controller.addTask(task1, list3));

		assertFalse(controller.editTask(task1, task2));
		assertTrue(controller.editTask(task1, new Task("new", "", false)));
	}

	public void testEditTasks() {
		tasks.add(new Task("new", "", false));
		assertTrue(controller.addTasks(tasks, emptyList) == tasks.size());

		final Collection<ITask> newTasks = new ArrayList<ITask>();
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
		assertTrue(controller.addList(list1));
		assertTrue(controller.addList(emptyList));

		assertTrue(controller.moveTask(task1, emptyList));
	}

	public void testRemoveList() {
		assertTrue(controller.addList(list1));
		int size = controller.getAllLists().size();
		assertTrue(controller.removeList(list1));
		assertTrue(controller.getAllLists().size() == size - 1);
	}

	public void testRemoveLists() {
		final Collection<ITaskCollection> lists = new ArrayList<ITaskCollection>();
		assertTrue(controller.addList(list1));
		assertTrue(controller.addList(emptyList));

		int size = controller.getAllLists().size();

		lists.add(emptyList);
		lists.add(list1);
		assertTrue(controller.removeLists(lists) == 2);
		assertTrue(controller.getAllLists().size() == size - 2);
	}

	public void testRemoveTask() {
		assertTrue(controller.addTask(task1, list1));
		int size = controller.getAllTasks().size();

		assertTrue(controller.removeTask(task1));
		assertTrue(controller.getAllTasks().size() == size - 1);
	}

	public void testRemoveTasks() {
		final ITask temp = new Task("new task", "", false);
		tasks.add(temp);

		assertTrue(controller.addTask(task1, list1));
		assertTrue(controller.addTask(temp, list1));

		int size = controller.getAllTasks().size();

		assertTrue(controller.removeTasks(tasks) == 2);
		assertTrue(controller.getAllTasks().size() == size - 2);

	}

	public void testCompleteTask() {
		controller.addList(list1);
		assertTrue(controller.completeTask(task1));
	}

	public void testDecompleteTask() {
		controller.addList(list1);
		assertTrue(controller.decompleteTask(task1));
	}

	public void testToggleTaskCompleted() {
		controller.addList(list1);

		assertTrue(controller.toggleTaskCompleted(task1));

		final Task task3 = new Task(task1, true);

		controller.addTask(task3, list1);

		assertTrue(controller.toggleTaskCompleted(task3));
	}

	public void testGetNumberOfCreatedTasks() {
		assertTrue(controller.getNumberOfCreatedTasks(-1) == 0);
	}

	public void testGetNumberOfFinishedTasks() {
		assertTrue(controller.getNumberOfFinishedTasks(-1) == 0);
	}

	public void testGetNumberOfOverdueTasks() {
		assertTrue(controller.getNumberOfOverdueTasks(-1) == 0);
	}

	public void testGetNumberOfDeletedTasks() {
		assertTrue(controller.getNumberOfDeletedTasks(-1) == 0);
	}

	public void testGetNumberOfCreatedLists() {
		assertTrue(controller.getNumberOfCreatedLists(-1) == 0);
	}

	public void testGetNumberOfDeletedLists() {
		assertTrue(controller.getNumberOfDeletedLists(-1) == 0);
	}

	public void testIncrementNumberOfCreatedTasks() {
		controller.incrementNumberOfCreatedTasks(1);
		assertTrue(controller.getNumberOfCreatedTasks(-1) == 1);
	}

	public void testIncrementNumberOfFinishedTasks() {
		controller.incrementNumberOfFinishedTasks(1);
		assertTrue(controller.getNumberOfFinishedTasks(-1) == 1);
	}

	public void testIncrementNumberOfOverdueTasks() {
		controller.incrementNumberOfOverdueTasks(1);
		assertTrue(controller.getNumberOfOverdueTasks(-1) == 1);
	}

	public void testIncrementNumberOfDeletedTasks() {
		controller.incrementNumberOfDeletedTasks(1);
		assertTrue(controller.getNumberOfDeletedTasks(-1) == 1);
	}

	public void testIncrementNumberOfCreatedLists() {
		controller.incrementNumberOfCreatedLists(1);
		assertTrue(controller.getNumberOfCreatedLists(-1) == 1);
	}

	public void testIncrementNumberOfDeletedLists() {
		controller.incrementNumberOfDeletedLists(1);
		assertTrue(controller.getNumberOfDeletedLists(-1) == 1);
	}

	public void testClearStatisticsData() {
		controller.incrementNumberOfDeletedLists(1);

		controller.clearStatisticsData();

		assertTrue(controller.getNumberOfDeletedLists(-1) == 0);

	}
}
