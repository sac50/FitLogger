package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.fragments.WorkoutDateListFragment;
import com.cwru.fragments.WorkoutDateListFragment.onWorkoutDateListingClickListener;
import com.cwru.fragments.WorkoutListingFragment;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;
import com.cwru.fragments.WorkoutSummaryFragment;

public class WorkoutHistory extends FragmentActivity implements onWorkoutListingClickListener, onWorkoutDateListingClickListener {
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
			WorkoutListingFragment workoutListings = new WorkoutListingFragment();
			workoutListings.setRetainInstance(true);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flWorkoutHistoryMainFrame, workoutListings, "workoutListings");
			transaction.commit();
		}
	}

	@Override
	public void onWorkoutListingListenerClick(String workoutName) {
		WorkoutDateListFragment workoutDateList = new WorkoutDateListFragment(this, workoutName);
		workoutDateList.setRetainInstance(true);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(null);
		transaction.replace(R.id.flWorkoutHistoryMainFrame, workoutDateList);
		transaction.commit();
	}

	@Override
	public void onWorkoutDateListingListenerClick(int workoutId, String date) {
		WorkoutSummaryFragment workoutSummary = new WorkoutSummaryFragment(this, workoutId, date);
		workoutSummary.setRetainInstance(true);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(null);
		transaction.replace(R.id.flWorkoutHistoryMainFrame, workoutSummary);
		transaction.commit();
	}
}
