package com.cwru.controller;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.model.Exercise;
import com.cwru.model.WorkoutWorkflowSetFragment;

public class WorkoutWorkflow extends FragmentActivity {
	
	ArrayList<Exercise> exercisesForWorkout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.workout_workflow);
		
		// Tablet
		if (HomeScreen.isTablet) {
			
		}		
		// Phone
		else {
			WorkoutWorkflowSetFragment setFragment = new WorkoutWorkflowSetFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flWorkoutWorkflowMainFrame, setFragment);
			transaction.commit();
		}
		
	}
}
