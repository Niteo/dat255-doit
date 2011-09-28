package se.chalmers.doit.presentation.activities.implementation;

import se.chalmers.doit.R;
import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Task;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DefaultListView extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ITask[] tasks = new Task[] { new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!"), new Task("Wash the dishes", "Use the Yes washing-up liquid"), new Task("Do homework", "All chapters!") };
		TaskListAdapter adapter = new TaskListAdapter(this, tasks);
		setListAdapter(adapter);
		setContentView(R.layout.defaultlistview);
		
		final EditText edittext = (EditText) findViewById(R.id.quickaddedittext);
		Button quickAddButton = (Button) findViewById(R.id.quickaddbutton);
		quickAddButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(DefaultListView.this, edittext.getText(), Toast.LENGTH_SHORT).show();	
			}
		});
		
	    edittext.setOnKeyListener(new OnKeyListener() {
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            // If the event is a key-down event on the "enter" button
	            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
	                (keyCode == KeyEvent.KEYCODE_ENTER)) {
	              // Perform action on key press
	              Toast.makeText(DefaultListView.this, edittext.getText(), Toast.LENGTH_SHORT).show();
	              return true;
	            }
	            return false;
	        }
	    });
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