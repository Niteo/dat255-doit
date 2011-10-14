package se.chalmers.doit.logic.controller.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import se.chalmers.doit.core.IStatisticalData;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.data.storage.IDataStorage;
import se.chalmers.doit.data.storage.IStatisticsDataStorage;
import se.chalmers.doit.data.storage.implementation.StatisticsDataCache;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.verification.IDataVerifier;
import se.chalmers.doit.logic.verification.implementation.DataVerifier;

public final class LogicController implements ILogicController {

	private final IDataVerifier verifier;
	private IDataStorage data;
	private final IStatisticsDataStorage statistics;
	private static LogicController instance;

	private LogicController() {
		verifier = new DataVerifier();
		statistics = new StatisticsDataCache();
	}

	@Override
	public boolean addList(final ITaskCollection taskCollection) throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		if (verifier.verifyList(taskCollection, data.getAllLists())) {
			incrementNumberOfCreatedLists(1);
			return data.addList(taskCollection);
		}
		return false;
	}

	@Override
	public int addLists(final Collection<ITaskCollection> collection)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		if (data.getAllLists().size() == 0) {
			return data.addLists(collection);
		}

		boolean canBeAdded = true;
		for (ITaskCollection newCollection : collection) {
			if (!verifier.verifyList(newCollection, data.getAllLists())) {
				canBeAdded = false;
			}
		}

		if (canBeAdded) {
			incrementNumberOfCreatedLists(collection.size());
			return data.addLists(collection);
		}
		return 0;
	}

	@Override
	public boolean addTask(final ITask task, final ITaskCollection collection)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		if (verifier.verifyTask(task)) {
			incrementNumberOfCreatedTasks(1);
			return data.addTask(task, collection);
		}
		return false;
	}

	@Override
	public int addTasks(final Collection<ITask> tasks,
			final ITaskCollection collection)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		for (final ITask task : tasks) {
			if (!verifier.verifyTask(task)) {
				return 0;
			}
		}
		incrementNumberOfCreatedTasks(tasks.size());
		return data.addTasks(tasks, collection);
	}

	@Override
	public void clearData()throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		data.clearData();
	}

	@Override
	public boolean completeTask(final ITask task)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		return _completeTask(task);
	}

	@Override
	public boolean decompleteTask(final ITask task)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		return _decompleteTask(task);
	}

	@Override
	public boolean editList(final ITaskCollection oldCollection,
			final ITaskCollection newCollection)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		for (final ITaskCollection list : data.getAllLists()) {
			if (!newCollection.getName().equals(list.getName())) {
				return data.editList(oldCollection, newCollection);
			}
		}
		return false;
	}

	@Override
	public boolean editTask(final ITask oldTask, final ITask newTask)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		if (verifier.verifyTask(newTask)) {
			return data.editTask(oldTask, newTask);
		}
		return false;
	}

	@Override
	public boolean editTasks(final ITaskCollection oldCollection,
			final ITaskCollection newCollection) throws IllegalStateException{
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		for (final ITask task : newCollection.getTasks()) {
			if (!verifier.verifyTask(task)) {
				return false;
			}
		}
		data.editList(oldCollection, newCollection);
		return true;
	}

	@Override
	public Collection<ITaskCollection> getAllLists()throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		return data.getAllLists();
	}

	@Override
	public Collection<ITask> getAllTasks()throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

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
			final ITaskCollection taskCollection)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		return data.moveTask(task, taskCollection);
	}

	@Override
	public boolean removeList(final ITaskCollection collection)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		incrementNumberOfDeletedLists(1);
		return data.removeList(collection);
	}

	@Override
	public int removeLists(final Collection<ITaskCollection> collection)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		incrementNumberOfDeletedLists(collection.size());
		return data.removeLists(collection);
	}

	@Override
	public boolean removeTask(final ITask task)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		incrementNumberOfDeletedTasks(1);
		return data.removeTask(task);
	}

	@Override
	public int removeTasks(final Collection<ITask> listOfTasksToRemove)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		incrementNumberOfDeletedTasks(listOfTasksToRemove.size());
		return data.removeTasks(listOfTasksToRemove);
	}

	@Override
	public int getNumberOfCreatedTasks(final int pastDays) throws IllegalStateException{
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		int retVal = 0;
		for (final IStatisticalData d : _getDataForInterval(pastDays)) {
			retVal += d.getCreatedTasks();
		}

		return retVal;
	}

	@Override
	public int getNumberOfFinishedTasks(final int pastDays) throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		int retVal = 0;
		for (final IStatisticalData d : _getDataForInterval(pastDays)) {
			retVal += d.getFinishedTasks();
		}

		return retVal;
	}

	@Override
	public int getNumberOfOverdueTasks(final int pastDays) throws IllegalStateException{
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		int retVal = 0;
		for (final IStatisticalData d : _getDataForInterval(pastDays)) {
			retVal += d.getOverdueTasks();
		}

		return retVal;
	}

	@Override
	public int getNumberOfDeletedTasks(final int pastDays)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		int retVal = 0;
		for (final IStatisticalData d : _getDataForInterval(pastDays)) {
			retVal += d.getDeletedTasks();
		}

		return retVal;
	}

	@Override
	public int getNumberOfCreatedLists(final int pastDays)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		int retVal = 0;
		for (final IStatisticalData d : _getDataForInterval(pastDays)) {
			retVal += d.getCreatedLists();
		}

		return retVal;
	}

	@Override
	public int getNumberOfDeletedLists(final int pastDays)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		int retVal = 0;
		for (final IStatisticalData d : _getDataForInterval(pastDays)) {
			retVal += d.getDeletedLists();
		}

		return retVal;
	}

	@Override
	public void incrementNumberOfCreatedTasks(final int amount) throws IllegalStateException{
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		statistics.reportCreatedTasks(amount, new Date());

	}

	@Override
	public void incrementNumberOfFinishedTasks(final int amount)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		statistics.reportFinishedTasks(amount, new Date());

	}

	@Override
	public void incrementNumberOfOverdueTasks(final int amount) throws IllegalStateException{
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		statistics.reportOverdueTasks(amount, new Date());

	}

	@Override
	public void incrementNumberOfDeletedTasks(final int amount) throws IllegalStateException{
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		statistics.reportDeletedTasks(amount, new Date());

	}

	@Override
	public void incrementNumberOfCreatedLists(final int amount)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		statistics.reportCreatedLists(amount, new Date());

	}

	@Override
	public void incrementNumberOfDeletedLists(final int amount)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		statistics.reportDeletedLists(amount, new Date());

	}

	@Override
	public void clearStatisticsData()throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		statistics.clearData();

	}

	@Override
	public boolean toggleTaskCompleted(final ITask task)throws IllegalStateException {
		if(data==null){
			throw new IllegalStateException("No storage strategy has been set!");
		}

		if (task.isCompleted()) {
			return _decompleteTask(task);
		}
		return _completeTask(task);
	}

	@Override
	public void setStorageStrategy(IDataStorage dataStorage) {
		data = dataStorage;
	}

	private Collection<IStatisticalData> _getDataForInterval(final int interval) {

		final Collection<IStatisticalData> retList = new ArrayList<IStatisticalData>();

		final Date tempDate = new Date();

		tempDate.setSeconds(0);
		tempDate.setMinutes(0);
		tempDate.setHours(0);

		Date date = new Date(tempDate.getTime() - (long) 86400000
				* (long) interval);

		if (interval < 0) {
			date = new Date(0);
		}

		for (final IStatisticalData d : statistics.getStatisticsData()) {
			if (d.getDate().compareTo(date) > 0) {
				retList.add(d);
			}
		}

		return retList;

	}

	private boolean _completeTask(final ITask task) {
		incrementNumberOfFinishedTasks(1);
		return data.editTask(task, new Task(task, true));
	}

	private boolean _decompleteTask(final ITask task) {
		return data.editTask(task, new Task(task, false));
	}

}
