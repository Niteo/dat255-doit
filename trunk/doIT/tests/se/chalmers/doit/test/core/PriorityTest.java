package se.chalmers.doit.test.core;

import org.junit.Before;

import se.chalmers.doit.core.implementation.Priority;
import android.test.AndroidTestCase;

/**
 * JUnit testclass for testing se.chalmers.doit.test.core.Task
 *
 * @author Kaufmann
 */
public class PriorityTest extends AndroidTestCase {
	private Priority lowPriority;
	private Priority mediumPriority;
	private Priority highPriority;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		lowPriority = new Priority((byte) 0);
		mediumPriority = new Priority((byte) 3);
		highPriority = new Priority((byte) 5);
	}

	public void testGetValue() {
		assertTrue(lowPriority.getValue() == 0);
		assertTrue(mediumPriority.getValue() == 3);
		assertTrue(highPriority.getValue() == 5);
	}

	public void testCompareTo() {
		assertTrue(lowPriority.compareTo(lowPriority) == 0);
		assertTrue(lowPriority.compareTo(mediumPriority) == -1);
		assertTrue(lowPriority.compareTo(highPriority) == -1);

		assertTrue(mediumPriority.compareTo(lowPriority) == 1);
		assertTrue(mediumPriority.compareTo(mediumPriority) == 0);
		assertTrue(mediumPriority.compareTo(highPriority) == -1);

		assertTrue(highPriority.compareTo(lowPriority) == 1);
		assertTrue(highPriority.compareTo(mediumPriority) == 1);
		assertTrue(highPriority.compareTo(highPriority) == 0);
	}
}