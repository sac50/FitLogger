package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.WorkoutListingFragment;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;
/**
 * Activity that holds the Workout workflow module.  This module allows
 * the user to execute a workout and record the data from it.  
 * @author sacrilley
 *
 */
public class WorkoutWorkflow extends FragmentActivity implements onWorkoutListingClickListener {	
	/** TextView for the Application Title Bar that when clicked takes user to the Home Screenn */
	private TextView appTitleBar;
	
	@Override
	/** 
	 * onCreate Callback for Activity.
	 * Sets the Layout and loads the WorkoutListingFragment into the forefront.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.workout_workflow);
		// Set Fragment Listeners
		WorkoutListingFragment.setOnWorkoutListingClickListener(this);
		
		// Get Title Bar from Layout
		appTitleBar = (TextView) this.findViewById(R.id.tvWorkoutWorkflowAppTitleBar);
		// Set listener to send user to home page when text view clicked
		appTitleBar.setOnClickListener(goHomeListener);
		
		// Load WorkoutListing Fragment into the forefront
		WorkoutListingFragment workouts = new WorkoutListingFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flWorkoutWorkflowMainFrame, workouts);
		transaction.commit();
	}
	
	/**
	 * Create intent to take user to the Home Screen.  The intent resets the application 
	 * back stack on click.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(WorkoutWorkflow.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};

	@Override
	/**
	 * Listener that when a workout list entry is clicked, it takes the user to the screen
	 * to start performing the workout and the exercises belonging to it
	 */
	public void onWorkoutListingListenerClick(String workoutName) {
		Intent intent = new Intent(this, PerformWorkout.class);
		intent.putExtra("workoutName", workoutName);
		startActivity(intent);		
	}
}
