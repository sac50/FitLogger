package com.cwru.controller;

import com.cwru.dao.DbAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class AnalyticsActivity extends FragmentActivity {
	DbAdapter mDbHelper;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (HomeScreen.isTablet) {
			
		} else {
			
		}
	}
}