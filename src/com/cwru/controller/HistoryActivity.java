package com.cwru.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.cwru.R;

/**
 * Activity to display the available history tabs to the user.
 * @author sacrilley
 *
 */
public class HistoryActivity extends TabActivity {
	private TabHost tabHost; // Activity TabHost
	private TabHost.TabSpec tabSpec; // TabSpec to use for all tabs
	private String lastTabTag;
	private TextView appTitleBar;
	
	
	@Override
	/**
	 * onCreate Callback.  Sets up the tabs and sets workout history as the default selected tab
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_exercise_module);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvWorkoutExerciseModuleAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
		
		// Get Tab Host
		tabHost = getTabHost();
		
		// Create Workout Tab
		Intent intent = new Intent().setClass(this, WorkoutHistory.class);
		tabSpec = tabHost.newTabSpec("workoutHistory").setIndicator("Workout History").setContent(intent);
		tabHost.addTab(tabSpec);
	
		// Edit Workout Tab
		intent = new Intent().setClass(this, ExerciseHistory.class);
		tabSpec = tabHost.newTabSpec("exerciseHistory").setIndicator("Exercise History").setContent(intent);
		tabHost.addTab(tabSpec);
		
		// Set current Tab
		// Default to Last chosen tab or if none chosen, go to create workout tab
		if (lastTabTag == null) { 
			tabHost.setCurrentTabByTag("workoutHistory");
		}
		tabHost.setCurrentTabByTag(lastTabTag);
		
	}
	
	/**
	 * Top title bar on click event handler.  Takes user back to home screen.  Resets back stack to clear history.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(HistoryActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};

}
