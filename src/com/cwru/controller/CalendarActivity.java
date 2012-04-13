package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cwru.R;
import com.cwru.fragments.CalendarDayViewFragment;
import com.cwru.fragments.CalendarDayViewFragment.scheduleWorkoutListener;
import com.cwru.fragments.CalendarFragment;
import com.cwru.fragments.CalendarFragment.goToDayEventsListener;
import com.cwru.fragments.CalendarFragment.returnDateListener;
import com.cwru.fragments.CalendarScheduleWorkoutFragment;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;

public class CalendarActivity extends FragmentActivity implements returnDateListener, goToDayEventsListener, onWorkoutListingClickListener, scheduleWorkoutListener {
	private boolean returnDate = false;
	private boolean returnWorkout = false;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		
		// Set Listener
		CalendarFragment.setGetDateListener(this);
		CalendarFragment.setGotToDayEventsListener(this);
		CalendarDayViewFragment.setOnWorkoutListingClickListener(this);
		CalendarDayViewFragment.setScheduleWorkout(this);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.containsKey("RETURN-DATE")) {
				returnDate = true;
			}
		}
		
		CalendarFragment calendar = new CalendarFragment(this, returnDate);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, calendar);
		transaction.commit();
		
	}

	@Override
	public void returnSelectedDate(String dateSelected) {
		Intent intent = new Intent();
		intent.putExtra("DATE-SELECTED", dateSelected);
		setResult(1, intent);
		super.finish();
	}

	@Override
	public void goToDayEvents(String date) {
		// TODO Auto-generated method stub
		CalendarDayViewFragment dayView = new CalendarDayViewFragment(this, date);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, dayView);
		Log.d("Steve", "Go To Days Events");
		transaction.commit();
		
	}

	@Override
	public void onWorkoutListingListenerClick(String workoutName) {
		Intent intent = new Intent(this, PerformWorkout.class);
		intent.putExtra("workoutName", workoutName);
		startActivity(intent);		
	}

	@Override
	public void scheduleWorkout(String date) {
		// TODO Auto-generated method stub
		CalendarScheduleWorkoutFragment scheduleWorkout = new CalendarScheduleWorkoutFragment(this, date);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, scheduleWorkout);
		transaction.commit();
		
	}

}
