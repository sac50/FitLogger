package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.fragments.HistoryFragment;
import com.cwru.fragments.NotesFragment;
import com.cwru.fragments.WorkoutSummaryFragment;

public class CalendarActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		
		WorkoutSummaryFragment workoutSummary = new WorkoutSummaryFragment(this, 1, "2012/03/29");
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, workoutSummary);
		transaction.commit();
		
	}
}
