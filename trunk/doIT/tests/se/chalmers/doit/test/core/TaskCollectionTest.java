package se.chalmers.doit.test.core;

import java.util.ArrayList;

import org.junit.*;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.TaskCollection;

import android.test.AndroidTestCase;

public class TaskCollectionTest extends AndroidTestCase{
	
	@Test
	public void testTaskCollection() {
		new TaskCollection("Name", new ArrayList<ITask>());
	}

	@Test
	public void testGetName() {
		TaskCollection t = new TaskCollection("Name", new ArrayList<ITask>());
		assert(t.getName().equals("Name"));
		t = new TaskCollection("Sw0mp", new ArrayList<ITask>());
		assert(t.getName().equals("Sw0mp"));
	}

	@Test
	public void testGetTaskList() {
		ArrayList<ITask> list1 = new ArrayList<ITask>();
		ArrayList<ITask> list2 = new ArrayList<ITask>();
		
		TaskCollection t = new TaskCollection("Name", list1);
		assert(t.getTaskList().equals(list1));
		t = new TaskCollection("Sw0mp", list2);
		assert(t.getTaskList().equals(list2));
	}
}
