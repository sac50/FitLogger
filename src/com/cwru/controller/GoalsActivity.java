package com.cwru.controller;

import com.cwru.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class GoalsActivity extends TabActivity {
	private TabHost tabHost; // Activity TabHost
	private TabHost.TabSpec tabSpec; // TabSpec to use for all tabs
	private String lastTabTag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goals_module);
		
		// Get Tab Host
		tabHost = getTabHost();
		
		//Create Exercise Goals tab
		Intent intent = new Intent().setClass(this, ExerciseGoalActivity.class);
		tabSpec = tabHost.newTabSpec("exerciseGoals").setIndicator("Exercise Goals").setContent(intent);
		tabHost.addTab(tabSpec);
		
		//Create Body Goals tab
		intent = new Intent().setClass(this, BodyGoalActivity.class);
		tabSpec = tabHost.newTabSpec("bodyGoals").setIndicator("Body Goals").setContent(intent);
		tabHost.addTab(tabSpec);
		
		//Create Custom Goals tab
		intent = new Intent().setClass(this, CustomGoalActivity.class);
		tabSpec = tabHost.newTabSpec("customGoals").setIndicator("Custom Goals").setContent(intent);
		tabHost.addTab(tabSpec);
		
		// Set current Tab
		// Default to Last chosen tab or if none chosen, go to create exercise goals tab
		if (lastTabTag == null) { 
			tabHost.setCurrentTabByTag("exerciseGoals");
		}
		tabHost.setCurrentTabByTag(lastTabTag);
	}
}