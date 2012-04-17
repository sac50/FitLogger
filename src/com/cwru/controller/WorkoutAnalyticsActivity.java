package com.cwru.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.utils.GraphBuilder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class WorkoutAnalyticsActivity extends Activity {
	DbAdapter mDbHelper;
	
	WebView view;
	RadioGroup group;
	RadioButton month;
	RadioButton threeMonths;
	RadioButton year;
	
	String monthHTML;
	String threeMonthHTML;
	String yearHTML;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DbAdapter(this);
		
		setContentView(R.layout.workout_analytics);
		
		view = (WebView) findViewById(R.id.wvWorkoutAnalytics);
		view.getSettings().setJavaScriptEnabled(true);
		view.setBackgroundColor(0xFF000000);
				
		group = (RadioGroup) findViewById(R.id.rgWorkoutAnalyticsSwitches);
		group.setOnCheckedChangeListener(groupListener);
		
		month = (RadioButton) findViewById(R.id.rbMonth);
		threeMonths = (RadioButton) findViewById(R.id.rbThreeMonths);
		year = (RadioButton) findViewById(R.id.rbYear);
		
//		month.setOnClickListener(toggleListener);
//		threeMonths.setOnClickListener(toggleListener);
//		year.setOnClickListener(toggleListener);
		
//		monthMap = mDbHelper.getWorkoutResultsForPastWeeks(4);
//		threeMonthMap = mDbHelper.getWorkResultsForPastWeeks(12);
//		yearMap = mDbHelper.getWorkoutResultsForPastWeeks(52);
		
		HashMap<String, Integer> monthMap = new LinkedHashMap<String, Integer>();
		HashMap<String, Integer> threeMonthMap = new LinkedHashMap<String, Integer>();
		HashMap<String, Integer> yearMap = new LinkedHashMap<String, Integer>();
		
		for (int i = 1; i < 60; i++) {
			monthMap.put(Integer.toString(i), i%5);
			threeMonthMap.put(Integer.toString(i), i%10);
			yearMap.put(Integer.toString(i), i%15);
		}
		
		GraphBuilder gb = new GraphBuilder();
		
		monthHTML = gb.buildHTML(monthMap, "Workout frequency by week for the past month");
		threeMonthHTML = gb.buildHTML(threeMonthMap, "Workout frequency by week for the past three months.");
		yearHTML = gb.buildHTML(yearMap, "Workout frequency by month for the past year.");
		
		if (HomeScreen.isTablet) {
			view.loadUrl("file:///android_asset/js/loading_tablet.html");
		} else {
			view.loadUrl("file:///android_asset/js/loading_phone.html");
		}
//		month.setChecked(true);
		
//		view.loadDataWithBaseURL("file:///android_asset/js/", monthHTML, "text/html", "utf-8", null);
	}
	
	RadioGroup.OnCheckedChangeListener groupListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			
			case R.id.rbMonth:
				view.loadDataWithBaseURL("file:///android_asset/js/", monthHTML, "text/html", "utf-8", null);
				break;
			case R.id.rbThreeMonths:
				view.loadDataWithBaseURL("file:///android_asset/js/", threeMonthHTML, "text/html", "utf-8", null);
				break;
			case R.id.rbYear:
				view.loadDataWithBaseURL("file:///android_asset/js/", yearHTML, "text/html", "utf-8", null);
				break;
			}
		}
	};
}