package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.ExerciseSummaryFragment;

/**
 * Activity to display exercise history
 * @author sacrilley
 *
 */
public class ExerciseHistoryActivity extends FragmentActivity {
	private int exerciseId;
	private TextView appTitleBar;
	
	@Override
	/**
	 * onCreate Callback.  Displays history for exercise.  The selected exercise is determined by id that is passed into the intent with key EXERCISE-ID
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_exercise_activity);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvHistoryExerciseAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
	
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
	
	/**
	 * Top title bar on click event handler.  Takes user back to home screen.  Resets back stack to clear history.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ExerciseHistoryActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
}
