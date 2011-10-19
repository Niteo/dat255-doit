package se.chalmers.doit.presentation.activities.implementation;

import se.chalmers.doit.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Help extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		TextView tv = (TextView) findViewById(R.id.helpTextView);
		tv.setText(Html.fromHtml(getString(R.string.help)));
	}
}