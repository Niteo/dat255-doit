package se.chalmers.doit.presentation.activities.implementation;

import se.chalmers.doit.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
 
 public class Preferences extends PreferenceActivity {
     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);  
 
         addPreferencesFromResource(R.xml.preferences);
         setContentView(R.layout.preferences_layout);
     }
 }