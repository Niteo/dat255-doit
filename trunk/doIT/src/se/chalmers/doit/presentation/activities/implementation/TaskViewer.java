package se.chalmers.doit.presentation.activities.implementation;

import java.util.*;

import se.chalmers.doit.R;
import se.chalmers.doit.core.*;
import se.chalmers.doit.core.implementation.*;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import se.chalmers.doit.util.IComparatorStrategy;
import se.chalmers.doit.util.implementation.*;
import android.app.ListActivity;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.widget.*;

/**
 * Activity displaying the default list.
 * 
 * @author Marco Baxemyr
 * 
 */
public class TaskViewer extends ListActivity {

	private static final int ADD_NEW_TASK = 0;
	private static final int CONTEXT_SUBMENU = 14432;
	private static final int EDIT_TASK = 1;
	private ITaskCollection activeList;
	private TaskListAdapter adapter;
	private final HashMap<Integer, Intent> intentMap = new HashMap<Integer, Intent>();
	private ITask lastEditedTask;
	private int mParentContextMenuListIndex; // workaround to allow submenus in
												// a ListACtivity's ContextMenu
	private final HashMap<Integer, IComparatorStrategy> strategyMap = new HashMap<Integer, IComparatorStrategy>();

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		// if info == null, the user is in the submenu
		int idxOfList = (info != null) ? info.position
				: this.mParentContextMenuListIndex;
		final ITask task = adapter.getItem(idxOfList);
		switch (item.getItemId()) {

			case R.id.context_complete:
			case R.id.context_incomplete:
				_toggleTaskCompleted(task);
				return true;
			case R.id.context_edit:
				lastEditedTask = task;
				final String name = task.getName();
				final String description = task.getDescription();
				final IPriority priority = task.getPriority();
				final Date dueDate = task.getDueDate();
				final Date reminderDate = task.getReminderDate();
				final boolean isCompleted = task.isCompleted();
				final Intent editTaskIntent = new Intent(this,
						EditTaskView.class);
				editTaskIntent.putExtra("taskName", name);
				editTaskIntent.putExtra("taskDescription", description);
				editTaskIntent.putExtra("taskPriority", priority.getValue());
				editTaskIntent.putExtra("taskDueDate", dueDate == null ? -1
						: dueDate.getTime());
				editTaskIntent.putExtra("taskReminderDate",
						reminderDate == null ? -1 : reminderDate.getTime());
				editTaskIntent.putExtra("taskIsCompleted", isCompleted);
				startActivityForResult(editTaskIntent, EDIT_TASK);
				return true;
			case R.id.context_delete:
				_deleteTask(task);
				return true;
			case CONTEXT_SUBMENU:
				String listName = item.getTitle().toString();
				Collection<ITaskCollection> lists = LogicController
						.getInstance().getAllLists();
				for (ITaskCollection list : lists) {
					if (list.getName().equals(listName)) {
						LogicController.getInstance().moveTask(task, list);
					}
				}
				return true;
			default: // can handle submenus if we save off info.position
				this.mParentContextMenuListIndex = idxOfList;
				break;
		}
		return false;
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskview);

		_initStrategyMap();

		adapter = new TaskListAdapter(this, new ArrayList<ITask>());
		setListAdapter(adapter);
		_updateView();
		final ListView list = getListView();
		registerForContextMenu(list);
		final EditText edittext = (EditText) findViewById(R.id.quickaddedittext);
		final Button quickAddButton = (Button) findViewById(R.id.quickaddbutton);
		final Button addNewButton = (Button) findViewById(R.id.addnewbutton);
		quickAddButton.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(final View v) {
				if (edittext.getText().toString().length() > 0) {
					_addTask(new Task(edittext.getText().toString(), "", false));
				} else {
					Toast.makeText(TaskViewer.this, "You must enter a name!",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		addNewButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				// TODO: send the content of edittext as a Bundle to the Create
				// New Task-activity (EditTaskView) as the name of the new task
				final Intent addNewTaskIntent = new Intent(TaskViewer.this,
						EditTaskView.class);
				startActivityForResult(addNewTaskIntent, ADD_NEW_TASK);
			}
		});

		edittext.setOnKeyListener(new OnKeyListener() {
			@SuppressWarnings("synthetic-access")
			@Override
			public boolean onKey(final View v, final int keyCode,
					final KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					if (edittext.getText().toString().length() > 0) {
						_addTask(new Task(edittext.getText().toString(), "",
								false));
					}
					return true;
				}
				return false;
			}
		});

		_initIntentMap();
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v,
			final ContextMenuInfo menuInfo) {
		if (v.getId() == android.R.id.list) {
			final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			final ITask task = adapter.getItem(info.position);
			menu.setHeaderTitle(task.getName());
			final MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
			if (task.isCompleted()) {
				menu.removeItem(R.id.context_complete);
			} else {
				menu.removeItem(R.id.context_incomplete);
			}
			if (LogicController.getInstance().getAllLists().size() > 1) {
				SubMenu move = menu.addSubMenu("Move");
				Collection<ITaskCollection> lists = LogicController
						.getInstance().getAllLists();
				for (ITaskCollection list : lists) {
					if (!(list.getName() == activeList.getName())) {
						move.add(Menu.NONE, CONTEXT_SUBMENU, Menu.NONE,
								list.getName());
					}
				}
				// workaround to make Move appear between Edit and Delete
				// instead of at the bottom.
				menu.removeItem(R.id.context_delete);
				menu.add(Menu.NONE, R.id.context_delete, Menu.NONE, "Delete");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.taskviewmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final Intent intent = intentMap.get(Integer.valueOf(item.getItemId()));
		if (intent != null) {
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {

		super.onResume();

		_updateView();
	}

	@Override
	public void onWindowFocusChanged(final boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		_updateView();
	}

	public void setActiveList(final String name) {
		final SharedPreferences sp = getPreferences(MODE_PRIVATE);
		final SharedPreferences.Editor edit = sp.edit();
		edit.putString("lastlist", name);
		edit.commit();
	}

	public void updateView() {
		_updateView();
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (requestCode == ADD_NEW_TASK && resultCode == RESULT_OK) {
			_addTask(_createTaskFromBundleData(data));
		}
		if (requestCode == EDIT_TASK && resultCode == RESULT_OK) {
			final ITask task = _createTaskFromBundleData(data);
			_editTask(lastEditedTask, task);
			lastEditedTask = task;
		}
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		final ITask task = (Task) this.getListAdapter().getItem(position);
		// TODO show more details of the task to the user.
		if (task.getReminderDate() == null) {
			Toast.makeText(TaskViewer.this, "Reminder Date is null!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void _addTask(final ITask task) {
		if (LogicController.getInstance().addTask(task, activeList)) {
			_updateView();
			Toast.makeText(TaskViewer.this, "Task added!", Toast.LENGTH_SHORT)
					.show();
			((EditText) findViewById(R.id.quickaddedittext)).setText("");
		}
	}

	private Task _createTaskFromBundleData(final Intent data) {
		final String name = data.getExtras().getString("taskName");
		final String description = data.getExtras()
				.getString("taskDescription");
		final IPriority priority = new Priority(data.getExtras().getByte(
				"taskPriority"));
		final long dueDateLong = data.getExtras().getLong("taskDueDate");
		final long reminderDateLong = data.getExtras().getLong(
				"taskReminderDate");
		final Date dueDate = dueDateLong != -1 ? new Date(dueDateLong) : null;
		final Date reminderDate = reminderDateLong != -1 ? new Date(
				reminderDateLong) : null;
		final boolean isCompleted = data.getBooleanExtra("taskIsCompleted",
				false);
		return new Task(name, description, priority, dueDate, reminderDate, 0,
				isCompleted);
	}

	private void _deleteTask(final ITask task) {
		if (LogicController.getInstance().removeTask(task)) {
			_updateView();
		}
	}

	private void _editTask(final ITask oldTask, final ITask newTask) {
		if (LogicController.getInstance().editTask(oldTask, newTask)) {
			_updateView();
		}
	}

	private void _initIntentMap() {
		intentMap.clear();
		intentMap.put(Integer.valueOf(R.id.taskviewmenu_sort), new Intent(this,
				TaskSorter.class));
		intentMap.put(Integer.valueOf(R.id.taskviewmenu_about), new Intent(
				this, About.class));
		intentMap.put(Integer.valueOf(R.id.taskviewmenu_help), new Intent(this,
				Help.class));
		intentMap.put(Integer.valueOf(R.id.taskviewmenu_statistics),
				new Intent(this, Statistics.class));
		intentMap.put(Integer.valueOf(R.id.taskviewmenu_settings), new Intent(
				this, Preferences.class));
	}

	private void _initStrategyMap() {

		// Maps the index to a IComparatorStrategy
		strategyMap.clear();
		strategyMap.put(Integer.valueOf(0),
				new DueDateComparatorStrategy(false));
		strategyMap
				.put(Integer.valueOf(1), new DueDateComparatorStrategy(true));
		strategyMap.put(Integer.valueOf(2), new NameComparatorStrategy(false));
		strategyMap.put(Integer.valueOf(3), new NameComparatorStrategy(true));
		strategyMap.put(Integer.valueOf(4),
				new PriorityComparatorStrategy(true));
		strategyMap.put(Integer.valueOf(5), new PriorityComparatorStrategy(
				false));
	}

	private void _populateList() {

		adapter.clear();
		if (activeList == null) {
			LogicController.getInstance().addList(
					new TaskCollection("Default", new ArrayList<ITask>()));
			_updateActiveList();
		} else {
			for (final ITask task : _sortList().getTasks()) {
				adapter.add(task);
			}
		}

	}

	private ITaskCollection _sortList() {

		final SharedPreferences preferences = getSharedPreferences(
				Constants.SHARED_PREFERENCES_SORTING, MODE_PRIVATE);

		final int primarySortingStrategyIndex = preferences.getInt(
				Constants.SHARED_PREFERENCES_PRIMARY_SORTING,
				Constants.GOOD_DEFAULT_PRIMARY);
		final int secondarySortingStrategyIndex = preferences.getInt(
				Constants.SHARED_PREFERENCES_SECONDARY_SORTING,
				Constants.GOOD_DEFAULT_SECONDARY);
		final int tertiarySortingStrategyIndex = preferences.getInt(
				Constants.SHARED_PREFERENCES_TERTIARY_SORTING,
				Constants.GOOD_DEFAULT_TERTIARY);

		final IComparatorStrategy primary = strategyMap.get(Integer
				.valueOf(primarySortingStrategyIndex));
		final IComparatorStrategy secondary = strategyMap.get(Integer
				.valueOf(secondarySortingStrategyIndex));
		final IComparatorStrategy tertiary = strategyMap.get(Integer
				.valueOf(tertiarySortingStrategyIndex));

		final TaskListUtility taskListUtil = new TaskListUtility();

		ITaskCollection tempList = null;

		if (activeList != null) {
			final ArrayList<ITask> tasks = new ArrayList<ITask>(
					activeList.getTasks());
			taskListUtil.sortTasks(tasks, primary, secondary, tertiary);
			tempList = new TaskCollection(activeList.getName(), tasks);
		}

		return tempList;

	}

	private void _toggleTaskCompleted(final ITask task) {
		if (LogicController.getInstance().toggleTaskCompleted(task)) {
			_updateView();
		}
	}

	private void _updateActiveList() {
		final Collection<ITaskCollection> c = LogicController.getInstance()
				.getAllLists();
		final SharedPreferences sp = getPreferences(MODE_PRIVATE);
		final String lastListShown = sp.getString("lastlist", "Default");

		activeList = null;
		for (final ITaskCollection list : c) {
			if (list.getName().equals(lastListShown)) {
				activeList = list;
				return;
			}
		}
	}

	private void _updateHeader() {
		if (activeList != null) {
			((TextView) findViewById(R.id.listName)).setText(activeList
					.getName());
		}
	}

	private void _updateView() {
		_updateActiveList();

		if (activeList == null) {
			setActiveList("Default");
		}

		_populateList();
		_updateHeader();
	}

}