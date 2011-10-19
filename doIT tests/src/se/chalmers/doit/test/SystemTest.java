package se.chalmers.doit.test;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.implementation.DataStorage;
import se.chalmers.doit.data.storage.implementation.StatisticsDataCache;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class SystemTest extends AndroidTestCase {

	ILogicController lc;

	int bigNumber;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		lc = LogicController.getInstance();
		lc.setStorageStrategy(new DataStorage(SQLiteDatabase.create(null)));
		lc.setStatisticsStrategy(new StatisticsDataCache());

		// bigNumber specifies how many times the "many"-tests will run
		bigNumber = 13;

	}

	public void testSystem() {

		int createdTasks = 0;
		int deletedTasks = 0;
		int finishedTasks = 0;
		int createdLists = 0;
		int deletedLists = 0;

		_testToggleCompletionManyTasks();

		createdTasks += bigNumber;
		deletedTasks += bigNumber;
		finishedTasks += bigNumber;
		createdLists += 1;
		deletedLists += 1;

		assertTrue(lc.getNumberOfCreatedTasks(-1) == createdTasks);
		assertTrue(lc.getNumberOfDeletedTasks(-1) == deletedTasks);
		assertTrue(lc.getNumberOfFinishedTasks(-1) == finishedTasks);
		assertTrue(lc.getNumberOfCreatedLists(-1) == createdLists);
		assertTrue(lc.getNumberOfDeletedLists(-1) == deletedLists);

		_testAddIncorrectLists();

		createdTasks += 0;
		deletedTasks += 0;
		finishedTasks += 0;
		createdLists += 3;
		deletedLists += 3;

		assertTrue(lc.getNumberOfCreatedTasks(-1) == createdTasks);
		assertTrue(lc.getNumberOfDeletedTasks(-1) == deletedTasks);
		assertTrue(lc.getNumberOfFinishedTasks(-1) == finishedTasks);
		assertTrue(lc.getNumberOfCreatedLists(-1) == createdLists);
		assertTrue(lc.getNumberOfDeletedLists(-1) == deletedLists);

		_testMoveManyTasks();

		createdTasks += bigNumber;
		deletedTasks += bigNumber;
		finishedTasks += 0;
		createdLists += 2;
		deletedLists += 2;

		assertTrue(lc.getNumberOfCreatedTasks(-1) == createdTasks);
		assertTrue(lc.getNumberOfDeletedTasks(-1) == deletedTasks);
		assertTrue(lc.getNumberOfFinishedTasks(-1) == finishedTasks);
		assertTrue(lc.getNumberOfCreatedLists(-1) == createdLists);
		assertTrue(lc.getNumberOfDeletedLists(-1) == deletedLists);

		_testEditManyTasks();

		createdTasks += bigNumber;
		deletedTasks += bigNumber;
		finishedTasks += 0;
		createdLists += 1;
		deletedLists += 1;

		assertTrue(lc.getNumberOfCreatedTasks(-1) == createdTasks);
		assertTrue(lc.getNumberOfDeletedTasks(-1) == deletedTasks);
		assertTrue(lc.getNumberOfFinishedTasks(-1) == finishedTasks);
		assertTrue(lc.getNumberOfCreatedLists(-1) == createdLists);
		assertTrue(lc.getNumberOfDeletedLists(-1) == deletedLists);

		_testEditManyLists();

		createdTasks += 0;
		deletedTasks += 0;
		finishedTasks += 0;
		createdLists += bigNumber;
		deletedLists += bigNumber;

		assertTrue(lc.getNumberOfCreatedTasks(-1) == createdTasks);
		assertTrue(lc.getNumberOfDeletedTasks(-1) == deletedTasks);
		assertTrue(lc.getNumberOfFinishedTasks(-1) == finishedTasks);
		assertTrue(lc.getNumberOfCreatedLists(-1) == createdLists);
		assertTrue(lc.getNumberOfDeletedLists(-1) == deletedLists);

		_testAddAndDeleteManyTasks();

		createdTasks += 2 * bigNumber;
		deletedTasks += 2 * bigNumber;
		finishedTasks += 2 * bigNumber;
		createdLists += 2;
		deletedLists += 2;

		assertTrue(lc.getNumberOfCreatedTasks(-1) == createdTasks);
		assertTrue(lc.getNumberOfDeletedTasks(-1) == deletedTasks);
		assertTrue(lc.getNumberOfFinishedTasks(-1) == finishedTasks);
		assertTrue(lc.getNumberOfCreatedLists(-1) == createdLists);
		assertTrue(lc.getNumberOfDeletedLists(-1) == deletedLists);

		_testAddAndDeleteManyLists();

		createdTasks += 0;
		deletedTasks += 0;
		finishedTasks += 0;
		createdLists += bigNumber;
		deletedLists += bigNumber;

		assertTrue(lc.getNumberOfCreatedTasks(-1) == createdTasks);
		assertTrue(lc.getNumberOfDeletedTasks(-1) == deletedTasks);
		assertTrue(lc.getNumberOfFinishedTasks(-1) == finishedTasks);
		assertTrue(lc.getNumberOfCreatedLists(-1) == createdLists);
		assertTrue(lc.getNumberOfDeletedLists(-1) == deletedLists);

		_testBasicAddAndDeleteListAndTasks();

		createdTasks += 3;
		deletedTasks += 3;
		finishedTasks += 0;
		createdLists += 2;
		deletedLists += 2;

		assertTrue(lc.getNumberOfCreatedTasks(-1) == createdTasks);
		assertTrue(lc.getNumberOfDeletedTasks(-1) == deletedTasks);
		assertTrue(lc.getNumberOfFinishedTasks(-1) == finishedTasks);
		assertTrue(lc.getNumberOfCreatedLists(-1) == createdLists);
		assertTrue(lc.getNumberOfDeletedLists(-1) == deletedLists);

	}

	private void _testToggleCompletionManyTasks() {
		final ITaskCollection list = new TaskCollection("List");

		lc.addList(list);

		final ITask[] taskArray = new ITask[bigNumber];

		int i = 0;

		for (i = 0; i < bigNumber; i++) {
			taskArray[i] = new Task("Task " + (i + 1), "", false);
			lc.addTask(taskArray[i],
					_getNewListReference(list, lc.getAllLists()));
			assertTrue(lc.getAllTasks().size() == (i + 1));

			lc.toggleTaskCompleted(_getNewTaskReference(taskArray[i],
					lc.getAllTasks()));
			assertTrue(_getNewTaskReference(taskArray[i], lc.getAllTasks())
					.isCompleted());
			assertTrue(lc.getAllTasks().size() == i + 1);

			lc.toggleTaskCompleted(_getNewTaskReference(taskArray[i],
					lc.getAllTasks()));
			assertFalse(_getNewTaskReference(taskArray[i], lc.getAllTasks())
					.isCompleted());
			assertTrue(lc.getAllTasks().size() == i + 1);
		}

		lc.removeList(_getNewListReference(list, lc.getAllLists()));

		assertTrue(lc.getAllLists().size() == 0);
		assertTrue(lc.getAllTasks().size() == 0);
	}

	private void _testAddIncorrectLists() {

		String goodString1;
		String goodString2;
		String goodString3;

		final StringBuilder s1 = new StringBuilder();
		final StringBuilder s2 = new StringBuilder();
		final StringBuilder s3 = new StringBuilder();

		int i = 0;

		for (i = 0; i < 31; i++) {
			s1.append("a");
			s2.append("a");
			s3.append("a");
		}

		s1.append("b");
		s2.append("c");
		s3.append("asdfghj");

		goodString1 = s1.toString();
		goodString2 = s2.toString();
		goodString3 = s3.toString();

		final ITaskCollection list1 = new TaskCollection(goodString1);
		final ITaskCollection list1dupe = new TaskCollection(goodString1);
		final ITaskCollection list2 = new TaskCollection(goodString2);
		final ITaskCollection list3 = new TaskCollection(goodString3);

		lc.addList(list1);
		assertTrue(lc.getAllLists().size() == 1);
		assertTrue(_isListInCollection(lc.getAllLists(),
				_getNewListReference(list1, lc.getAllLists())));

		lc.addList(list1);
		assertTrue(lc.getAllLists().size() == 1);

		lc.addList(list1dupe);
		assertTrue(lc.getAllLists().size() == 1);

		lc.addList(new TaskCollection(goodString1));
		assertTrue(lc.getAllLists().size() == 1);

		lc.addList(list2);
		assertTrue(lc.getAllLists().size() == 2);
		assertTrue(_isListInCollection(lc.getAllLists(),
				_getNewListReference(list2, lc.getAllLists())));

		lc.addList(list2);
		assertTrue(lc.getAllLists().size() == 2);

		lc.addList(list3);
		assertTrue(lc.getAllLists().size() == 3);
		assertTrue(_isListInCollection(lc.getAllLists(),
				_getNewListReference(list3, lc.getAllLists())));

		lc.removeList(_getNewListReference(list1, lc.getAllLists()));
		lc.removeList(_getNewListReference(list2, lc.getAllLists()));
		lc.removeList(_getNewListReference(list3, lc.getAllLists()));

		assertTrue(lc.getAllLists().size() == 0);
		assertTrue(lc.getAllTasks().size() == 0);
	}

	private void _testMoveManyTasks() {
		final ITaskCollection list1 = new TaskCollection("från");
		final ITaskCollection list2 = new TaskCollection("till");

		lc.addList(list1);
		lc.addList(list2);

		final ITask[] taskArray = new ITask[bigNumber];

		int i = 0;

		for (i = 0; i < bigNumber; i++) {
			taskArray[i] = new Task("Task " + (i + 1), "", false);
			lc.addTask(taskArray[i],
					_getNewListReference(list1, lc.getAllLists()));

			assertTrue(lc.getAllTasks().size() == (i + 1));
		}

		assertTrue(lc.getAllTasks().size() == bigNumber);

		for (i = 0; i < bigNumber; i++) {
			lc.moveTask(_getNewTaskReference(taskArray[i], lc.getAllTasks()),
					_getNewListReference(list2, lc.getAllLists()));

			assertTrue(lc.getAllTasks().size() == bigNumber);
			assertTrue(_getNewListReference(list1, lc.getAllLists()).getTasks()
					.size() == bigNumber - (i + 1));
			assertTrue(_getNewListReference(list2, lc.getAllLists()).getTasks()
					.size() == (i + 1));
		}

		assertTrue(lc.getAllTasks().size() == bigNumber);

		lc.removeList(_getNewListReference(list1, lc.getAllLists()));
		lc.removeList(_getNewListReference(list2, lc.getAllLists()));

		assertTrue(lc.getAllLists().size() == 0);
		assertTrue(lc.getAllTasks().size() == 0);
	}

	private void _testEditManyTasks() {

		final ITaskCollection list = new TaskCollection("list");

		lc.addList(list);

		final ITask[] oldTasks = new ITask[bigNumber];
		final ITask[] newTasks = new ITask[bigNumber];

		int i = 0;

		for (i = 0; i < bigNumber; i++) {
			oldTasks[i] = new Task("Task " + (i + 1), "a", false);
			newTasks[i] = new Task("Task2 " + (i + 1), "b", false);

			assertTrue(lc.addTask(oldTasks[i],
					_getNewListReference(list, lc.getAllLists())));
			assertTrue(lc.getAllTasks().size() == (i + 1));
		}

		for (i = 0; i < bigNumber; i++) {
			lc.editTask(_getNewTaskReference(oldTasks[i], lc.getAllTasks()),
					newTasks[i]);

			assertTrue(lc.getAllTasks().size() == bigNumber);
		}

		lc.removeList(_getNewListReference(list, lc.getAllLists()));

		assertTrue(lc.getAllLists().size() == 0);
		assertTrue(lc.getAllTasks().size() == 0);

	}

	private void _testEditManyLists() {
		final ITaskCollection[] oldLists = new ITaskCollection[bigNumber];
		final ITaskCollection[] newLists = new ITaskCollection[bigNumber];

		int i = 0;

		for (i = 0; i < bigNumber; i++) {
			oldLists[i] = new TaskCollection("oldList " + (i + 1));
			lc.addList(oldLists[i]);

			newLists[i] = new TaskCollection("newList " + (i + 1));
		}

		assertTrue(lc.getAllLists().size() == bigNumber);

		for (i = 0; i < bigNumber; i++) {
			lc.editList(_getNewListReference(oldLists[i], lc.getAllLists()),
					newLists[i]);

			assertTrue(lc.getAllLists().size() == bigNumber);
		}

		final Collection<ITaskCollection> tempLists = lc.getAllLists();
		for (i = 0; i < bigNumber; i++) {
			assertTrue(_isListInCollection(tempLists, newLists[i]));
			assertFalse(_isListInCollection(tempLists, oldLists[i]));
		}

		for (i = 0; i < bigNumber; i++) {
			lc.removeList(_getNewListReference(newLists[i], lc.getAllLists()));
		}

		assertTrue(lc.getAllLists().size() == 0);
		assertTrue(lc.getAllTasks().size() == 0);
	}

	private void _testAddAndDeleteManyTasks() {

		final ITaskCollection list1 = new TaskCollection("bigList");
		final ITaskCollection list2 = new TaskCollection("bigList2");

		lc.addList(list1);
		lc.addList(list2);

		final ITask[] taskArray1 = new ITask[bigNumber];
		final ITask[] taskArray2 = new ITask[bigNumber];

		int i = 0;

		for (i = 0; i < bigNumber; i++) {
			taskArray1[i] = new Task("Task " + (i + 1), "", false);
			taskArray2[i] = new Task("Task " + (i + 1), "", false);

			lc.addTask(taskArray1[i],
					_getNewListReference(list1, lc.getAllLists()));
			lc.addTask(taskArray2[i],
					_getNewListReference(list2, lc.getAllLists()));

			assertTrue(lc.getAllTasks().size() == (i + 1) * 2);
		}

		assertTrue(lc.getAllTasks().size() == 2 * bigNumber);

		for (i = 0; i < bigNumber; i++) {
			lc.completeTask(_getNewTaskReference(taskArray1[i],
					lc.getAllTasks()));
			lc.completeTask(_getNewTaskReference(taskArray2[i],
					lc.getAllTasks()));

			lc.decompleteTask(_getNewTaskReference(taskArray1[i],
					lc.getAllTasks()));
		}

		for (i = 0; i < bigNumber; i++) {
			lc.removeTask(_getNewTaskReference(taskArray1[i], lc.getAllTasks()));

			assertTrue(lc.getAllTasks().size() == ((2 * bigNumber) - (i + 1)));
		}

		assertTrue(lc.getAllTasks().size() == bigNumber);

		for (i = 0; i < bigNumber; i++) {
			lc.removeTask(_getNewTaskReference(taskArray2[i], lc.getAllTasks()));

			assertTrue(lc.getAllTasks().size() == bigNumber - (i + 1));
		}

		lc.removeList(_getNewListReference(list1, lc.getAllLists()));
		lc.removeList(_getNewListReference(list2, lc.getAllLists()));

		assertTrue(lc.getAllLists().size() == 0);
		assertTrue(lc.getAllTasks().size() == 0);

	}

	private void _testAddAndDeleteManyLists() {

		final ITaskCollection[] listArray = new ITaskCollection[bigNumber];

		int i = 0;

		for (i = 0; i < bigNumber; i++) {
			listArray[i] = new TaskCollection("List number " + (i + 1));
			lc.addList(listArray[i]);
			assertTrue(lc.getAllLists().size() == (i + 1));
		}

		for (i = 0; i < bigNumber; i++) {
			lc.removeList(_getNewListReference(listArray[i], lc.getAllLists()));
			assertTrue(lc.getAllLists().size() == (bigNumber - (i + 1)));
		}

		assertTrue(lc.getAllLists().size() == 0);
		assertTrue(lc.getAllTasks().size() == 0);

	}

	private void _testBasicAddAndDeleteListAndTasks() {

		Collection<ITaskCollection> tempLists;
		Collection<ITask> tempTasks;

		// Create lists and add them to the data
		final ITaskCollection firstList = new TaskCollection("firstList");
		final ITaskCollection secondList = new TaskCollection("secondList");

		lc.addList(firstList);
		lc.addList(secondList);

		// Assert that the lists have been added
		tempLists = lc.getAllLists();
		assertTrue(tempLists.size() == 2);
		assertTrue(_isListInCollection(tempLists, firstList));
		assertTrue(_isListInCollection(tempLists, secondList));

		// Regret creating firstList
		lc.removeList(_getNewListReference(firstList, lc.getAllLists()));

		// Assert that firstList is gone and secondList is still there
		tempLists = lc.getAllLists();
		assertTrue(tempLists.size() == 1);
		assertFalse(_isListInCollection(tempLists, firstList));
		assertTrue(_isListInCollection(tempLists, secondList));

		// Create some tasks and add them to secondList
		final ITask t1 = new Task("aName", "", false);
		final ITask t2 = new Task("bName", "", false);
		final ITask t3 = new Task("cName", "", false);

		lc.addTask(t1, _getNewListReference(secondList, lc.getAllLists()));
		lc.addTask(t2, _getNewListReference(secondList, lc.getAllLists()));
		lc.addTask(t3, _getNewListReference(secondList, lc.getAllLists()));

		// Assert that the tasks have been added
		tempTasks = lc.getAllTasks();
		assertTrue(tempTasks.size() == 3);
		assertTrue(_isTaskInCollection(tempTasks, t1));
		assertTrue(_isTaskInCollection(tempTasks, t2));
		assertTrue(_isTaskInCollection(tempTasks, t3));

		// Edit one of the tasks
		final ITask t4 = new Task("dName", "", false);
		lc.editTask(_getNewTaskReference(t2, lc.getAllTasks()), t4);

		// Assert that the task have been edited/replaced
		tempTasks = lc.getAllTasks();
		assertTrue(tempTasks.size() == 3);
		assertTrue(_isTaskInCollection(tempTasks, t4));
		assertFalse(_isTaskInCollection(tempTasks, t2));

		// Remove the remaining list
		lc.removeList(_getNewListReference(secondList, lc.getAllLists()));

		// Assert that the list and all the tasks it contained is removed
		tempLists = lc.getAllLists();
		tempTasks = lc.getAllTasks();
		assertTrue(tempLists.size() == 0);
		assertTrue(tempTasks.size() == 0);

	}

	private boolean _isListInCollection(final Collection<ITaskCollection> c,
			final ITaskCollection col) {

		for (final ITaskCollection itc : c) {
			if (itc.getName().equals(col.getName())) {
				return true;
			}
		}

		return false;
	}

	private boolean _isTaskInCollection(final Collection<ITask> c, final ITask t) {

		for (final ITask it : c) {
			if (Task.isTasksEqual(it, t)) {
				return true;
			}
		}
		return false;
	}

	private ITaskCollection _getNewListReference(final ITaskCollection tc,
			final Collection<ITaskCollection> col) {

		ITaskCollection ret = null;

		for (final ITaskCollection c : col) {
			if (tc.getName().equals(c.getName())) {
				ret = c;
			}
		}

		return ret;
	}

	private ITask _getNewTaskReference(final ITask task,
			final Collection<ITask> col) {
		ITask ret = null;

		for (final ITask t : col) {
			if (t.getName().equals(task.getName())) {
				ret = t;
			}
		}

		return ret;
	}
}
