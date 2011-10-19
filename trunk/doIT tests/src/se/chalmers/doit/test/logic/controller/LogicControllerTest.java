package se.chalmers.doit.test.logic.controller;

import java.util.ArrayList;
import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.IDataStorage;
import se.chalmers.doit.data.storage.implementation.DataStorage;
import se.chalmers.doit.data.storage.implementation.StatisticsDataCache;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.database.sqlite.SQLiteDatabase;
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
	private IDataStorage data;

	@Override
	public void setUp() throws Exception {
		task1 = new Task("Task1", "", false);
		task2 = new Task("TooLongTaskName123456789000000000", "I'm too long!",
				false);
		tasks = new ArrayList<ITask>();
		controller = LogicController.getInstance();
		data = new DataStorage(SQLiteDatabase.create(null));
		controller.setStorageStrategy(data);
		controller.setStatisticsStrategy(new StatisticsDataCache());

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
		final int size = controller.getAllLists().size();
		assertFalse(controller.addList(list1));
		assertTrue(controller.getAllLists().size() == size);

	}

	public void testAddLists() {
		final Collection<ITaskCollection> lists = new ArrayList<ITaskCollection>();
		lists.add(list1);
		lists.add(emptyList);

		final int size = controller.getAllLists().size();

		assertTrue(controller.addLists(lists) == lists.size());
		assertTrue(controller.getAllLists().size() == size + 2);
		assertTrue(controller.addLists(lists) == 0);
		assertTrue(controller.getAllLists().size() == size + 2);

	}

	public void testAddTask() {
		assertTrue(controller.addList(emptyList));

		assertTrue(controller.addTask(task1, emptyList));
		for(int i = 0; i < 5; i++){
			assertTrue(controller.addTask(new Task("1","",false), _getFirstList()));
		}

		assertTrue(controller.addList(list1));
		assertFalse(controller.addTask(task2, list1));

	}

	private ITaskCollection _getFirstList(){
		for(ITaskCollection t : controller.getAllLists()){
			if(t != null){
				return t;
			}
		}
		return null;
	}

	private ITask _getFirstTask() {
		for (ITask t : controller.getAllTasks()) {
			if (t != null) {
				return t;
			}
		}
		return null;
	}

	public void testAddTasks() {
		controller.addList(emptyList);
		assertTrue(controller.addTasks(tasks, emptyList) == tasks.size());
		final int size = controller.getAllTasks().size();
		tasks.add(task2);
		assertTrue(controller.addTasks(tasks, emptyList) == 0);
		assertTrue(controller.getAllTasks().size() == size);
	}

	public void testClearData() {
		final ITask newTask = new Task("NewTask", "", false);
		assertTrue(controller.addList(list1));

		assertTrue(controller.getAllLists().size() == 1);
		assertTrue(controller.getAllTasks().size() == 1);

		assertTrue(controller.addTask(newTask, _getFirstList()));

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

		assertTrue(controller.editList(_getFirstList(), newList));
	}

	public void testEditTask() {
		final Collection<ITask> collection = new ArrayList<ITask>();
		final ITaskCollection list3 = new TaskCollection("List3", collection);
		assertTrue(controller.addList(list3));
		assertTrue(controller.addTask(task1, list3));

		assertFalse(controller.editTask(_getFirstTask(), task2));
		assertTrue(controller.editTask(_getFirstTask(), new Task("new", "", false)));
	}

	public void testMoveTask() {
		assertTrue(controller.addList(list1));
		assertTrue(controller.addList(emptyList));

		assertTrue(controller.moveTask(_getFirstTask(), emptyList));
	}

	public void testRemoveList() {
		assertTrue(controller.addList(list1));
		final int size = controller.getAllLists().size();
		assertTrue(controller.removeList(_getFirstList()));
		assertTrue(controller.getAllLists().size() == size - 1);
	}

	public void testRemoveLists() {
		assertTrue(controller.addList(list1));
		assertTrue(controller.addList(emptyList));

		assertTrue(controller.removeLists(controller.getAllLists()) == 2);
		assertTrue(controller.getAllLists().size() == 0);
	}

	public void testRemoveTask() {
		assertTrue(controller.addList(list1));
		final int size = controller.getAllTasks().size();

		assertTrue(controller.removeTask(_getFirstTask()));
		assertTrue(controller.getAllTasks().size() == size - 1);
	}

	public void testRemoveTasks() {
		final ITask temp = new Task("new task", "", false);
		assertTrue(controller.addList(list1));

		assertTrue(controller.addTask(temp, _getFirstList()));

		final int size = controller.getAllTasks().size();

		assertTrue(controller.removeTasks(controller.getAllTasks()) == 2);
		assertTrue(controller.getAllTasks().size() == size - 2);

	}

	public void testCompleteTask() {
		controller.addList(list1);
		assertTrue(controller.completeTask(_getFirstTask()));
	}

	public void testDecompleteTask() {
		controller.addList(list1);
		assertTrue(controller.decompleteTask(_getFirstTask()));
	}

	public void testToggleTaskCompleted() {
		controller.addList(list1);

		assertTrue(controller.toggleTaskCompleted(_getFirstTask()));

		for(ITask t : controller.getAllTasks()){
			assertTrue(t.isCompleted());
		}
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
