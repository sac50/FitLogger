package com.cwru.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.cwru.R;


/**
 * Activity to house the Create Edit Workouts and Exercises Module.
 * Implements a Tab Layout to allow each to be done.  Default tab is create workout.
 * @author sacrilley
 *
 */
public class CreateEditTabActivity  extends TabActivity {
	
	private TabHost tabHost; // Activity TabHost
	private TabHost.TabSpec tabSpec; // TabSpec to use for all tabs
	private String lastTabTag;
	private TextView appTitleBar;
	
	
	
	@Override
	/**
	 * Sets the layout and intents to be executed when each tab is clicked
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_exercise_module);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvWorkoutExerciseModuleAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
		
		// Get Tab Host
		tabHost = getTabHost();
		
		// Create Workout Tab
		Intent intent = new Intent().setClass(this, CreateWorkoutActivity.class);
		tabSpec = tabHost.newTabSpec("createWorkout").setIndicator("Create Workout").setContent(intent);
		tabHost.addTab(tabSpec);
	
		// Edit Workout Tab
		intent = new Intent().setClass(this, EditWorkoutActivity.class);
		tabSpec = tabHost.newTabSpec("editWorkout").setIndicator("Edit Workout").setContent(intent);
		tabHost.addTab(tabSpec);
		
		// Create Exercises Tab
		intent = new Intent().setClass(this, CreateExerciseActivity.class);
		tabSpec = tabHost.newTabSpec("createExercise").setIndicator("Create Exercise").setContent(intent);
		tabHost.addTab(tabSpec);
		
		// Edit Exercises Tab
		intent = new Intent().setClass(this, EditExerciseActivity.class);
		tabSpec = tabHost.newTabSpec("editExercise").setIndicator("Edit Exercise").setContent(intent);
		tabHost.addTab(tabSpec);
		
		// Set current Tab
		// Default to Last chosen tab or if none chosen, go to create workout tab
		if (lastTabTag == null) { 
			tabHost.setCurrentTabByTag("createWorkout");
		}
		tabHost.setCurrentTabByTag(lastTabTag);
		
	}

	/**
	 * Top title bar on click event handler.  Takes user back to home screen.  Resets back stack to clear history.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CreateEditTabActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
}
