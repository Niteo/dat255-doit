package se.chalmers.doit.test.data.storage.wrappers;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.data.storage.wrappers.ITaskCollectionWrapper;
import se.chalmers.doit.data.storage.wrappers.implementation.TaskCollectionWrapper;
import android.test.AndroidTestCase;

public class TaskCollectionWrapperTest extends AndroidTestCase {

	private ITaskCollectionWrapper wrapper;
	private ITaskCollection taskCollection;
	private Collection<ITask> tasks;

	@Override
	@Before
	public void setUp() throws Exception {
		tasks = new ArrayList<ITask>();
		tasks.add(new Task("Task", "desc", false));
		taskCollection = new TaskCollection("Name", tasks);
		wrapper = new TaskCollectionWrapper(0, taskCollection);
	}

	public void testGetName() {
		assertTrue(wrapper.getName().equals(taskCollection.getName()));
	}

	public void testGetTaskList() {
		assertTrue(wrapper.getTasks().equals(taskCollection.getTasks()));
	}

	public void testGetID() {
		assertTrue(wrapper.getID() == 0);
	}

}
