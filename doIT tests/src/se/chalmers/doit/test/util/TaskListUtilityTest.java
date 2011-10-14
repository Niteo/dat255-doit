package se.chalmers.doit.test.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Priority;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.util.IComparatorStrategy;
import se.chalmers.doit.util.implementation.DueDateComparatorStrategy;
import se.chalmers.doit.util.implementation.NameComparatorStrategy;
import se.chalmers.doit.util.implementation.PriorityComparatorStrategy;
import se.chalmers.doit.util.implementation.TaskListUtility;
import android.test.AndroidTestCase;

public class TaskListUtilityTest extends AndroidTestCase {

	private ITask t1, t2, t3, t4, t5;
	private List<ITask> tc;

	@Override
	public void setUp() throws Exception {

		t1 = new Task("a", "b", new Priority((byte) 3), new Date(2), new Date(
				10), 0, false);
		t2 = new Task("a", "b", new Priority((byte) 3), new Date(10), new Date(
				10), 0, false);
		t3 = new Task("b", "b", new Priority((byte) 2), new Date(11), new Date(
				10), 0, false);
		t4 = new Task("b", "b", new Priority((byte) 3), new Date(10), new Date(
				10), 0, false);
		t5 = new Task("c", "b", new Priority((byte) 3), new Date(10), new Date(
				10), 0, false);

		tc = new ArrayList<ITask>();

		tc.add(t2);
		tc.add(t3);
		tc.add(t1);
		tc.add(t5);
		tc.add(t4);

	}

	public void testSortTasks() {
		final TaskListUtility tlu = new TaskListUtility();

		final IComparatorStrategy primary = new NameComparatorStrategy(false);
		final IComparatorStrategy secondary = new PriorityComparatorStrategy(
				false);
		final IComparatorStrategy tertiary = new DueDateComparatorStrategy(
				false);

		tlu.sortTasks(tc, primary, secondary, tertiary);

		assertTrue(tc.indexOf(t1) == 0);
		assertTrue(tc.indexOf(t2) == 1);
		assertTrue(tc.indexOf(t3) == 3);
		assertTrue(tc.indexOf(t4) == 2);
		assertTrue(tc.indexOf(t5) == 4);

	}

}
