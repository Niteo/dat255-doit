package se.chalmers.doit.test.util;

import java.util.Date;

import org.junit.Before;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.util.IComparatorStrategy;
import se.chalmers.doit.util.implementation.DueDateComparatorStrategy;
import android.test.AndroidTestCase;

public class DueDateComparatorStrategyTest extends AndroidTestCase {

	private ITask task1;
	private ITask task2;
	private ITask task3;
	private Date date1;
	private Date date2;

	@Override
	@Before
	public void setUp() throws Exception {
		date1 = new Date(2010, 12, 12);
		date2 = new Date(2012, 12, 12);

		task1 = new Task("Task1", "desc", null, date1, null, 0, false);
		task2 = new Task("Task2", "desc", null, date2, null, 0, false);
		task3 = new Task("Task3", "desc", null, null, null, 0, false);
	}

	public void testDueDateComparatorStrategyBoolean() {
		new DueDateComparatorStrategy(false);
		new DueDateComparatorStrategy(true);
	}

	public void testCompare() {
		final IComparatorStrategy comparator = new DueDateComparatorStrategy(
				false);
		final IComparatorStrategy invertedComparator = new DueDateComparatorStrategy(
				true);

		assertTrue(comparator.compare(task1, task2) == -1);
		assertTrue(invertedComparator.compare(task1, task2) == 1);

		assertTrue(comparator.compare(task1, task1) == 0);
		assertTrue(invertedComparator.compare(task1, task1) == 0);

		assertTrue(comparator.compare(task2, task1) == 1);
		assertTrue(invertedComparator.compare(task2, task1) == -1);

		assertTrue(comparator.compare(task1, task3) == -1);
		assertTrue(invertedComparator.compare(task1, task3) == -1);

		assertTrue(comparator.compare(task3, task2) == 1);
		assertTrue(invertedComparator.compare(task3, task2) == 1);

	}

}
