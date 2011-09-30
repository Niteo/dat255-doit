package se.chalmers.doit.test.data.storage.wrappers;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.data.storage.wrappers.ITaskWrapper;
import se.chalmers.doit.data.storage.wrappers.implementation.TaskWrapper;
import android.test.AndroidTestCase;

public class TaskWrapperTest extends AndroidTestCase {

	private ITaskWrapper tw;
	private ITask task;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		task = new Task("name", "desc", false);
		tw = new TaskWrapper(0, task);
	}

	@Test
	public void testGetName() {
		assertTrue(tw.getName().equals(task.getName()));
	}

	@Test
	public void testGetDescription() {
		assertTrue(tw.getDescription().equals(task.getName()));
	}

	@Test
	public void testGetDueDate() {
		assertTrue(tw.getDueDate().equals(task.getDueDate()));
	}

	@Test
	public void testGetPriority() {
		assertTrue(tw.getPriority().equals(task.getPriority()));
	}

	@Test
	public void testGetReminderDate() {
		assertTrue(tw.getReminderDate().equals(task.getReminderDate()));
	}

	@Test
	public void testGetCustomPosition() {
		assertTrue(tw.getCustomPosition() == task.getCustomPosition());
	}

	@Test
	public void testIsCompleted() {
		assertTrue(tw.isCompleted() == task.isCompleted());
	}

	@Test
	public void testGetID() {
		assertTrue(tw.getID() == 0);
		assertTrue(new TaskWrapper(5, task).getID() == 5);
	}

}
