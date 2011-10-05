package se.chalmers.doit.presentation.activities.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity displaying the default list.
 * 
 * @author phelerox
 * 
 */
public class DefaultListView extends ListActivity {

	private final HashMap<Integer, Intent> intentMap = new HashMap<Integer, Intent>();
	private TaskListAdapter adapter;
	private ITaskCollection activeList;

	private ITaskCollection _getActiveList() {
		Collection<ITaskCollection> c = LogicController.getInstance().getAllLists();
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		String lastListShown = sp.getString("lastListShown", "Default");
		for (ITaskCollection list : c) {
			if (list.getName().equals(lastListShown)) {
				return list;
			}
		}
		return null;
	}
	
	private void _populateList() {
		activeList = _getActiveList();
		if (activeList == null) {
			LogicController.getInstance().addList(new TaskCollection("Default", new ArrayList<ITask>()));
			activeList = _getActiveList();
		}
		else {
			adapter.clear();
			for(ITask task : activeList.getTasks()) {
				adapter.add(task);
			}
		}
	}
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.defaultlistview);
		adapter = new TaskListAdapter(this, new ArrayList<ITask>());
		
		_populateList();
		setListAdapter(adapter);
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
					_addTask(new Task(edittext.getText().toString(),
							"", false));
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
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		final ITask task = adapter.getItem(info.position);

		switch (item.getItemId()) {
		case R.id.context_complete:
		case R.id.context_incomplete:
			adapter.insert(new Task(task, !task.isCompleted()), info.position);
			adapter.remove(adapter.getItem(info.position + 1));
			return true;
		case R.id.context_edit:
			// TODO
			return true;
		case R.id.context_delete:
			Toast.makeText(DefaultListView.this, "Deleted", Toast.LENGTH_SHORT)
					.show();
			_deleteTask(adapter.getItem(info.position));
			return true;
		}
		return true;
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

	private void _addTask(final ITask task) {
		if (LogicController.getInstance().addTask(task, activeList)) {
			_populateList();
			Toast.makeText(DefaultListView.this, "Task added!", Toast.LENGTH_SHORT)
			.show();
			((EditText) findViewById(R.id.quickaddedittext)).setText("");
		}
	}

	private void _deleteTask(final ITask task) {
		if (LogicController.getInstance().removeTask(task)) {
			_populateList();
		}
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		final Object o = this.getListAdapter().getItem(position);
		final String keyword = o.toString();
		Toast.makeText(DefaultListView.this, "Clicked on: " + keyword,
				Toast.LENGTH_SHORT).show();
	}

	private void _createIntentMap() {
		intentMap.clear();
		intentMap.put(R.id.menu_about, new Intent(this, About.class));
		intentMap.put(R.id.menu_help, new Intent(this, Help.class));
		intentMap.put(R.id.menu_statistics, new Intent(this, Statistics.class));
		intentMap.put(R.id.menu_settings, new Intent(this, Preferences.class));
	}
}