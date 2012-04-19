package com.cwru.controller;

import com.cwru.R;
import com.cwru.fragments.ExerciseSummaryFragment;
import com.cwru.fragments.NotesFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ExerciseHistoryActivity extends FragmentActivity {
	private int exerciseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_exercise_activity);
	
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.containsKey("EXERCISE-ID")) {
				exerciseId = extras.getInt("EXERCISE-ID");
			}
		}
		
		ExerciseSummaryFragment exerciseSummary = new ExerciseSummaryFragment(this, exerciseId); 
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flHistoryExerciseMainFrame, exerciseSummary);
		transaction.commit();	
	}
}
