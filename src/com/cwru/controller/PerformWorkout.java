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
import com.cwru.fragments.HistoryFragment;
import com.cwru.fragments.NotesFragment;
import com.cwru.fragments.WorkoutSetFragment;
import com.cwru.fragments.WorkoutWorkflowCountDownTimerFragment;
import com.cwru.fragments.WorkoutWorkflowCountUpTimerFragment;
import com.cwru.fragments.WorkoutWorkflowDistanceFragment;
import com.cwru.model.Exercise;

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
	private int workoutId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.perform_workout);
		String workoutName = (String) this.getIntent().getExtras().get("workoutName");
		mDbHelper = new DbAdapter(this);
		workoutId = mDbHelper.getWorkoutIdFromName(workoutName);
		getExercisesForWorkout(workoutName);
		exerciseCounter = 0;
		btnPrevious = (Button) this.findViewById(R.id.btnPerformWorkoutPrev);
		btnNext = (Button) this.findViewById(R.id.btnPerformWorkoutNext);
		tvPercentDone = (TextView) this.findViewById(R.id.tvPerformWorkoutPercentageDone);
		Log.d("Size", "ExercisesForWorkout: " + exercisesForWorkout.size());
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
		//int sets = exerciseToLaunch.getSets();
		switch (exerciseToLaunch.getMode()) {
		case Exercise.SET_BASED_EXERCISE:
			Log.d("Steve", "Launched Set Based");
			launchSetExercise(exerciseToLaunch);
			break;
		case Exercise.DISTANCE_BASED_EXERCISE:
			Log.d("Steve", "Launched Distance Based");
			launchDistanceExercise(exerciseToLaunch);
			break;
		case Exercise.INTERVAL_BASED_EXERCISE:
			Log.d("Steve", "Launched Interval Based");
			launchIntervalExercise(exerciseToLaunch);
			break;
		case Exercise.COUNTDOWN_BASED_EXERCISE:
			Log.d("Steve", "Launched Time Based");
			launchCountdownTimeExercise(exerciseToLaunch);
			break;			
		case Exercise.COUNTUP_BASED_EXERCISE:
			Log.d("Steve", "Launched Count Up Time Based");
			launchCountupTimeExercise(exerciseToLaunch);
			break;
		}
	}
	
	private void launchSetExercise(Exercise exercise) {
		WorkoutSetFragment workoutSet = new WorkoutSetFragment(exercise, this, workoutId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
			NotesFragment notes = new NotesFragment();
			HistoryFragment history = new HistoryFragment();
			transaction.replace(R.id.flPerformWorkoutLeftFrame, workoutSet);
			transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
			transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
		}
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, workoutSet);
		}
		transaction.commit();
	}
	
	private void launchCountdownTimeExercise(Exercise exercise) {
		WorkoutWorkflowCountDownTimerFragment workoutTimer = new WorkoutWorkflowCountDownTimerFragment(exercise, this, workoutId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
			NotesFragment notes = new NotesFragment();
			HistoryFragment history = new HistoryFragment();
			transaction.replace(R.id.flPerformWorkoutLeftFrame, workoutTimer);
			transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
			transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
		}
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, workoutTimer);
		}
		transaction.commit();
	}
	
	private void launchCountupTimeExercise(Exercise exercise) {
		WorkoutWorkflowCountUpTimerFragment workoutTimer = new WorkoutWorkflowCountUpTimerFragment(exercise, this, workoutId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
			NotesFragment notes = new NotesFragment();
			HistoryFragment history = new HistoryFragment();
			transaction.replace(R.id.flPerformWorkoutLeftFrame, workoutTimer);
			transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
			transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
		}
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, workoutTimer);
		}
		transaction.commit();
	}
	
	private void launchDistanceExercise(Exercise exercise) {
		WorkoutWorkflowDistanceFragment distance = new WorkoutWorkflowDistanceFragment(exercise, this, workoutId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
			NotesFragment notes = new NotesFragment();
			HistoryFragment history = new HistoryFragment();
			transaction.replace(R.id.flPerformWorkoutLeftFrame, distance);
			transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
			transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
		}
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, distance);
		}
		transaction.commit();
	}
	
	private void launchIntervalExercise(Exercise exercise) {
		
	}
	
	/**
	 * TODO Sets array will go to the workoutset fragment
	 */
	public void getExercisesForWorkout (String workoutName) {
		exercisesForWorkout = new ArrayList<Exercise>();
		String exerciseSequence = mDbHelper.getExerciseSequence(workoutName);
		StringTokenizer st = new StringTokenizer(exerciseSequence,",");
		while (st.hasMoreTokens()) {
			int exerciseId = Integer.parseInt(st.nextToken());
			Exercise exercise = mDbHelper.getExerciseFromId(exerciseId);
			exercisesForWorkout.add(exercise);
		}
	}
}
