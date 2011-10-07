package se.chalmers.doit.presentation.activities.implementation;

import se.chalmers.doit.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.*;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends TabActivity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainview);
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, TaskViewer.class);
		spec = tabHost
				.newTabSpec("taskview")
				.setIndicator("Tasks", res.getDrawable(R.drawable.ic_menu_mark))
				.setContent(intent);
		tabHost.addTab(spec); // Do the same for the other tabs

		intent = new Intent().setClass(this, ListViewer.class);
		spec = tabHost
				.newTabSpec("listview")
				.setIndicator("Lists",
						res.getDrawable(R.drawable.ic_menu_agenda))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// Update a tab when changed to
		OnTabChangeListener tcl = new OnTabChangeListener() {
			@Override
			public void onTabChanged(final String tabId) {
				_updateTabs();
			}
		};
		tabHost.setOnTabChangedListener(tcl);
	}

	public void setActiveList(final String listName) {
		((TaskViewer) getLocalActivityManager().getActivity("taskview"))
				.setActiveList(listName);
		getTabHost().setCurrentTab(0);
	}

	private void _updateTabs() {
		((ListViewer) getLocalActivityManager().getActivity("listview"))
				.updateView();
		((TaskViewer) getLocalActivityManager().getActivity("taskview"))
				.updateView();
	}
}