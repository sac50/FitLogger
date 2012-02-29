package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cwru.R;
import com.cwru.model.WorkoutListingFragment;

public class EditWorkoutActivity extends FragmentActivity  {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.edit_workout_information);
		
		// Tablet
		if (HomeScreen.isTablet) {
			WorkoutListingFragment workoutListings = new WorkoutListingFragment();
			workoutListings.setRetainInstance(true);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditWorkoutInformationLeftFrame, workoutListings);
			transaction.commit();
		} 
		// Phone
		else {
			WorkoutListingFragment workoutListings = new WorkoutListingFragment();
			workoutListings.setRetainInstance(true);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flEditWorkoutInformationMainFrame, workoutListings, "workoutListings");
			transaction.commit();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d("STEVE", "Edit Workout Activity onPause() Called");
		
	}
}
