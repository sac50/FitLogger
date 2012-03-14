package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cwru.R;
import com.cwru.fragments.WorkoutListingFragment;

public class WorkoutWorkflow extends FragmentActivity {	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.workout_workflow);
		WorkoutListingFragment workouts = new WorkoutListingFragment(WorkoutListingFragment.WORKOUT_WORKFLOW_LIST);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flWorkoutWorkflowMainFrame, workouts);
		transaction.commit();
		Log.d("Steve", "Workout onCreate call");
		
	}
	

}
