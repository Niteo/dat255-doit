package se.chalmers.doit.presentation.activities.implementation;

import java.util.HashMap;

import se.chalmers.doit.R;
import android.app.Activity;
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

	private int daysInterval = -1;
	private Spinner intervalSpinner;
	private ArrayAdapter<CharSequence> adapter;
	private HashMap<String, Integer> map = new HashMap<String, Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);

		_initHashMap();
		_init(daysInterval);

		intervalSpinner = (Spinner) findViewById(R.id.intervalSpinner);

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
								_init(daysInterval);
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// Nothing happens
					}
				});
	}

	private void _init(int interval) {
		// TODO: use methods from a controller

		//Dummy methods for testing the gui, to be removed
		_setCompletedTasksNumber(interval);
		_setCreatedTasksNumber(interval);
		_setDeletedTasksNumber(interval);
		_setExpiredTasksNumber(interval);

		_setCreatedListsNumber(interval);
		_setDeletedListsNumber(interval);
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
		map.clear();

		String[] array = getResources()
				.getStringArray((R.array.interval_array));

		// Maps the strings from the spinner to correspond to an interval
		map.put(array[0], new Integer(-1));
		map.put(array[1], new Integer(30));
		map.put(array[2], new Integer(7));
		map.put(array[3], new Integer(0));

	}

	@SuppressWarnings("boxing")
	private int _findInterval(String s) {
		return map.get(s);
	}
}