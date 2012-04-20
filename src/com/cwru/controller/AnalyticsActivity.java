package com.cwru.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.cwru.R;

public class AnalyticsActivity extends TabActivity {
	private TabHost tabHost; // Activity TabHost
	private TabHost.TabSpec tabSpec; // TabSpec to use for all tabs
	private String lastTabTag;
	private TextView appTitleBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analytics_module);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvAnalyticsModuleAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
		
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
	
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AnalyticsActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
}