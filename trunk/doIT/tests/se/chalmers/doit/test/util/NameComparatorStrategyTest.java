package se.chalmers.doit.test.util;

import org.junit.Before;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.util.IComparatorStrategy;
import se.chalmers.doit.util.implementation.NameComparatorStrategy;
import android.test.AndroidTestCase;

public class NameComparatorStrategyTest extends AndroidTestCase {

	private ITask t1, t2, t3, t4, t5, t6, t7;

	@Override
	@Before
	public void setUp() throws Exception {
		t1 = new Task("a", "asd", false);
		t2 = new Task("b", "asd", false);
		t3 = new Task("aa", "asd", false);
		t4 = new Task("ab", "asd", false);
		t5 = new Task("A", "asd", false);
		t6 = new Task("B", "asd", false);
		t7 = new Task("AA", "asd", false);
	}

	public void testNameComparatorStrategy() {
		new NameComparatorStrategy(false);
		new NameComparatorStrategy(true);
	}

	public void testCompare() {
		final IComparatorStrategy strat = new NameComparatorStrategy(false);
		final IComparatorStrategy stratInv = new NameComparatorStrategy(true);

		assertTrue(strat.compare(t1, t2) == -1);
		assertTrue(strat.compare(t2, t1) == 1);
		assertTrue(strat.compare(t1, t1) == 0);

		assertTrue(strat.compare(t3, t4) == -1);
		assertTrue(strat.compare(t5, t1) == -1);
		assertTrue(strat.compare(t6, t2) == -1);

		assertTrue(strat.compare(t1, t3) == -1);
		assertTrue(strat.compare(t7, t1) == -1);

		// Test with inverted strategy
		assertTrue(stratInv.compare(t1, t2) == 1);
		assertTrue(stratInv.compare(t2, t1) == -1);
		assertTrue(stratInv.compare(t1, t1) == 0);

		assertTrue(stratInv.compare(t3, t4) == 1);
		assertTrue(stratInv.compare(t5, t1) == 1);
		assertTrue(stratInv.compare(t6, t2) == 1);

		assertTrue(stratInv.compare(t1, t3) == 1);
		assertTrue(stratInv.compare(t7, t1) == 1);
	}

}
