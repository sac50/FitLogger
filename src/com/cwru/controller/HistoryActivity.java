package com.cwru.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.cwru.R;

public class HistoryActivity extends TabActivity {
	private TabHost tabHost; // Activity TabHost
	private TabHost.TabSpec tabSpec; // TabSpec to use for all tabs
	private String lastTabTag;
	private TextView appTitleBar;
	
	
	@Override
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
	
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(HistoryActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};

}
