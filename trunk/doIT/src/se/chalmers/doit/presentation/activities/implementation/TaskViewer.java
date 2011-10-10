package se.chalmers.doit.presentation.activities.implementation;

import java.util.*;

import se.chalmers.doit.R;
import se.chalmers.doit.core.*;
import se.chalmers.doit.core.implementation.*;
import se.chalmers.doit.logic.controller.implementation.LogicController;
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
 * @author phelerox
 * 
 */
public class TaskViewer extends ListActivity {

	private ITaskCollection activeList;
	private TaskListAdapter adapter;
	private final HashMap<Integer, Intent> intentMap = new HashMap<Integer, Intent>();

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		final ITask task = adapter.getItem(info.position);

		switch (item.getItemId()) {
			case R.id.context_complete:
			case R.id.context_incomplete:
				_toggleTaskCompleted(task);
				return true;
			case R.id.context_edit:
				// TODO Add edit ruta :)
				return true;
			case R.id.context_delete:
				_deleteTask(adapter.getItem(info.position));
				return true;
		}
		return false;
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskview);
		adapter = new TaskListAdapter(this, new ArrayList<ITask>());
		setListAdapter(adapter);
		_updateView();
		final ListView list = getListView();
		registerForContextMenu(list);
		final EditText edittext = (EditText) findViewById(R.id.quickaddedittext);
		final Button quickAddButton = (Button) findViewById(R.id.quickaddbutton);
		quickAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (edittext.getText().toString().length() > 0) {
					_addTask(new Task(edittext.getText().toString(), "", false));
				}
			}
		});

		edittext.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(final View v, final int keyCode,
					final KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					_addTask(new Task(edittext.getText().toString(), "", false));
					return true;
				}
				return false;
			}
		});

		_createIntentMap();
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
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final Intent intent = intentMap.get(item.getItemId());
		if (intent != null) {
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onWindowFocusChanged(final boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		_updateView();
	}

	public void setActiveList(final String name) {
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("lastlist", name);
		edit.commit();
	}

	public void updateView() {
		_updateView();
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		final Object o = this.getListAdapter().getItem(position);
		final String keyword = o.toString();
		Toast.makeText(TaskViewer.this, "Clicked on: " + keyword,
				Toast.LENGTH_SHORT).show();
	}

	private void _addTask(final ITask task) {
		if (LogicController.getInstance().addTask(task, activeList)) {
			_updateView();
			Toast.makeText(TaskViewer.this, "Task added!", Toast.LENGTH_SHORT)
					.show();
			((EditText) findViewById(R.id.quickaddedittext)).setText("");
		}
	}

	private void _createIntentMap() {
		intentMap.clear();
		intentMap.put(R.id.menu_about, new Intent(this, About.class));
		intentMap.put(R.id.menu_help, new Intent(this, Help.class));
		intentMap.put(R.id.menu_statistics, new Intent(this, Statistics.class));
		intentMap.put(R.id.menu_settings, new Intent(this, Preferences.class));
	}

	private void _deleteTask(final ITask task) {
		if (LogicController.getInstance().removeTask(task)) {
			_updateView();
		}
	}

	private void _populateList() {
		adapter.clear();
		if (activeList == null) {
			LogicController.getInstance().addList(
					new TaskCollection("Default", new ArrayList<ITask>()));
			_updateActiveList();
		} else {
			for (ITask task : activeList.getTasks()) {
				adapter.add(task);
			}
		}
	}

	private void _toggleTaskCompleted(final ITask task) {
		if (LogicController.getInstance().toggleTaskCompleted(task)) {
			_updateView();
		}
	}

	private void _updateActiveList() {
		Collection<ITaskCollection> c = LogicController.getInstance()
				.getAllLists();
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		String lastListShown = sp.getString("lastlist", "Default");

		activeList = null;
		for (ITaskCollection list : c) {
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