package se.chalmers.doit.presentation.activities.implementation;

import java.util.*;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.widget.*;

/**
 * View for viewing lists
 * 
 * @author Kaufmann
 * 
 */
public class ListViewer extends ListActivity {
	private ListListAdapter adapter;
	private final HashMap<Integer, Intent> intentMap = new HashMap<Integer, Intent>();

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		final ITaskCollection col = adapter.getItem(info.position);

		switch (item.getItemId()) {
			case R.id.lv_context_delete:
				_deleteList(col);
				return true;
			case R.id.lv_context_edit:
				// TODO
				return true;
		}
		return false;
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		adapter = new ListListAdapter(this, new ArrayList<ITaskCollection>());
		setListAdapter(adapter);
		_updateView();
		final ListView list = getListView();
		registerForContextMenu(list);
		final EditText edittext = (EditText) findViewById(R.id.lv_addlistedittext);
		final Button quickAddButton = (Button) findViewById(R.id.lv_addlistbutton);

		quickAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (edittext.getText().toString().length() > 0) {
					_addList(new TaskCollection(edittext.getText().toString()));
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
					_addList(new TaskCollection(edittext.getText().toString()));
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
			final ITaskCollection item = adapter.getItem(info.position);
			menu.setHeaderTitle(item.getName());
			final MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.listview_context_menu, menu);
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

	public void updateView() {
		_updateView();
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		TaskCollection o = (TaskCollection) this.getListAdapter().getItem(
				position);
		((MainActivity) getParent()).setActiveList(o.getName());
	}

	private void _addList(final ITaskCollection col) {
		if (LogicController.getInstance().addList(col)) {
			_updateView();
			Toast.makeText(ListViewer.this, "List added!", Toast.LENGTH_SHORT)
					.show();
			((EditText) findViewById(R.id.lv_addlistedittext)).setText("");
		}
	}

	private void _createIntentMap() {
		intentMap.clear();
		intentMap.put(R.id.menu_about, new Intent(this, About.class));
		intentMap.put(R.id.menu_help, new Intent(this, Help.class));
		intentMap.put(R.id.menu_statistics, new Intent(this, Statistics.class));
		intentMap.put(R.id.menu_settings, new Intent(this, Preferences.class));
	}

	private void _deleteList(final ITaskCollection list) {
		if (LogicController.getInstance().removeList(list)) {
			_updateView();
		}
	}

	private void _populateList() {
		adapter.clear();
		for (ITaskCollection t : LogicController.getInstance().getAllLists()) {
			adapter.add(t);
		}
	}

	private void _updateView() {
		_populateList();
	}
}