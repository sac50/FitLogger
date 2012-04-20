package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.ExerciseListingFragment;
import com.cwru.fragments.ExerciseListingFragment.onExerciseListingClickListener;
import com.cwru.fragments.ExerciseSummaryFragment;

/**
 * Activity that is housed under the exercise tab under the history module.  
 * Provides user with list of all exercises and allows them to be clicked and provides the history pertaining to the exercise
 * @author sacrilley
 *
 */
public class ExerciseHistory extends FragmentActivity implements onExerciseListingClickListener {
	
	@Override
	/**
	 * onCreate Callback.  Sets listeners and launches the exercise listing framgent
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set Listener
		ExerciseListingFragment.setOnExerciseListingClickListener(this);
		
		setContentView(R.layout.exercise_history);
		
		// Tablet
		if (HomeScreen.isTablet) {
			
		} 
		// Phone
		else {
			ExerciseListingFragment exerciseListing = new ExerciseListingFragment();
			exerciseListing.setRetainInstance(true);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.addToBackStack(null);
			transaction.replace(R.id.flExerciseHistoryMainFrame, exerciseListing, "workoutListings");
			transaction.commit();
		}
	}

	@Override
	/** 
	 * Launches the exercise summary into the main frame after an exercise has been selected from the exercise listing fragment
	 */
	public void onExerciseListingListenerClick(int exerciseId) {
		ExerciseSummaryFragment exerciseSummary = new ExerciseSummaryFragment(this, exerciseId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(null);
		transaction.replace(R.id.flExerciseHistoryMainFrame, exerciseSummary);
		transaction.commit();
	}

}
