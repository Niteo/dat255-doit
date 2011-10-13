package se.chalmers.doit.presentation.activities.implementation;

import java.util.Calendar;
import java.util.Date;

import se.chalmers.doit.R;
import se.chalmers.doit.core.IPriority;
import se.chalmers.doit.core.implementation.Priority;
import se.chalmers.doit.core.implementation.Task;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

/**
 * Activity for creating new tasks and editing existing tasks.
 * 
 * @author Marco Baxemyr
 * 
 */
public class EditTaskView extends Activity {

	private Intent data;
	private Button mPickDueDate;
	private Button mPickReminderDate;
	private Button mPickReminderTime;
	private boolean dueDateSet = false;
	private boolean reminderDateSet = false;
	private boolean reminderTimeSet = false;
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
			dueDateSet = true;
			mDueYear = year;
			mDueMonth = monthOfYear;
			mDueDay = dayOfMonth;
			updateDueDateDisplay();
		}
	};
	private DatePickerDialog.OnDateSetListener mReminderDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			reminderDateSet = true;
			mReminderYear = year;
			mReminderMonth = monthOfYear;
			mReminderDay = dayOfMonth;
			updateReminderDateDisplay();
		}

	};
	private TimePickerDialog.OnTimeSetListener mReminderTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			reminderTimeSet = true;
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
		reminderTimeSet = false;
		mReminderHour = 0;
		mReminderMinute = 0;
		updateReminderTimeDisplay();
	}

	private void _loadTask(Bundle extras) {
		String name = extras.getString("taskName");
		String description = extras.getString("taskDescription");
		IPriority priority = new Priority(extras.getByte("taskPriority"));
		long dueDateLong =  extras.getLong("taskDueDate");
		dueDateSet = dueDateLong == 0 ? false : true;
		if (dueDateSet) {
			Date dueDate = new Date(dueDateLong);
			mDueYear = dueDate.getYear();
			mDueMonth = dueDate.getMonth();
			mDueDay = dueDate.getDate();
			updateDueDateDisplay();
		}
		long reminderDateLong =  extras.getLong("taskReminderDate");
		reminderDateSet = reminderDateLong == 0 ? false : true;
		if (reminderDateSet) {
			Date reminderDate = new Date(reminderDateLong);
			mReminderYear = reminderDate.getYear();
			mReminderMonth = reminderDate.getMonth();
			mReminderDay = reminderDate.getDate();
			mReminderHour = reminderDate.getHours();
			mReminderMinute = reminderDate.getMinutes();
			reminderTimeSet = true;			
			updateReminderDateDisplay();
			updateReminderTimeDisplay();
		}
		
		
		
		((EditText) findViewById(R.id.edittaskname)).setText(name);
		((EditText) findViewById(R.id.edittaskdescription)).setText(description);
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.priority);
		switch (priority.getValue()) {
		case 1:
			radioGroup.check(R.id.priority_1);
			break;
		case 2:
			radioGroup.check(R.id.priority_2);
			break;
		case 3:
			radioGroup.check(R.id.priority_3);
			break;
		case 4:
			radioGroup.check(R.id.priority_4);
			break;
		case 5:
			radioGroup.check(R.id.priority_5);
			break;
		default:
			radioGroup.check(R.id.priority_3);
			break;
		}
		
		
		
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

		Button saveButton = (Button) findViewById(R.id.savebutton);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Task task = _generateTask();
				data.putExtra("taskName", task.getName());
				data.putExtra("taskDescription", task.getDescription());
				data.putExtra("taskPriority", task.getPriority().getValue());
				if (dueDateSet) 
					data.putExtra("taskDueDate", task.getDueDate().getTime());
				if (reminderDateSet && reminderTimeSet)
					data.putExtra("taskReminderDate", task.getReminderDate()
								.getTime());
				setResult(RESULT_OK, data);
				finish();
			}
		});

		Button cancelButton = (Button) findViewById(R.id.cancelbutton);
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
		
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.priority);
		radioGroup.check(R.id.priority_3);
		data = getIntent();
		Bundle extras = data.getExtras();
		if (extras != null) {
			_loadTask(extras);
		}
	}

	private Task _generateTask() {
		String name = ((EditText) findViewById(R.id.edittaskname)).getText()
				.toString();
		String description = ((EditText) findViewById(R.id.edittaskdescription))
				.getText().toString();
		int radioButtonId = ((RadioGroup) findViewById(R.id.priority))
				.getCheckedRadioButtonId();
		byte priorityValue;
		switch (radioButtonId) {
		case R.id.priority_1:
			priorityValue = 1;
			break;
		case R.id.priority_2:
			priorityValue = 2;
			break;
		case R.id.priority_3:
			priorityValue = 3;
			break;
		case R.id.priority_4:
			priorityValue = 4;
			break;
		case R.id.priority_5:
			priorityValue = 5;
			break;
		default:
			priorityValue = 3;
			break;
		}
		IPriority priority = new Priority(priorityValue);
		Date dueDate = new Date(mDueYear, mDueMonth, mDueYear);
		Date reminderDate = new Date(mReminderYear, mReminderMonth,
				mReminderDay, mReminderHour, mReminderMinute);
		return new Task(name, description, priority, dueDate, reminderDate, 0,
				false);
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
			timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Disable",
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
		if (dueDateSet) {
			mPickDueDate.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(mDueYear).append("-").append(mDueMonth + 1).append("-")
					.append(mDueDay).append(" "));
		}
		else {
			mPickDueDate.setText("None");
		}
	}

	private void updateReminderDateDisplay() {
		if (reminderDateSet) {
			mPickReminderDate.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(mReminderYear).append("-").append(mReminderMonth + 1)
					.append("-").append(mReminderDay).append(" "));
		}
		else {
			mPickReminderDate.setText("None");
		}
	}

	private void updateReminderTimeDisplay() {
		if (reminderTimeSet) {	
			mPickReminderTime.setText(new StringBuilder()
					.append(pad(mReminderHour)).append(":").append(
							pad(mReminderMinute)));
		}
		else {
			mPickReminderTime.setText("None");
		}
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
}