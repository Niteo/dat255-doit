package se.chalmers.doit.logic.controller.implementation;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.data.storage.IDataCache;
import se.chalmers.doit.data.storage.implementation.DataCache;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.verification.IDataVerifier;
import se.chalmers.doit.logic.verification.implementation.DataVerifier;

public final class LogicController implements ILogicController {

	private final IDataVerifier verifier;
	private final IDataCache data;
	private static LogicController instance;

	private LogicController() {
		verifier = new DataVerifier();
		data = new DataCache();
	}

	@Override
	public boolean addList(ITaskCollection taskCollection) {
		if (data.getAllLists().size() == 0) {
			return data.addList(taskCollection);
		}
		for (ITaskCollection list : data.getAllLists()) {
			if (!list.getName().equals(taskCollection.getName())) {
				return data.addList(taskCollection);
			}
		}
		return false;
	}

	@Override
	public int addLists(Collection<ITaskCollection> collection) {
		if (data.getAllLists().size() == 0) {
			return data.addLists(collection);
		}
		for (ITaskCollection savedList : data.getAllLists()) {
			for (ITaskCollection newList : collection) {
				if (savedList.getName().equals(newList.getName())) {
					return 0;
				}
			}
		}
		return data.addLists(collection);
	}

	@Override
	public boolean addTask(ITask task, ITaskCollection collection) {
		if (verifier.verifyTask(task)) {
			return data.addTask(task, collection);
		}
		return false;
	}

	@Override
	public int addTasks(Collection<ITask> tasks, ITaskCollection collection) {
		for (ITask task : tasks) {
			if (!verifier.verifyTask(task)) {
				return 0;
			}
		}
		return data.addTasks(tasks, collection);
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
				return data.editList(oldCollection, newCollection);
			}
		}
		return false;
	}

	@Override
	public boolean editTask(ITask oldTask, ITask newTask) {
		if (verifier.verifyTask(newTask)) {
			return data.editTask(oldTask, newTask);
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
		// TODO implement a method editTasks in DataCache!?
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

	public static synchronized ILogicController getInstance() {
		if (instance == null) {
			instance = new LogicController();
		}
		return instance;
	}

	@Override
	public boolean moveTask(ITask task, ITaskCollection taskCollection) {
		return data.moveTask(task, taskCollection);
	}

	@Override
	public boolean removeList(ITaskCollection collection) {
		return data.removeList(collection);
	}

	@Override
	public int removeLists(Collection<ITaskCollection> collection) {
		return data.removeLists(collection);
	}

	@Override
	public boolean removeTask(ITask task) {
		return data.removeTask(task);
	}

	@Override
	public int removeTasks(Collection<ITask> listOfTasksToRemove) {
		return data.removeTasks(listOfTasksToRemove);
	}

}
