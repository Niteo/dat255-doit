package se.chalmers.doit.presentation.activities.implementation;

import java.util.ArrayList;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITask;
import android.app.Activity;
import android.graphics.*;
import android.view.*;
import android.widget.*;

public class TaskListAdapter extends ArrayAdapter<ITask> {
	private final Activity context;
	private final ArrayList<ITask> tasks;

	public TaskListAdapter(final Activity context, final ArrayList<ITask> tasks) {
		super(context, R.layout.task_list_item, tasks);
		this.context = context;
		this.tasks = tasks;

	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(R.layout.task_list_item, null);
		}
		ITask task = tasks.get(position);
		TextView topText = (TextView) view.findViewById(R.id.toptext);
		TextView bottomText = (TextView) view.findViewById(R.id.bottomtext);
		topText.setText(task.getName());
		bottomText.setText(task.getDescription());

		if (task.isCompleted()) {
			// Strikethrough text
			topText.setPaintFlags(topText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			topText.setTextColor(Color.GRAY);
			bottomText.setPaintFlags(bottomText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			bottomText.setTextColor(Color.GRAY);
		} else {
			// Remove strikethough flag
			topText.setPaintFlags(topText.getPaintFlags()
					& (~Paint.STRIKE_THRU_TEXT_FLAG));
			topText.setTextColor(Color.WHITE);
			bottomText.setPaintFlags(bottomText.getPaintFlags()
					& (~Paint.STRIKE_THRU_TEXT_FLAG));
			bottomText.setTextColor(Color.WHITE);
		}
		return view;
	}
}