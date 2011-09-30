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
	private Date date1;
	private Date date2;

	@Override
	@Before
	public void setUp() throws Exception {
		date1 = new Date(2010, 12, 12);
		date2 = new Date(2012, 12, 12);

		task1 = new Task("Task1", "desc", null, date1, null, 0, false);
		task2 = new Task("Task2", "desc", null, date2, null, 0, false);
	}

	public void DueDateComparatorStrategyoBoolean() {
		new DueDateComparatorStrategy(false);
		new DueDateComparatorStrategy(true);
	}

	public void testCompare() {
		IComparatorStrategy comparator = new DueDateComparatorStrategy(false);
		IComparatorStrategy invertedComparator = new DueDateComparatorStrategy(
				true);

		assertTrue(comparator.compare(task1, task2) == -1);
		assertTrue(invertedComparator.compare(task1, task2) == 1);

		assertTrue(comparator.compare(task1, task1) == 0);
		assertTrue(invertedComparator.compare(task1, task1) == 0);

		assertTrue(comparator.compare(task2, task1) == 1);
		assertTrue(invertedComparator.compare(task2, task1) == -1);

	}

}
