package com.cwru.controller;

import com.cwru.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class AnalyticsActivity extends TabActivity {
	private TabHost tabHost; // Activity TabHost
	private TabHost.TabSpec tabSpec; // TabSpec to use for all tabs
	private String lastTabTag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analytics_module);
		
		// Get Tab Host
		tabHost = getTabHost();
		
		// Create Workout Analytics Tab
		Intent intent = new Intent().setClass(this, WorkoutAnalyticsActivity.class);
		tabSpec = tabHost.newTabSpec("workoutAnalytics").setIndicator("Workout Analytics").setContent(intent);
		tabHost.addTab(tabSpec);
		
		// Create Exercise Analytics Tab
		intent = new Intent().setClass(this, ExerciseAnalyticsActivity.class);
		tabSpec = tabHost.newTabSpec("exerciseAnalytics").setIndicator("Exercise Analytics").setContent(intent);
		tabHost.addTab(tabSpec);
		
		// Set Current Tab
		// Default to Last chosen tab or if none chosen, go to workout analytics tab
		if (lastTabTag == null) { 
			tabHost.setCurrentTabByTag("workoutAnalytics");
		}
		tabHost.setCurrentTabByTag(lastTabTag);
	}
}