package se.chalmers.doit.test.core;

import java.util.ArrayList;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.TaskCollection;

import android.test.AndroidTestCase;

public class TaskCollectionTest extends AndroidTestCase{
	
	public void testTaskCollection() {
		new TaskCollection("Name", new ArrayList<ITask>());
	}

	public void testGetName() {
		TaskCollection t = new TaskCollection("Name", new ArrayList<ITask>());
		assertTrue(t.getName().equals("Name"));
		t = new TaskCollection("Sw0mp", new ArrayList<ITask>());
		assertTrue(t.getName().equals("Sw0mp"));
	}

	public void testGetTaskList() {
		ArrayList<ITask> list1 = new ArrayList<ITask>();
		ArrayList<ITask> list2 = new ArrayList<ITask>();
		
		TaskCollection t = new TaskCollection("Name", list1);
		assertTrue(t.getTaskList().equals(list1));
		t = new TaskCollection("Sw0mp", list2);
		assertTrue(t.getTaskList().equals(list2));
	}
}