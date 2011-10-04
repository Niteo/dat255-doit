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
		for (ITaskCollection list : data.getAllLists()) {
			if (!list.getName().equals(taskCollection.getName())) {
				data.addList(taskCollection);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addLists(Collection<ITaskCollection> collection) {
		for (ITaskCollection savedList : data.getAllLists()) {
			for (ITaskCollection newList : collection) {
				if (savedList.getName().equals(newList.getName())) {
					return false;
				}
			}
		}
		data.addLists(collection);
		return true;
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
		for (ITask task : tasks) {
			if (!verifier.verifyTask(task)) {
				return false;
			}
		}
		data.addTasks(tasks, collection);
		return true;
	}

	@Override
	public void clearData() {
		data.clearData();
	}

	@Override
	public boolean editList(ITaskCollection oldCollection,
			ITaskCollection newCollection) {
		for (ITaskCollection list : data.getAllLists()) {
			if (!newCollection.getName().equals(list.getName())) {
				data.editList(oldCollection, newCollection);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean editTask(ITask oldTask, ITask newTask) {
		if (verifier.verifyTask(newTask)) {
			data.editTask(oldTask, newTask);
			return true;
		}
		return false;
	}

	@Override
	public boolean editTasks(ITaskCollection oldCollection,
			ITaskCollection newCollection) {
		for (ITask task : newCollection.getTasks()) {
			if (!verifier.verifyTask(task)) {
				return false;
			}
		}
		//TODO implement a method editTasks in DataCache!?
		data.editList(oldCollection, newCollection);
		return true;
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
