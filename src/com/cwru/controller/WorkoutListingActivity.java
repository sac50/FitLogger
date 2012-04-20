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
import com.cwru.fragments.WorkoutListingFragment.onReturnWorkoutListener;

/**
 * Activity that displays a list of workout names.
 * @author sacrilley
 *
 */
public class WorkoutListingActivity extends FragmentActivity implements onReturnWorkoutListener {
	/** Flag that gets set if this activity was started so that it would return a workout name */
	private boolean returnWorkout = false;
	/** TextView for the Application Title Bar that when clicked takes user to the Home Screenn */
	private TextView appTitleBar;
	
	@Override
	/**
	 * onCreate callback that sets the layout for the activity.  Determines if activity 
	 * was started to return a workout name or list all workouts.  Launches the workout listing
	 * fragment to the forefront.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_listing_activity);
		
		// Set Listener
		WorkoutListingFragment.setOnReturnWorkoutListener(this);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvWorkoutListingAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
		
		WorkoutListingFragment workouts = null;
		
		// Get any extras provided by the calling activity
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			// Activity was started to return a selected workout
			if (extras.containsKey("RETURN-WORKOUT")) {
				returnWorkout = true;
				workouts = new WorkoutListingFragment(returnWorkout);
			}
		}
		
		// If no flag set, create a blank workout listing fragment
		if (!returnWorkout) {
			workouts = new WorkoutListingFragment();
		}
		
		// Launch the created workout listing fragment to the main frame of the application layout
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flWorkoutListingMainFrame, workouts);
		transaction.commit();
	}
	
	@Override
	/**
	 * Code that implements the onReturnWorkoutListener interface.
	 * Finishes the activity and puts the workout name selected into the intent
	 */
	public void onReturnWorkoutListenerClick(String workoutName) {
		Log.d("Steve", "WORKOUT LISTING ACTIVITY ===============================>: " + workoutName);
		Intent intent = new Intent();
		intent.putExtra("RETURN-WORKOUT", workoutName);
		setResult(1, intent);
		super.finish();
	}
	
	/**
	 * Create intent to take user to the Home Screen.  The intent resets the application 
	 * back stack on click.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(WorkoutListingActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
}
