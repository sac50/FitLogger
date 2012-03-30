package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.fragments.ExerciseSummaryFragment;

public class CalendarActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		
		ExerciseSummaryFragment exerciseSummary = new ExerciseSummaryFragment(this, 4);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, exerciseSummary);
		transaction.commit();
		
	}
}
