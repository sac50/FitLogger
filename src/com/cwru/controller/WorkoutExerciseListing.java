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

/**
 * Activity that displays all exercises in a list view to the user.
 * @author sacrilley
 *
 */
public class WorkoutExerciseListing extends FragmentActivity {
	/** TextView for the Application Title Bar that when clicked takes user to the Home Screenn */
	private TextView appTitleBar;

	@Override
	/**
	 * onCreate Callback.  Sets the layout and all listeners.  Loads the Exercise Bank
	 * Fragment and puts it in the main frame
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_exercise_listing);
		
		// Get the Title bar from layout
		appTitleBar = (TextView) this.findViewById(R.id.tvWorkoutExerciseListingAppTitleBar);
		// Set title bar listener
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
	
	/**
	 * Listener to direct user to home screen.  Resets application back stack on click.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(WorkoutExerciseListing.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};

}
