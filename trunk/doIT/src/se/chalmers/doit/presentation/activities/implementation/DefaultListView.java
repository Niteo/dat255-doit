package se.chalmers.doit.presentation.activities.implementation;

import java.util.ArrayList;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Task;
import android.app.ListActivity;
import android.content.Intent;
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
 * @author phelerox
 *
 */
public class DefaultListView extends ListActivity {
	/** Called when the activity is first created. */

	private TaskListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.defaultlistview);
		ArrayList<ITask> tasks = new ArrayList<ITask>();
		tasks.add(new Task("Wash the dishes", "Use the Yes washing-up liquid",
				false));
		tasks.add(new Task("Do homework", "All chapters!", false));
		adapter = new TaskListAdapter(this, tasks);

		setListAdapter(adapter);

		ListView list = getListView();

		registerForContextMenu(list);
		final EditText edittext = (EditText) findViewById(R.id.quickaddedittext);
		Button quickAddButton = (Button) findViewById(R.id.quickaddbutton);
		quickAddButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addTask(new Task(edittext.getText().toString(),
						"Preset description.", false));
			}
		});

		edittext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
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
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(DefaultListView.this, "Clicked on: " + keyword,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == android.R.id.list) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			ITask task = adapter.getItem(info.position);
			menu.setHeaderTitle(task.getName());
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
			if (task.isCompleted()) {
				menu.removeItem(R.id.context_complete);
			}
			else {
				menu.removeItem(R.id.context_incomplete);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		ITask task = adapter.getItem(info.position);;
		switch (item.getItemId()) {
		case R.id.context_complete:
		case R.id.context_incomplete:
			adapter.insert(new Task(task, !task.isCompleted()), info.position);
			adapter.remove(adapter.getItem(info.position+1));
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_help:
			Intent showHelp = new Intent(this, Help.class);
			startActivity(showHelp);
			return true;
		case R.id.menu_about:
			Intent showAbout = new Intent(this, About.class);
			startActivity(showAbout);
			return true;
		case R.id.menu_statistics:
			// TODO
			return true;
		case R.id.menu_settings:
			// TODO
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void addTask(ITask task) {
		adapter.add(task);
		Toast.makeText(DefaultListView.this, "Task added!", Toast.LENGTH_SHORT)
				.show();
	}

	public void deleteTask(ITask task) {
		adapter.remove(task);
	}
}