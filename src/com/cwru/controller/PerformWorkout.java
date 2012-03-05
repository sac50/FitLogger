package com.cwru.controller;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.WorkoutSetFragment;
import com.cwru.model.WorkoutWorkflowCountUpTimerFragment;

/**
 * 
 * @author sacrilley
 * 
 * Use fragments to alternate between different exercises for the workout
 * so we'll have set fragment, cardio fragment and interval fragment
 *
 */
public class PerformWorkout extends FragmentActivity {
	private ArrayList<Exercise> exercisesForWorkout;
	private DbAdapter mDbHelper;
	private Exercise currentExercise;
	private int exerciseCounter;
	private Button btnPrevious; 
	private Button btnNext;
	private TextView tvPercentDone;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.perform_workout);
		String workoutName = (String) this.getIntent().getExtras().get("workoutName");
		mDbHelper = new DbAdapter(this);
		getExercisesForWorkout(workoutName);
		exerciseCounter = 0;
		btnPrevious = (Button) this.findViewById(R.id.btnPerformWorkoutPrev);
		btnNext = (Button) this.findViewById(R.id.btnPerformWorkoutNext);
		tvPercentDone = (TextView) this.findViewById(R.id.tvPerformWorkoutPercentageDone);
		int percentage = exerciseCounter / exercisesForWorkout.size();
		tvPercentDone.setText(percentage + " % Workout Complete");
		launchExercise();

	}
	
	private void launchExercise() {
		if (exercisesForWorkout.size() < 0) {
			/** 
			 * TODO 
			 * MAKE THIS SHOW THAT WORKOUT HAS NO EXERCISES
			 */
			Log.d("Steve", "DID NOT LAUNCH EXERCISE");
			return;
		}
		
		Exercise exerciseToLaunch = exercisesForWorkout.get(exerciseCounter);
		String type = exerciseToLaunch.getType();
		int sets = exerciseToLaunch.getSets();
		Log.d("STEVE", "SETS: " + sets);
		Log.d("Steve", "Did Launch: " + type);

		/** TODO 
		 * Change this to get values from the resource file
		 */
		// Is a set based exercise
		if (exerciseToLaunch.getSets() != 0) {
			Log.d("Steve", "-----------------------------------------------------");
			WorkoutSetFragment workoutSet = new WorkoutSetFragment(exerciseToLaunch, this);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flPerformWorkoutMainFrame, workoutSet);
			transaction.commit();
		} 
		// Countdown time
		if (exerciseToLaunch.getIsCountdown()) {
			//WorkoutWorkflowCountDownTimerFragment workoutTimer = new WorkoutWorkflowCountDownTimerFragment(exerciseToLaunch, this);
			WorkoutWorkflowCountUpTimerFragment workoutTimer = new WorkoutWorkflowCountUpTimerFragment(exerciseToLaunch, this);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flPerformWorkoutMainFrame, workoutTimer);
			transaction.commit();
		}
		
		
	}
	
	/**
	 * TODO Sets array will go to the workoutset fragment
	 */
	public void getExercisesForWorkout (String workoutName) {
		exercisesForWorkout = new ArrayList<Exercise>();
		String exerciseSequence = mDbHelper.getExerciseSequence(workoutName);
		StringTokenizer st = new StringTokenizer(exerciseSequence,",");
		while (st.hasMoreTokens()) {
			Long exerciseId =  Long.parseLong(st.nextToken());
			Exercise exercise = mDbHelper.getExerciseFromId(exerciseId);
			exercisesForWorkout.add(exercise);
		}
	}
}
