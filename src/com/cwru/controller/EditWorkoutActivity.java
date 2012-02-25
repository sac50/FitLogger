package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.model.EditWorkoutInformation;
import com.cwru.model.WorkoutListingFragment;

public class EditWorkoutActivity extends FragmentActivity  {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.edit_workout_information);
		
		// Tablet
		if (HomeScreen.isTablet) {
			WorkoutListingFragment workoutListings = new WorkoutListingFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditWorkoutInformationLeftFrame, workoutListings);
			transaction.commit();
		} 
		// Phone
		else {
			
		}
	}
}
