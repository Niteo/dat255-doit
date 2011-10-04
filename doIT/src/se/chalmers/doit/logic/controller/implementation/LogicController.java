package se.chalmers.doit.logic.controller.implementation;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.data.storage.IDataCache;
import se.chalmers.doit.data.storage.implementation.DataCache;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.verification.IDataVerifier;
import se.chalmers.doit.logic.verification.implementation.DataVerifier;

public class LogicController implements ILogicController {

	private final IDataVerifier verifier;
	private final IDataCache data;

	public LogicController() {
		verifier = new DataVerifier();
		data = new DataCache();
	}

	@Override
	public boolean addList(ITaskCollection taskCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addLists(Collection<ITaskCollection> collection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addTask(ITask task, ITaskCollection collection) {
		if (verifier.verifyTask(task)) {
			data.addTask(task, collection);
			return true;
		}
		return false;
	}

	@Override
	public boolean addTasks(Collection<ITask> tasks, ITaskCollection collection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clearData() {
		data.clearData();
	}

	@Override
	public boolean editList(ITaskCollection oldCollection,
			ITaskCollection newCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editTask(ITask oldTask, ITask newTask) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editTasks(ITaskCollection oldCollection,
			ITaskCollection newCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<ITaskCollection> getAllLists() {
		return data.getAllLists();
	}

	@Override
	public Collection<ITask> getAllTasks() {
		return data.getAllTasks();
	}

	@Override
	public void moveTask(ITask task, ITaskCollection taskCollection) {
		data.moveTask(task, taskCollection);
	}

	@Override
	public void removeList(ITaskCollection collection) {
		data.removeList(collection);
	}

	@Override
	public void removeLists(Collection<ITaskCollection> collection) {
		data.removeLists(collection);
	}

	@Override
	public void removeTask(ITask task) {
		data.removeTask(task);
	}

	@Override
	public void removeTasks(Collection<ITask> listOfTasksToRemove) {
		data.removeTasks(listOfTasksToRemove);
	}

}
