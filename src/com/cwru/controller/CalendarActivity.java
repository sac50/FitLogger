package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.fragments.CalendarFragment;

public class CalendarActivity extends FragmentActivity {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		
		CalendarFragment calendar = new CalendarFragment(this);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, calendar);
		transaction.commit();
		
	}
}
