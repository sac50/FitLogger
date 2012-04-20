package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.CalendarDayViewFragment;
import com.cwru.fragments.CalendarDayViewFragment.scheduleWorkoutListener;
import com.cwru.fragments.CalendarFragment;
import com.cwru.fragments.CalendarFragment.goToDayEventsListener;
import com.cwru.fragments.CalendarFragment.returnDateListener;
import com.cwru.fragments.CalendarScheduleWorkoutFragment;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;

/**
 * Activity to implement a calendar.  Creates a main frame from which calendar related fragments
 * are loaded to.
 * @author sacrilley
 *
 */
public class CalendarActivity extends FragmentActivity implements returnDateListener, goToDayEventsListener, onWorkoutListingClickListener, scheduleWorkoutListener {
	private boolean returnDate = false;
	private boolean returnWorkout = false;
	private TextView appTitleBar;

	@Override
	/**
	 * Sets the listeneres for the activity and loads the calendar fragment
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		
		// Set Listener
		CalendarFragment.setGetDateListener(this);
		CalendarFragment.setGotToDayEventsListener(this);
		CalendarDayViewFragment.setOnWorkoutListingClickListener(this);
		CalendarDayViewFragment.setScheduleWorkout(this);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvCalendarAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
		
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
	/**
	 * Finishes the activity and puts the date selected in the intent under key DATE-SELECTED
	 */
	public void returnSelectedDate(String dateSelected) {
		Intent intent = new Intent();
		intent.putExtra("DATE-SELECTED", dateSelected);
		setResult(1, intent);
		super.finish();
	}

	@Override
	/**
	 * Replaces main frame with calendar day view fragment to show day specific events
	 */
	public void goToDayEvents(String date) {
		// TODO Auto-generated method stub
		CalendarDayViewFragment dayView = new CalendarDayViewFragment(this, date);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, dayView);
		transaction.addToBackStack(null);
		Log.d("Steve", "Go To Days Events");
		transaction.commit();
		
	}

	@Override
	/**
	 * Takes a workout selected and launches the perform workout intent to allow user to 
	 * start workout.  Launches the Workout Workflow Module
	 */
	public void onWorkoutListingListenerClick(String workoutName) {
		Intent intent = new Intent(this, PerformWorkout.class);
		intent.putExtra("workoutName", workoutName);
		startActivity(intent);		
	}

	@Override
	/**
	 * Replaces main frame with fragment to allowing workout scheduling 
	 */
	public void scheduleWorkout(String date) {
		// TODO Auto-generated method stub
		CalendarScheduleWorkoutFragment scheduleWorkout = new CalendarScheduleWorkoutFragment(this, date);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, scheduleWorkout);
		transaction.addToBackStack(null);
		transaction.commit();
		
	}
	
	/**
	 * Top title bar on click event handler.  Takes user back to home screen.  Resets back stack to clear history.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CalendarActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};

}
