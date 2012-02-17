package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.model.ExerciseBankFragment;
import com.cwru.model.ExerciseSequenceFragment;

public class WorkoutExerciseListing extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* Tablet - Show split pane of exercise bank and exercise sequence */
		if (HomeScreen.isTablet) {
			ExerciseBankFragment ebank = new ExerciseBankFragment();
			ExerciseSequenceFragment esequence = new ExerciseSequenceFragment();
			
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flWorkoutExerciseListingLeftFrame, ebank);
			transaction.add(R.id.flWorkoutExerciseListingRightFrame, esequence);
			transaction.commit();			
		}
		/* Phone - Show exercise bank and exercise sequence independently and linked to each other */
		else {
			
		}
	}
}
