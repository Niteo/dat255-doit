package se.chalmers.doit.presentation.activities.implementation;

import java.util.Calendar;

import se.chalmers.doit.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Activity for creating new tasks and editing existing tasks.
 * 
 * @author phelerox
 * 
 */
public class EditTaskView extends Activity {

	private Button mPickDueDate;
	private Button mPickReminderDate;
	private Button mPickReminderTime;
	private int mDueYear;
	private int mDueMonth;
	private int mDueDay;
	private int mReminderYear;
	private int mReminderMonth;
	private int mReminderDay;
	private int mReminderHour;
	private int mReminderMinute;

	static final int DUE_DATE_DIALOG_ID = 0;
	static final int REMINDER_DATE_DIALOG_ID = 1;
	static final int REMINDER_TIME_DIALOG_ID = 2;

	private DatePickerDialog.OnDateSetListener mDueDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mDueYear = year;
			mDueMonth = monthOfYear;
			mDueDay = dayOfMonth;
			updateDueDateDisplay();
		}
	};
	private DatePickerDialog.OnDateSetListener mReminderDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mReminderYear = year;
			mReminderMonth = monthOfYear;
			mReminderDay = dayOfMonth;
			updateReminderDateDisplay();
		}

	};
	private TimePickerDialog.OnTimeSetListener mReminderTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mReminderHour = hourOfDay;
			mReminderMinute = minute;
			updateReminderTimeDisplay();
		}
	};

	private DialogInterface.OnCancelListener mOnCancelReminderTimeListener = new DialogInterface.OnCancelListener() {
		public void onCancel(DialogInterface dialog) {
			resetReminderTime();
		}
	};

	private void resetReminderTime() {
		mReminderHour = 0;
		mReminderMinute = 0;
		updateReminderTimeDisplay();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edittaskview);
		mPickDueDate = (Button) findViewById(R.id.pickDueDate);

		mPickDueDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DUE_DATE_DIALOG_ID);
			}
		});

		mPickReminderDate = (Button) findViewById(R.id.pickReminderDate);

		mPickReminderDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(REMINDER_DATE_DIALOG_ID);
			}
		});

		mPickReminderTime = (Button) findViewById(R.id.pickReminderTime);

		// add a click listener to the button
		mPickReminderTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(REMINDER_TIME_DIALOG_ID);
			}
		});

		final Calendar c = Calendar.getInstance();
		mDueYear = c.get(Calendar.YEAR);
		mDueMonth = c.get(Calendar.MONTH);
		mDueDay = c.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DUE_DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDueDateSetListener, mDueYear,
					mDueMonth, mDueDay);
		case REMINDER_DATE_DIALOG_ID:
			return new DatePickerDialog(this, mReminderDateSetListener,
					mDueYear, mDueMonth, mDueDay);
		case REMINDER_TIME_DIALOG_ID:
			TimePickerDialog timePicker = new TimePickerDialog(this,
					mReminderTimeSetListener, mReminderHour, mReminderMinute,
					true);
			timePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
		            "Disable",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                    resetReminderTime();
		                }
		            });
			timePicker.setOnCancelListener(mOnCancelReminderTimeListener);
			return timePicker;
		}
		return null;
	}

	private void updateDueDateDisplay() {
		mPickDueDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mDueYear).append("-").append(mDueMonth + 1).append("-")
				.append(mDueDay).append(" "));
	}

	private void updateReminderDateDisplay() {
		mPickReminderDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mReminderYear + 1).append("-").append(
						mReminderMonth + 1).append("-").append(mReminderDay)
				.append(" "));
	}

	private void updateReminderTimeDisplay() {
		mPickReminderTime.setText(new StringBuilder()
				.append(pad(mReminderHour)).append(":").append(
						pad(mReminderMinute)));
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
}