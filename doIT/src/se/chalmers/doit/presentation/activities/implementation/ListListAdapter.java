package se.chalmers.doit.presentation.activities.implementation;

import java.util.ArrayList;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITaskCollection;
import android.app.Activity;
import android.view.*;
import android.widget.*;

public class ListListAdapter extends ArrayAdapter<ITaskCollection> {
	private final ArrayList<ITaskCollection> collections;
	private final Activity context;

	public ListListAdapter(final Activity context,
			final ArrayList<ITaskCollection> collections) {
		super(context, R.layout.task_list_item, collections);
		this.context = context;
		this.collections = collections;

	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(R.layout.task_list_item, null);
		}
		ITaskCollection item = collections.get(position);
		TextView topText = (TextView) view.findViewById(R.id.toptext);
		TextView bottomText = (TextView) view.findViewById(R.id.bottomtext);
		topText.setText(item.getName());
		bottomText.setText(item.getTasks().size() + " tasks");

		return view;
	}
}