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

public class WorkoutListingActivity extends FragmentActivity implements onReturnWorkoutListener {
	private boolean returnWorkout = false;
	private TextView appTitleBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_listing_activity);
		
		// Set Listener
		WorkoutListingFragment.setOnReturnWorkoutListener(this);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvWorkoutListingAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
		
		WorkoutListingFragment workouts = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.containsKey("RETURN-WORKOUT")) {
				returnWorkout = true;
				workouts = new WorkoutListingFragment(returnWorkout);

			}
		}
		
		if (!returnWorkout) {
			workouts = new WorkoutListingFragment();
		}
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flWorkoutListingMainFrame, workouts);
		transaction.commit();
		
	}

	@Override
	public void onReturnWorkoutListenerClick(String workoutName) {
		Log.d("Steve", "WORKOUT LISTING ACTIVITY ===============================>: " + workoutName);
		Intent intent = new Intent();
		intent.putExtra("RETURN-WORKOUT", workoutName);
		setResult(1, intent);
		super.finish();
	}
	
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(WorkoutListingActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
}
