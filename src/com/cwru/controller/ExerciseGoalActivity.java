package com.cwru.controller;

import com.cwru.R;
import com.cwru.fragments.ExerciseGoalBankFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ExerciseGoalActivity extends FragmentActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.exercise_goal_information);
		
		ExerciseGoalBankFragment goalBank = new ExerciseGoalBankFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		// Tablet
		if (HomeScreen.isTablet) {
			transaction.add(R.id.flExerciseGoalLeftFrame, goalBank);
		
		// Phone
		} else {
			transaction.add(R.id.flExerciseGoalMainFrame, goalBank);
		}
		
		transaction.commit();
	}
}