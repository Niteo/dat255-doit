package se.chalmers.doit.presentation.activities.implementation;

import se.chalmers.doit.R;
import se.chalmers.doit.logic.controller.implementation.LogicController;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;

public class Preferences extends PreferenceActivity {
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
		setContentView(R.layout.preferences_layout);

		final Button dataEraserButton = (Button) findViewById(R.id.erase_all_data_button);
		dataEraserButton.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(final View v) {
				_removeListsTasks();
			}
		});

		final Button statisticsEraserButton = (Button) findViewById(R.id.erase_all_statistics_button);
		statisticsEraserButton.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(final View v) {
				_removeStatistics();
			}
		});
	}

	private void _removeListsTasks() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						LogicController.getInstance().clearData();
						break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}

	private void _removeStatistics() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						LogicController.getInstance().clearStatisticsData();
						break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}
}