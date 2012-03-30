package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cwru.R;
import com.cwru.fragments.WorkoutListingFragment;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;

public class WorkoutWorkflow extends FragmentActivity implements onWorkoutListingClickListener {	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.workout_workflow);
		// Set Fragment Listeners
		WorkoutListingFragment.setOnWorkoutListingClickListener(this);
		
		WorkoutListingFragment workouts = new WorkoutListingFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flWorkoutWorkflowMainFrame, workouts);
		transaction.commit();
		Log.d("Steve", "Workout onCreate call");
		
	}

	@Override
	public void onWorkoutListingListenerClick(String workoutName) {
		Intent intent = new Intent(this, PerformWorkout.class);
		intent.putExtra("workoutName", workoutName);
		startActivity(intent);		
	}
	

}
