package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.ExerciseBankFragment;
import com.cwru.fragments.ExerciseSequenceFragment;

public class WorkoutExerciseListing extends FragmentActivity {
	
	private TextView appTitleBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_exercise_listing);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvWorkoutExerciseListingAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
		
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
			//ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flWorkoutExerciseListingMainFrame, ebank, "exerciseBank");
			transaction.commit();
		}
	}
	
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(WorkoutExerciseListing.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};

}
