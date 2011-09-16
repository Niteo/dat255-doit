package se.chalmers.doit.presentation.activities.implementation;

import se.chalmers.doit.R;
import android.app.Activity;
import android.os.Bundle;

public class DefaultListView extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defaultlistview);
    }
}