package se.chalmers.doit.test.core;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.*;

import android.test.AndroidTestCase;

import se.chalmers.doit.core.implementation.*;

/**
 * JUnit testclass for testing task.java.
 * @author Kaufmann, Boel
 *
 */
public class TaskTest extends AndroidTestCase{
	@Test
	public void testTaskStringStringIPriorityDateDateIntBoolean() {
		new Task("Name", "Description", new Priority((byte)1),
				new Date(1), new Date(2), 0, false);
	}

	@Test
	public void testTaskStringStringBoolean() {
		new Task("Name", "Description", false);
	}
	

	@Test (expected = NullPointerException.class)
	public void testTaskITaskNullPointer() {
		new Task(null);
	}
	
	public void testTaskITask() {
		new Task(new Task("Name", "Description", false));
	}

	@Test
	public void testGetCustomPosition() {
		Task t = new Task("Name", "Description", new Priority((byte)1),
				new Date(1), new Date(2), 5, false);
		assert(t.getCustomPosition() == 5);
		
		t = new Task("Name", "Description", new Priority((byte)1),
				new Date(1), new Date(2), 13337, false);
		assert(t.getCustomPosition() == 13337);
	}

	@Test
	public void testGetDescription() {
		Task t = new Task("Name", "Desc.", false);
		assert(t.getDescription().equals("Desc."));
		t = new Task("Name", "ASCii25", false);
		assert(t.getDescription().equals("ASCii25"));
	}

	@Test
	public void testGetDueDate() {
		Date date1 = new Date(1);
		Date date2 = new Date(2);
		
		Task t = new Task("Name", "Description", new Priority((byte)1),
				date1, new Date(2), 5, false);
		assert(t.getDueDate() == date1);
		
		t = new Task("Name", "Description", new Priority((byte)1),
				date2, new Date(2), 13337, false);
		assert(t.getDueDate() == date2);
	}

	@Test
	public void testGetName() {
		Task t = new Task("Boel", "...", false);
		assert(t.getName().equals("Boel"));
		t = new Task("Nelson", "...", false);
		assert(t.getName().equals("Nelson"));
	}

	@Test
	public void testGetPriority() {
		Task t = new Task("Name", "Description", new Priority((byte)1),
				new Date(1), new Date(2), 5, false);
		assert(t.getPriority().getValue() == 1);
		
		t = new Task("Name", "Description", new Priority((byte)2),
				new Date(1), new Date(2), 5, false);
		assert(t.getPriority().getValue() == 2);
	}

	@Test
	public void testGetReminderDate() {
		Date date1 = new Date(4);
		Date date2 = new Date(5);
		
		Task t = new Task("Name", "Description", new Priority((byte)1),
				new Date(1), date1, 5, false);
		assert(t.getDueDate() == date1);
		
		t = new Task("Name", "Description", new Priority((byte)1),
				new Date(1), date2, 5, false);
		assert(t.getDueDate() == date2);
	}

	@Test
	public void testIsCompleted() {
		Task t = new Task("Boel", "...", false);
		assert(!t.isCompleted());
		t = new Task("Boel", "...", true);
		assert(t.isCompleted());
	}

	@Test
	public void testToString() {
		Task t = new Task("Boel", "...", false);
		assert(t.toString().equals(t.getName()));
		t = new Task("Nelson1337", "...", true);
		assert(t.toString().equals(t.getName()));
	}
}