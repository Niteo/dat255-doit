package se.chalmers.doit.test.core;

import org.junit.Before;

import se.chalmers.doit.core.implementation.Priority;
import android.test.AndroidTestCase;

/**
 * JUnit testclass for testing se.chalmers.doit.test.core.Task
 * @author Kaufmann
 */
public class PriorityTest extends AndroidTestCase{
	private Priority lowPriority;
	private Priority mediumPriority;
	private Priority highPriority;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		lowPriority = new Priority((byte)0);
		mediumPriority = new Priority((byte)3);
		highPriority = new Priority((byte)5);
	}
	
	public void testGetValue() throws Exception {
		assert(lowPriority.getValue() == 0);
		assert(mediumPriority.getValue() == 3);
		assert(highPriority.getValue() == 5);
		
		assert(!(lowPriority.getValue() != 0));
		assert(!(lowPriority.getValue() != 3));
		assert(!(lowPriority.getValue() != 5));
	}
	
	public void testCompareTo() throws Exception {
		assert(lowPriority.compareTo(lowPriority) == 0);
		assert(lowPriority.compareTo(mediumPriority) == -1);
		assert(lowPriority.compareTo(highPriority) == -1);
		
		assert(mediumPriority.compareTo(lowPriority) == 1);
		assert(mediumPriority.compareTo(mediumPriority) == 0);
		assert(mediumPriority.compareTo(highPriority) == -1);
		
		assert(highPriority.compareTo(lowPriority) == 1);
		assert(highPriority.compareTo(mediumPriority) == 1);
		assert(highPriority.compareTo(highPriority) == 0);
	}
}