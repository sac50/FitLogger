package com.cwru.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.cwru.R;


public class CreateEditTabActivity  extends TabActivity {
	
	private TabHost tabHost; // Activity TabHost
	private TabHost.TabSpec tabSpec; // TabSpec to use for all tabs
	private String lastTabTag;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_exercise_module);
		
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

	
}
