package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.WorkoutDateListFragment;
import com.cwru.fragments.WorkoutDateListFragment.onWorkoutDateListingClickListener;
import com.cwru.fragments.WorkoutListingFragment;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;
import com.cwru.fragments.WorkoutSummaryFragment;

/**
 * Activity that implements the Workout History Tab of the History Module.
 * Provides the user with a list of workouts, then list of dates and then the summary 
 * of the selected workout on the particular date
 * @author sacrilley
 *
 */
public class WorkoutHistory extends FragmentActivity implements onWorkoutListingClickListener, onWorkoutDateListingClickListener {
	
	/**
	 * onCreate Callback for the activity.
	 * Loads the Workout Listing fragment into the main view
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set Listener
		WorkoutListingFragment.setOnWorkoutListingClickListener(this);
		WorkoutDateListFragment.setOnWorkoutDateListingClickListener(this);
		
		setContentView(R.layout.workout_history);
		
		// Tablet
		if (HomeScreen.isTablet) {
			
		} 
		// Phone
		else {
			// Create fragment to show listing of workouts and put in in main frame of layout
			WorkoutListingFragment workoutListings = new WorkoutListingFragment();
			// Add fragment to the back stack
			workoutListings.setRetainInstance(true);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flWorkoutHistoryMainFrame, workoutListings, "workoutListings");
			transaction.commit();
		}
	}

	@Override
	/**
	 * Code to implement the onWorkoutListingClickListener interface.
	 * Takes in the workout name provided and launches a fragment to provide 
	 * the user with a list of all dates that the workout was performed.
	 */
	public void onWorkoutListingListenerClick(String workoutName) {
		WorkoutDateListFragment workoutDateList = new WorkoutDateListFragment(this, workoutName);
		workoutDateList.setRetainInstance(true);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(null);
		transaction.replace(R.id.flWorkoutHistoryMainFrame, workoutDateList);
		transaction.commit();
	}

	@Override
	/**
	 * Code to implement the onWorkoutDateListingListener interface.
	 * Takes in a workoutid and date and launches fragment to show the summary 
	 * that displays the results of the workout
	 */
	public void onWorkoutDateListingListenerClick(int workoutId, String date) {
		WorkoutSummaryFragment workoutSummary = new WorkoutSummaryFragment(this, workoutId, date);
		workoutSummary.setRetainInstance(true);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(null);
		transaction.replace(R.id.flWorkoutHistoryMainFrame, workoutSummary);
		transaction.commit();
	}

}
