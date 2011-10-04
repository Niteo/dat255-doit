package se.chalmers.doit.presentation.activities.implementation;

import java.util.ArrayList;
import java.util.HashMap;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Task;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.defaultlistview);
		final ArrayList<ITask> tasks = new ArrayList<ITask>();
		tasks.add(new Task("Wash the dishes", "Use the Yes washing-up liquid",
				false));
		tasks.add(new Task("Do homework", "All chapters!", false));
		adapter = new TaskListAdapter(this, tasks);

		setListAdapter(adapter);

		final ListView list = getListView();

		registerForContextMenu(list);
		final EditText edittext = (EditText) findViewById(R.id.quickaddedittext);
		final Button quickAddButton = (Button) findViewById(R.id.quickaddbutton);
		quickAddButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				if (edittext.getText().toString().length() > 0) {
					addTask(new Task(edittext.getText().toString(), "", false));
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
					addTask(new Task(edittext.getText().toString(),
							"Preset description.", false));
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
			deleteTask(adapter.getItem(info.position));
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

	public void addTask(final ITask task) {
		adapter.add(task);
		Toast.makeText(DefaultListView.this, "Task added!", Toast.LENGTH_SHORT)
				.show();
		((EditText) findViewById(R.id.quickaddedittext)).setText("");
	}

	public void deleteTask(final ITask task) {
		adapter.remove(task);
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