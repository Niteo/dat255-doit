package se.chalmers.doit.presentation.activities.implementation;

import java.util.HashMap;

import se.chalmers.doit.R;
import se.chalmers.doit.util.implementation.Constants;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class TaskSorter extends Activity {

	private Spinner primarySpinner;
	private Spinner secondarySpinner;
	private Spinner tertiarySpinner;
	private int primarySelectedIndex;
	private int secondarySelectedIndex;
	private int tertiarySelectedIndex;
	private int tempPrimary;
	private int tempSecondary;
	private int tempTertiary;
	private Button cancelButton;
	private Button sortButton;

	private ArrayAdapter<CharSequence> adapter;
	private final HashMap<String, Integer> sortingMap = new HashMap<String, Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasksorter);

		_init();
	}

	private void _init() {

		_initHashMaps();

		// Sets selected indices to the ones saved in shared preferences, if no
		// data is found, the index is set to 0
		SharedPreferences preferences = getSharedPreferences(
				Constants.SHARED_PREFERENCES_SORTING, MODE_PRIVATE);
		primarySelectedIndex = preferences.getInt("current_primary_sorting", 0);
		secondarySelectedIndex = preferences.getInt(
				"current_secondary_sorting", 0);
		tertiarySelectedIndex = preferences.getInt("current_tertiary_sorting",
				0);

		// Init spinners
		primarySpinner = (Spinner) findViewById(R.id.primarySpinner);
		secondarySpinner = (Spinner) findViewById(R.id.secondarySpinner);
		tertiarySpinner = (Spinner) findViewById(R.id.tertiarySpinner);

		// Init button
		cancelButton = (Button) findViewById(R.id.sortCancelButton);
		sortButton = (Button) findViewById(R.id.sortButton);

		adapter = ArrayAdapter.createFromResource(this, R.array.sorting_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		primarySpinner.setAdapter(adapter);
		secondarySpinner.setAdapter(adapter);
		tertiarySpinner.setAdapter(adapter);

		_initListeners();

		if (primarySelectedIndex != 0) {
			primarySpinner.setSelection(primarySelectedIndex);
		} else {
			primarySpinner.setSelection(Constants.GOOD_DEFAULT_PRIMARY);
		}
		if (secondarySelectedIndex != 0) {
			secondarySpinner.setSelection(secondarySelectedIndex);
		} else {
			secondarySpinner.setSelection(Constants.GOOD_DEFAULT_SECONDARY);
		}
		if (tertiarySelectedIndex != 0) {
			tertiarySpinner.setSelection(tertiarySelectedIndex);
		} else {
			tertiarySpinner.setSelection(Constants.GOOD_DEFAULT_TERTIARY);
		}
	}

	private void _initHashMaps() {
		sortingMap.clear();

		String[] array = getResources().getStringArray((R.array.sorting_array));

		// Maps the strings from the spinner to correspond to an index
		sortingMap.put(array[0], Integer.valueOf(0));
		sortingMap.put(array[1], Integer.valueOf(1));
		sortingMap.put(array[2], Integer.valueOf(2));
		sortingMap.put(array[3], Integer.valueOf(3));
		sortingMap.put(array[4], Integer.valueOf(4));
		sortingMap.put(array[5], Integer.valueOf(5));
	}

	@SuppressWarnings("boxing")
	private int _findSelectedIndex(String selectedWord) {
		return sortingMap.get(selectedWord);
	}

	private void _sort() {

		SharedPreferences sp = getSharedPreferences(
				Constants.SHARED_PREFERENCES_SORTING, MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();

		// Save index of primary sort in shared preferences
		primarySelectedIndex = tempPrimary;
		edit.putInt(Constants.SHARED_PREFERENCES_PRIMARY_SORTING,
				primarySelectedIndex);
		edit.commit();

		// Save index of secondary sort in shared preferences
		secondarySelectedIndex = tempSecondary;
		edit.putInt(Constants.SHARED_PREFERENCES_SECONDARY_SORTING,
				secondarySelectedIndex);
		edit.commit();

		// Save index of tertiary sort in shared preferences
		tertiarySelectedIndex = tempTertiary;
		edit.putInt(Constants.SHARED_PREFERENCES_TERTIARY_SORTING,
				tertiarySelectedIndex);
		edit.commit();

		this.finish();
	}

	private void _cancel() {

		this.finish();

	}

	private void _initListeners() {

		sortButton.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(final View v) {
				_sort();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(final View v) {
				_cancel();
			}
		});

		primarySpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@SuppressWarnings("synthetic-access")
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Object item = parent.getItemAtPosition(pos);
						if (item != null) {
							if (item.toString() != null) {
								String s = item.toString();
								tempPrimary = _findSelectedIndex(s);

							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// Nothing happens
					}
				});

		secondarySpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@SuppressWarnings("synthetic-access")
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Object item = parent.getItemAtPosition(pos);
						if (item != null) {
							if (item.toString() != null) {
								String s = item.toString();
								tempSecondary = _findSelectedIndex(s);

							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// Nothing happens
					}
				});

		tertiarySpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@SuppressWarnings("synthetic-access")
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Object item = parent.getItemAtPosition(pos);
						if (item != null) {
							if (item.toString() != null) {
								String s = item.toString();
								tempTertiary = _findSelectedIndex(s);

							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// Nothing happens
					}
				});
	}
}
