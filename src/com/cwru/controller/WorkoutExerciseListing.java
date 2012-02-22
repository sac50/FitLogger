package com.cwru.controller;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cwru.R;
import com.cwru.model.ExerciseBankFragment;
import com.cwru.model.ExerciseSequenceFragment;

public class WorkoutExerciseListing extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_exercise_listing);
		String workoutName = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			workoutName = extras.getString("WorkoutName");
		} else {
			/**
			 * TODO 
			 * What to do if workout name doesnt come across
			 */
		}
		
		Log.d("Steve", "Workout Name: " + workoutName);
		
		
		/* Tablet - Show split pane of exercise bank and exercise sequence */
		if (HomeScreen.isTablet) {
			ExerciseBankFragment ebank = new ExerciseBankFragment(workoutName);
			ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flWorkoutExerciseListingLeftFrame, ebank, "exerciseBank");
			transaction.add(R.id.flWorkoutExerciseListingRightFrame, esequence, "exerciseSequence");
			transaction.commit();
		}
		/* Phone - Show exercise bank and exercise sequence independently and linked to each other */
		else {
			ExerciseBankFragment ebank = new ExerciseBankFragment(workoutName);
			ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.llWorkoutExerciseListingContainer, esequence);
			transaction.commit();
		}
	}

}
