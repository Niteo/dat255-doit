package se.chalmers.doit.presentation.activities.implementation;

import se.chalmers.doit.core.implementation.Task;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class DefaultListView extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.defaultlistview);
        Task[] tasks = new Task[] { new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!") };
		TaskListAdapter adapter = new TaskListAdapter(this, tasks);
		setListAdapter(adapter);
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_SHORT)
				.show();

	}
}