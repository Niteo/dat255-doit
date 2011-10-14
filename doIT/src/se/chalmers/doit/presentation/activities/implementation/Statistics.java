package se.chalmers.doit.presentation.activities.implementation;

import java.util.HashMap;

import se.chalmers.doit.R;
import se.chalmers.doit.logic.controller.ILogicController;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * An activity to display statistics as numbers.
 *
 * @author Boel
 *
 */
public class Statistics extends Activity {

	private int daysInterval;
	private Spinner intervalSpinner;
	private ArrayAdapter<CharSequence> adapter;
	private HashMap<String, Integer> intervalMap = new HashMap<String, Integer>();
	private HashMap<Integer, Integer> positionMap = new HashMap<Integer, Integer>();
	private ILogicController controller;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);

		_init();
	}

	private void _init() {
		controller = LogicController.getInstance();

		_initHashMap();

		// Get the saved interval
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		daysInterval = preferences.getInt("current", -1);

		_update(daysInterval);

		intervalSpinner = (Spinner) findViewById(R.id.intervalSpinner);

		// Init adapter
		adapter = ArrayAdapter.createFromResource(this, R.array.interval_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		intervalSpinner.setAdapter(adapter);

		intervalSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@SuppressWarnings("synthetic-access")
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Object item = parent.getItemAtPosition(pos);
						if (item != null) {
							if (item.toString() != null) {
								String s = item.toString();
								daysInterval = _findInterval(s);
								_update(daysInterval);

								SharedPreferences sp = getPreferences(MODE_PRIVATE);
								SharedPreferences.Editor edit = sp.edit();

								edit.putInt("current", daysInterval);
								edit.commit();
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// Nothing happens
					}
				});

		intervalSpinner.setSelection(_findPosition(Integer
				.valueOf(daysInterval)));
	}

	private void _update(int interval) {
		_setCompletedTasksNumber(controller.getNumberOfFinishedTasks(interval));
		_setCreatedTasksNumber(controller.getNumberOfCreatedTasks(interval));
		_setDeletedTasksNumber(controller.getNumberOfDeletedTasks(interval));
		_setExpiredTasksNumber(controller.getNumberOfOverdueTasks(interval));

		_setCreatedListsNumber(controller.getNumberOfCreatedLists(interval));
		_setDeletedListsNumber(controller.getNumberOfDeletedLists(interval));
	}

	private void _setCreatedTasksNumber(int amount) {
		((TextView) findViewById(R.id.createdTasksNumber)).setText("" + amount);
	}

	private void _setCompletedTasksNumber(int amount) {
		((TextView) findViewById(R.id.completedTasksNumber)).setText(""
				+ amount);

	}

	private void _setDeletedTasksNumber(int amount) {
		((TextView) findViewById(R.id.deletedTasksNumber)).setText("" + amount);

	}

	private void _setExpiredTasksNumber(int amount) {
		((TextView) findViewById(R.id.expiredTasksNumber)).setText("" + amount);

	}

	private void _setCreatedListsNumber(int amount) {
		((TextView) findViewById(R.id.createdListsNumber)).setText("" + amount);

	}

	private void _setDeletedListsNumber(int amount) {
		((TextView) findViewById(R.id.deletedListsNumber)).setText("" + amount);

	}

	private void _initHashMap() {
		intervalMap.clear();
		positionMap.clear();

		String[] array = getResources()
				.getStringArray((R.array.interval_array));

		// Maps the strings from the spinner to correspond to an interval
		intervalMap.put(array[0], Integer.valueOf(-1));
		intervalMap.put(array[1], Integer.valueOf(30));
		intervalMap.put(array[2], Integer.valueOf(7));
		intervalMap.put(array[3], Integer.valueOf(0));

		// Maps the intervals to correspond to an index
		positionMap.put(Integer.valueOf(-1), Integer.valueOf(0));
		positionMap.put(Integer.valueOf(30), Integer.valueOf(1));
		positionMap.put(Integer.valueOf(7), Integer.valueOf(2));
		positionMap.put(Integer.valueOf(0), Integer.valueOf(3));

	}

	private int _findInterval(String s) {
		Integer ret = intervalMap.get(s);
		return ret.intValue();
	}

	private int _findPosition(Integer value) {
		Integer ret = positionMap.get(value);
		return ret.intValue();
	}
}