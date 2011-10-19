package se.chalmers.doit.presentation.activities.implementation;

import java.util.*;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.app.*;
import android.content.*;
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
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setMessage("List name:");

				// Set an EditText view to get user input
				final EditText input = new EditText(this);
				input.setText(col.getName());
				input.setSingleLine(true);
				input.selectAll();
				alert.setView(input);

				alert.setPositiveButton("Change",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int whichButton) {
								String value = input.getText().toString();
								if (!col.getName().equals(value)) {
									if (!LogicController.getInstance()
											.editList(
													col,
													new TaskCollection(value,
															col.getTasks()))) {
										Toast.makeText(
												ListViewer.this,
												"List named " + value
														+ " already exists.",
												Toast.LENGTH_SHORT).show();
									}
								}
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int whichButton) {
								// Canceled
							}
						});
				final AlertDialog dialog = alert.create();
				input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(final View v,
							final boolean hasFocus) {
						if (hasFocus) {
							dialog.getWindow()
									.setSoftInputMode(
											WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
						}
					}
				});
				dialog.show();
				input.requestFocus();
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
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(final View v) {
				if (edittext.getText().toString().length() > 0) {
					_addList(new TaskCollection(edittext.getText().toString()));
				} else {
					Toast.makeText(ListViewer.this, "You must enter a name!",
							Toast.LENGTH_SHORT).show();
				}
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
		final Intent intent = intentMap.get(Integer.valueOf(item.getItemId()));
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
		final TaskCollection o = (TaskCollection) this.getListAdapter()
				.getItem(position);
		((MainActivity) getParent()).setActiveList(o.getName());
	}

	private void _addList(final ITaskCollection col) {
		if (LogicController.getInstance().addList(col)) {
			_updateView();
			Toast.makeText(ListViewer.this, "List added!", Toast.LENGTH_SHORT)
					.show();
			((EditText) findViewById(R.id.lv_addlistedittext)).setText("");
		} else {
			Toast.makeText(ListViewer.this,
					"Two lists cannot have the same name!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void _createIntentMap() {
		intentMap.clear();
		intentMap.put(Integer.valueOf(R.id.menu_about), new Intent(this,
				About.class));
		intentMap.put(Integer.valueOf(R.id.menu_help), new Intent(this,
				Help.class));
		intentMap.put(Integer.valueOf(R.id.menu_statistics), new Intent(this,
				Statistics.class));
		intentMap.put(Integer.valueOf(R.id.menu_settings), new Intent(this,
				Preferences.class));
	}

	private void _deleteList(final ITaskCollection list) {
		if (LogicController.getInstance().removeList(list)) {
			_updateView();
		}
	}

	private void _populateList() {
		adapter.clear();
		for (final ITaskCollection t : LogicController.getInstance()
				.getAllLists()) {
			adapter.add(t);
		}
	}

	private void _updateView() {
		_populateList();
	}
}