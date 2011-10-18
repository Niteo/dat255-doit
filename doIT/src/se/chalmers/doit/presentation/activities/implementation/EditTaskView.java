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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

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

	private static final int DUE_DATE_DIALOG_ID = 0;
	private static final int REMINDER_DATE_DIALOG_ID = 1;
	private static final int REMINDER_TIME_DIALOG_ID = 2;

	private final DatePickerDialog.OnDateSetListener mDueDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(final DatePicker view, final int year,
				final int monthOfYear, final int dayOfMonth) {
			dueDateSet = true;
			mDueYear = year;
			mDueMonth = monthOfYear;
			mDueDay = dayOfMonth;
			updateDueDateDisplay();
		}
	};
	private final DatePickerDialog.OnDateSetListener mReminderDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(final DatePicker view, final int year,
				final int monthOfYear, final int dayOfMonth) {
			reminderDateSet = true;
			mReminderYear = year;
			mReminderMonth = monthOfYear;
			mReminderDay = dayOfMonth;
			updateReminderDateDisplay();
		}

	};
	private final TimePickerDialog.OnTimeSetListener mReminderTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(final TimePicker view, final int hourOfDay,
				final int minute) {
			reminderTimeSet = true;
			mReminderHour = hourOfDay;
			mReminderMinute = minute;
			updateReminderTimeDisplay();
		}
	};

	private final DialogInterface.OnCancelListener mOnCancelReminderTimeListener = new DialogInterface.OnCancelListener() {
		@Override
		public void onCancel(final DialogInterface dialog) {
			resetReminderTime();
		}
	};

	private final DialogInterface.OnCancelListener mOnCancelReminderDateListener = new DialogInterface.OnCancelListener() {
		@Override
		public void onCancel(final DialogInterface dialog) {
			resetReminderDate();
		}
	};

	private final DialogInterface.OnCancelListener mOnCancelDueDateListener = new DialogInterface.OnCancelListener() {
		@Override
		public void onCancel(final DialogInterface dialog) {
			resetDueDate();
		}
	};

	private void _loadTask(final Bundle extras) {
		final String name = extras.getString("taskName");
		final String description = extras.getString("taskDescription");
		final IPriority priority = new Priority(extras.getByte("taskPriority"));
		final long dueDateLong = extras.getLong("taskDueDate");
		dueDateSet = dueDateLong == -1 ? false : true;
		if (dueDateSet) {
			final Date dueDate = new Date(dueDateLong);
			mDueYear = dueDate.getYear();
			mDueMonth = dueDate.getMonth();
			mDueDay = dueDate.getDate();
			updateDueDateDisplay();
		}
		final long reminderDateLong = extras.getLong("taskReminderDate");
		reminderDateSet = reminderDateLong == -1 ? false : true;
		if (reminderDateSet) {
			final Date reminderDate = new Date(reminderDateLong);
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
		((EditText) findViewById(R.id.edittaskdescription))
				.setText(description);
		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.priority);
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
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edittaskview);
		mPickDueDate = (Button) findViewById(R.id.pickDueDate);
		mPickDueDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				showDialog(DUE_DATE_DIALOG_ID);
			}
		});

		mPickReminderDate = (Button) findViewById(R.id.pickReminderDate);

		mPickReminderDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				showDialog(REMINDER_DATE_DIALOG_ID);
			}
		});

		mPickReminderTime = (Button) findViewById(R.id.pickReminderTime);

		// add a click listener to the button
		mPickReminderTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				showDialog(REMINDER_TIME_DIALOG_ID);
			}
		});

		final Calendar c = Calendar.getInstance();
		mDueYear = c.get(Calendar.YEAR);
		mDueMonth = c.get(Calendar.MONTH);
		mDueDay = c.get(Calendar.DAY_OF_MONTH);

		final Button saveButton = (Button) findViewById(R.id.savebutton);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Task task = _generateTask();

				boolean nameBool = false;

				if (task.getName().length() > 0) {
					nameBool = true;
				} else {
					Toast.makeText(EditTaskView.this, "You must enter a name!",
							Toast.LENGTH_SHORT).show();
				}

				boolean dateBool = false;

				if ((reminderDateSet && reminderTimeSet)
						|| (!reminderDateSet && !reminderTimeSet)) {
					dateBool = true;
				} else if (reminderDateSet && !reminderTimeSet) {
					Toast.makeText(
							EditTaskView.this,
							"You must enter a reminder time if you enter a reminder date!",
							Toast.LENGTH_SHORT).show();
				} else if (!reminderDateSet && reminderTimeSet) {
					Toast.makeText(
							EditTaskView.this,
							"You must enter a reminder date if you enter a reminder time!",
							Toast.LENGTH_SHORT).show();
				}

				if (dateBool && nameBool) {
					data.putExtra("taskName", task.getName());
					data.putExtra("taskDescription", task.getDescription());
					data.putExtra("taskPriority", task.getPriority().getValue());
					if (dueDateSet) {
						data.putExtra("taskDueDate", task.getDueDate()
								.getTime());
					} else {
						data.putExtra("taskDueDate", Long.valueOf(-1));
					}

					if (reminderDateSet && reminderTimeSet) {
						data.putExtra("taskReminderDate", task
								.getReminderDate().getTime());
					} else {
						data.putExtra("taskReminderDate", Long.valueOf(-1));
					}
					data.putExtra("taskIsCompleted", task.isCompleted());
					setResult(RESULT_OK, data);
					finish();
				}

			}
		});

		final Button cancelButton = (Button) findViewById(R.id.cancelbutton);
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.priority);
		radioGroup.check(R.id.priority_3);
		data = getIntent();
		final Bundle extras = data.getExtras();
		if (extras != null) {
			_loadTask(extras);
		}
	}

	private Task _generateTask() {
		final String name = ((EditText) findViewById(R.id.edittaskname))
				.getText().toString();
		final String description = ((EditText) findViewById(R.id.edittaskdescription))
				.getText().toString();
		final int radioButtonId = ((RadioGroup) findViewById(R.id.priority))
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
		final IPriority priority = new Priority(priorityValue);
		final Date dueDate = dueDateSet ? new Date(mDueYear, mDueMonth, mDueDay)
				: null;
		final Date reminderDate = (reminderDateSet && reminderTimeSet) ? new Date(
				mReminderYear, mReminderMonth, mReminderDay, mReminderHour,
				mReminderMinute) : null;
		final boolean isCompleted = data.getBooleanExtra("taskIsCompleted",
				false);
		return new Task(name, description, priority, dueDate, reminderDate, 0,
				isCompleted);
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		switch (id) {
		case DUE_DATE_DIALOG_ID:
			final DatePickerDialog dueDatePicker = new DatePickerDialog(this,
					mDueDateSetListener, mDueYear, mDueMonth, mDueDay);
			dueDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Disable",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int which) {
							resetDueDate();
						}
					});
			dueDatePicker.setOnCancelListener(mOnCancelDueDateListener);
			return dueDatePicker;
		case REMINDER_DATE_DIALOG_ID:
			final DatePickerDialog reminderDatePicker = new DatePickerDialog(
					this, mReminderDateSetListener, mDueYear, mDueMonth,
					mDueDay);
			reminderDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
					"Disable", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int which) {
							resetReminderDate();
						}
					});
			reminderDatePicker
					.setOnCancelListener(mOnCancelReminderDateListener);
			return reminderDatePicker;
		case REMINDER_TIME_DIALOG_ID:
			final TimePickerDialog timePicker = new TimePickerDialog(this,
					mReminderTimeSetListener, mReminderHour, mReminderMinute,
					true);
			timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Disable",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int which) {
							resetReminderTime();
						}
					});
			timePicker.setOnCancelListener(mOnCancelReminderTimeListener);
			return timePicker;
		}
		return null;
	}

	private void resetDueDate() {
		dueDateSet = false;
		mDueYear = 0;
		mDueMonth = 0;
		mDueDay = 0;
		updateDueDateDisplay();
	}

	private void resetReminderDate() {
		reminderDateSet = false;
		mReminderYear = 0;
		mReminderMonth = 0;
		mReminderDay = 0;
		updateReminderDateDisplay();
	}

	private void resetReminderTime() {
		reminderTimeSet = false;
		mReminderHour = 0;
		mReminderMinute = 0;
		updateReminderTimeDisplay();
	}

	private void updateDueDateDisplay() {
		if (dueDateSet) {
			mPickDueDate.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(mDueYear).append("-").append(mDueMonth + 1)
					.append("-").append(mDueDay).append(" "));
		} else {
			mPickDueDate.setText("None");
		}
	}

	private void updateReminderDateDisplay() {
		if (reminderDateSet) {
			mPickReminderDate.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(mReminderYear).append("-")
					.append(mReminderMonth + 1).append("-")
					.append(mReminderDay).append(" "));
		} else {
			mPickReminderDate.setText("None");
		}
	}

	private void updateReminderTimeDisplay() {
		if (reminderTimeSet) {
			mPickReminderTime.setText(new StringBuilder()
					.append(pad(mReminderHour)).append(":")
					.append(pad(mReminderMinute)));
		} else {
			mPickReminderTime.setText("None");
		}
	}

	private static String pad(final int c) {
		if (c >= 10) {
			return String.valueOf(c);
		} else {
			return "0" + String.valueOf(c);
		}
	}
}