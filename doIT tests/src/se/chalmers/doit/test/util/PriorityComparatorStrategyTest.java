package se.chalmers.doit.test.util;

import java.util.Date;

import se.chalmers.doit.core.IPriority;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Priority;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.util.IComparatorStrategy;
import se.chalmers.doit.util.implementation.Constants;
import se.chalmers.doit.util.implementation.PriorityComparatorStrategy;
import android.test.AndroidTestCase;

public class PriorityComparatorStrategyTest extends AndroidTestCase {

	private IPriority p1;
	private IPriority p2;

	private ITask t1;
	private ITask t2;

	@Override
	public void setUp() {
		final Date dummyDate = new Date();

		p1 = new Priority(Constants.PRIORITY_HIGH);
		p2 = new Priority(Constants.PRIORITY_LOW);

		t1 = new Task("Task1", "hej", p1, dummyDate, dummyDate, 0, false);
		t2 = new Task("Task2", "d�", p2, dummyDate, dummyDate, 0, false);
	}

	public void testPriorityComparatorStrategy() {
		new PriorityComparatorStrategy(false);
		new PriorityComparatorStrategy(true);
	}

	public void testCompare() {
		final IComparatorStrategy strat = new PriorityComparatorStrategy(false);
		final IComparatorStrategy stratInv = new PriorityComparatorStrategy(
				true);

		assertTrue(strat.compare(t1, t2) == -1);
		assertTrue(strat.compare(t2, t1) == 1);
		assertTrue(strat.compare(t1, t1) == 0);

		assertTrue(stratInv.compare(t1, t2) == 1);
		assertTrue(stratInv.compare(t2, t1) == -1);
		assertTrue(stratInv.compare(t1, t1) == 0);
	}

}
