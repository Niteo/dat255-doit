package se.chalmers.doit.logic.controller.implementation;

import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.data.storage.IDataStorage;
import se.chalmers.doit.data.storage.implementation.DataCache;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.verification.IDataVerifier;
import se.chalmers.doit.logic.verification.implementation.DataVerifier;

public final class LogicController implements ILogicController {

	private final IDataVerifier verifier;
	private final IDataStorage data;
	private static LogicController instance;

	private LogicController() {
		verifier = new DataVerifier();
		data = new DataCache();
	}

	@Override
	public boolean addList(final ITaskCollection taskCollection) {
		if (data.getAllLists().size() == 0) {
			return data.addList(taskCollection);
		}
		for (final ITaskCollection list : data.getAllLists()) {
			if (!list.getName().equals(taskCollection.getName())) {
				return data.addList(taskCollection);
			}
		}
		return false;
	}

	@Override
	public int addLists(final Collection<ITaskCollection> collection) {
		if (data.getAllLists().size() == 0) {
			return data.addLists(collection);
		}
		for (final ITaskCollection savedList : data.getAllLists()) {
			for (final ITaskCollection newList : collection) {
				if (savedList.getName().equals(newList.getName())) {
					return 0;
				}
			}
		}
		return data.addLists(collection);
	}

	@Override
	public boolean addTask(final ITask task, final ITaskCollection collection) {
		if (verifier.verifyTask(task)) {
			return data.addTask(task, collection);
		}
		return false;
	}

	@Override
	public int addTasks(final Collection<ITask> tasks,
			final ITaskCollection collection) {
		for (final ITask task : tasks) {
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
	public boolean editList(final ITaskCollection oldCollection,
			final ITaskCollection newCollection) {
		for (final ITaskCollection list : data.getAllLists()) {
			if (!newCollection.getName().equals(list.getName())) {
				return data.editList(oldCollection, newCollection);
			}
		}
		return false;
	}

	@Override
	public boolean editTask(final ITask oldTask, final ITask newTask) {
		if (verifier.verifyTask(newTask)) {
			return data.editTask(oldTask, newTask);
		}
		return false;
	}

	@Override
	public boolean editTasks(final ITaskCollection oldCollection,
			final ITaskCollection newCollection) {
		for (final ITask task : newCollection.getTasks()) {
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
	public boolean moveTask(final ITask task,
			final ITaskCollection taskCollection) {
		return data.moveTask(task, taskCollection);
	}

	@Override
	public boolean removeList(final ITaskCollection collection) {
		return data.removeList(collection);
	}

	@Override
	public int removeLists(final Collection<ITaskCollection> collection) {
		return data.removeLists(collection);
	}

	@Override
	public boolean removeTask(final ITask task) {
		return data.removeTask(task);
	}

	@Override
	public int removeTasks(final Collection<ITask> listOfTasksToRemove) {
		return data.removeTasks(listOfTasksToRemove);
	}

}
