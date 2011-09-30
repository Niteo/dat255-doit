package se.chalmers.doit.presentation.activities.implementation;

import java.util.ArrayList;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Task;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class DefaultListView extends ListActivity {
	/** Called when the activity is first created. */

	private TaskListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.defaultlistview);
		ArrayList<ITask> tasks = new ArrayList<ITask>();
		tasks.add(new Task("Wash the dishes", "Use the Yes washing-up liquid"));
		tasks.add(new Task("Do homework", "All chapters!"));
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
						"Preset description."));
			}
		});

		edittext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					addTask(new Task(edittext.getText().toString(),
							"Preset description."));
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
			menu.setHeaderTitle(adapter.getItem(info.position).getName());
			String[] menuItems = { "Mark as Complete", "Edit", "Delete" };
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();

		if (item.getTitle() == "Delete") {
			Toast.makeText(DefaultListView.this, "Deleted", Toast.LENGTH_SHORT)
					.show();
			deleteTask(adapter.getItem(info.position));
		} else {
			Toast.makeText(DefaultListView.this,
					"Clicked on: " + item.getTitle(), Toast.LENGTH_SHORT)
					.show();
		}

		return true;
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