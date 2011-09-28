package se.chalmers.doit.presentation.activities.implementation;

import se.chalmers.doit.R;
import se.chalmers.doit.core.implementation.Task;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<Task> {
	private final Activity context;
	private final Task[] tasks;

	public TaskListAdapter(Activity context, Task[] tasks) {
		super(context, R.layout.task_list_item, tasks);
		this.context = context;
		this.tasks = tasks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;  
        if (view == null) {  
                LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();  
                view = inflater.inflate(R.layout.task_list_item, null);  
        }  
        Task task = tasks[position];  
        TextView topText = (TextView) view.findViewById(R.id.toptext);  
        TextView bottomText = (TextView) view.findViewById(R.id.bottomtext);  
        topText.setText(task.getName());  
        bottomText.setText(task.getDescription());  
        return view;  
	}
}