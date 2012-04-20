package com.cwru.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.HistoryFragment;
import com.cwru.fragments.WorkoutSetFragment;
import com.cwru.fragments.WorkoutSummaryFragment;
import com.cwru.fragments.WorkoutWorkflowCountDownTimerFragment;
import com.cwru.fragments.WorkoutWorkflowCountUpTimerFragment;
import com.cwru.fragments.WorkoutWorkflowDistanceFragment;
import com.cwru.fragments.WorkoutWorkflowIntervalFragment;
import com.cwru.model.Exercise;
import com.cwru.model.goToHistoryListener;
import com.cwru.model.goToNotesListener;

/**
 * Activity to handle the execution of a user defined workout.  Contains a mainframe which shows the exercise specific data.
 * Contains next and prev buttons for exercise navigation within the workout.
 * 
 * @author sacrilley
 * 
 * Use fragments to alternate between different exercises for the workout
 * so we'll have set fragment, cardio fragment and interval fragment
 *
 */
public class PerformWorkout extends FragmentActivity implements goToNotesListener, goToHistoryListener {
	private ArrayList<Exercise> exercisesForWorkout;
	private DbAdapter mDbHelper;
	private Exercise currentExercise;
	private int exerciseCounter;
	private Button btnPrevious; 
	private Button btnNext;
	private TextView tvPercentDone;
	private int workoutId;
	private TextView appTitleBar;
	
	@Override
	/**
	 * oncreate Callback.  Sets the layout for the activity and the text for the top bar of window.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.perform_workout);
		String workoutName = (String) this.getIntent().getExtras().get("workoutName");
		mDbHelper = new DbAdapter(this);
		workoutId = mDbHelper.getWorkoutIdFromName(workoutName);
		getExercisesForWorkout(workoutName);
		exerciseCounter = 0;
		btnPrevious = (Button) this.findViewById(R.id.btnPerformWorkoutPrev);
		btnPrevious.setOnClickListener(prevExercise);
		btnNext = (Button) this.findViewById(R.id.btnPerformWorkoutNext);
		btnNext.setOnClickListener(nextExercise);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvPerformWorkoutAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
		
		tvPercentDone = (TextView) this.findViewById(R.id.tvPerformWorkoutPercentageDone);
		Log.d("Size", "ExercisesForWorkout: " + exercisesForWorkout.size());
		if (exercisesForWorkout.size() == 0) {
			TextView tvEmpty = new TextView(this);
			LinearLayout llContainer = (LinearLayout) this.findViewById(R.id.llPerformWorkoutContainer);
			tvEmpty.setText("No exercises in workout");
			llContainer.addView(tvEmpty);
		} 
		else {
			int percentage = exerciseCounter / exercisesForWorkout.size();
			tvPercentDone.setText(percentage + " % Workout Complete");
			launchExercise();
		}

	}
	
	/**
	 * Launches the exercise currently to be done to the main frame layout in the activity layout
	 */
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
		Log.d("Steve", "ExerciseCounter: "  + exerciseCounter);
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
	
	/**
	 * Performs fragment transaction to show set based exercise in main frame
	 * @param exercise
	 */
	private void launchSetExercise(Exercise exercise) {
		WorkoutSetFragment workoutSet = new WorkoutSetFragment(exercise, this, workoutId);
		WorkoutSetFragment.setGoToNotesListener(this);
		WorkoutSetFragment.setGoToHistoryListener(this);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
		//	NotesFragment notes = new NotesFragment();
			HistoryFragment history = new HistoryFragment();
			transaction.replace(R.id.flPerformWorkoutLeftFrame, workoutSet);
			//transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
			transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
		}
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, workoutSet);
		}
		transaction.commit();
	}
	
	/**
	 * Performs fragment transaction to show countdown timer based exercise in main frame.
	 * @param exercise
	 */
	private void launchCountdownTimeExercise(Exercise exercise) {
		WorkoutWorkflowCountDownTimerFragment.setGoToNotesListener(this);
		WorkoutWorkflowCountDownTimerFragment.setGoToHistoryListener(this);
		WorkoutWorkflowCountDownTimerFragment workoutTimer = new WorkoutWorkflowCountDownTimerFragment(exercise, this, workoutId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
		//	NotesFragment notes = new NotesFragment();
			HistoryFragment history = new HistoryFragment();
			transaction.replace(R.id.flPerformWorkoutLeftFrame, workoutTimer);
		//	transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
			transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
		}
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, workoutTimer);
		}
		transaction.commit();
	}
	
	/**
	 * Performs fragment transaction to show countup timer based exercise in main frame.
	 * @param exercise
	 */
	private void launchCountupTimeExercise(Exercise exercise) {
		WorkoutWorkflowCountUpTimerFragment.setGoToNotesListener(this);
		WorkoutWorkflowCountUpTimerFragment.setGoToHistoryListener(this);
		WorkoutWorkflowCountUpTimerFragment workoutTimer = new WorkoutWorkflowCountUpTimerFragment(exercise, this, workoutId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
		//	NotesFragment notes = new NotesFragment();
			HistoryFragment history = new HistoryFragment();
			transaction.replace(R.id.flPerformWorkoutLeftFrame, workoutTimer);
		//	transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
			transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
		}
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, workoutTimer);
		}
		transaction.commit();
	}
	
	/**
	 * Performs fragment transaction to show distance based exercise in main frame
	 * @param exercise
	 */
	private void launchDistanceExercise(Exercise exercise) {
		WorkoutWorkflowDistanceFragment.setGoToNotesListener(this);
		WorkoutWorkflowDistanceFragment.setGoToHistoryListener(this);
		WorkoutWorkflowDistanceFragment distance = new WorkoutWorkflowDistanceFragment(exercise, this, workoutId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
		//	NotesFragment notes = new NotesFragment();
			HistoryFragment history = new HistoryFragment();
			transaction.replace(R.id.flPerformWorkoutLeftFrame, distance);
		//	transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
			transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
		}
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, distance);
		}
		transaction.commit();
	}
	
	/**
	 * Performs fragment transaction to show interval based exercise main frame
	 * @param exercise
	 */
	private void launchIntervalExercise(Exercise exercise) {
		// Get Intervals for Exercise
		WorkoutWorkflowIntervalFragment.setGoToNotesListener(this);
		exercise.setInterval(mDbHelper.getIntervalForExercise(exercise.getId()));
		WorkoutWorkflowIntervalFragment interval = new WorkoutWorkflowIntervalFragment(exercise, this, workoutId,0);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (HomeScreen.isTablet) {
			
		} 
		else {
			transaction.replace(R.id.flPerformWorkoutMainFrame, interval);
		}
		transaction.commit();
	}
	
	/**
	 * Gets the exercises belonging to the selected workout
	 * @param workoutName
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
	
	/**
	 * Listener responding to the click of the next exercise button.  Loads the next exercise into the main frame.
	 */
	View.OnClickListener nextExercise = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			exerciseCounter++;
			boolean finished = false;
			if (exerciseCounter >= exercisesForWorkout.size()) {
				finished = true;
				exerciseCounter = exercisesForWorkout.size();
			}
			double p = (double) (exerciseCounter) / exercisesForWorkout.size();
			p *= 100;
			int percentage = (int) (p);
			//int percentage = (int)((double)exerciseCounter / (double)exercisesForWorkout.size()) * 100;			
			Log.d("Steve", "% = " + percentage);
			Log.d("Steve", "Exercises: " + exercisesForWorkout.size());
			Log.d("Steve", "Exercise Counter: " + exerciseCounter);
			if (percentage > 100) { percentage = 100; }
			tvPercentDone.setText(percentage + " % Workout Complete");
			Log.d("STEVE", "Exercise Counter: " +  exerciseCounter + " | " + exercisesForWorkout.size());
			if (finished) {
					// Launch Workout Summary
					/**TODO 
					 * CHANGE DATE TO MM/DD/YYYY
					 */
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					//get current date time with Date()
				    Date date = new Date();
				    String dateString = dateFormat.format(date);
					WorkoutSummaryFragment workoutSummary = new WorkoutSummaryFragment(PerformWorkout.this, workoutId, dateString);
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					if (HomeScreen.isTablet) {
			
					}
					else {
						transaction.replace(R.id.flPerformWorkoutMainFrame, workoutSummary);
						tvPercentDone.setText("100 % Workout Complete");
					}
					transaction.commit();
			
			}
			else {
				updateButtonText();
				launchExercise();
			}
		}
	};
	
	/**
	 * Listener responding to the click of the previous exercise button.  Loads the previous exercise into the main frame
	 */
	View.OnClickListener prevExercise = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			exerciseCounter--;
			if (exerciseCounter <= 0) {	
				exerciseCounter = 0;
			}	
			double p = (double) (exerciseCounter) / exercisesForWorkout.size();
			p *= 100;
			int percentage = (int) (p);
			//int percentage = (int)((double)exerciseCounter / (double)exercisesForWorkout.size()) * 100;			
			Log.d("Steve", "% = " + percentage);
			Log.d("Steve", "Exercises: " + exercisesForWorkout.size());
			Log.d("Steve", "Exercise Counter: " + exerciseCounter);
			if (percentage > 100) { percentage = 100; }
			tvPercentDone.setText(percentage + " % Workout Complete");
			updateButtonText();
			launchExercise();
						
		}
	};
	
	/**
	 * Function to display appropriate text for the naviagation buttons depending on what exercise index where are currently at
	 */
	private void updateButtonText() {
		if (exerciseCounter == 0) {
			btnPrevious.setText("Begin");
		} else {
			btnPrevious.setText("Prev");
		}
		
		if (exerciseCounter == exercisesForWorkout.size() -1) {
			btnNext.setText("Finish");
		} else {
			btnNext.setText("Next");
		}
	}

	@Override
	/**
	 * Launches Activity to display the user note for the activity
	 */
	public void goToExerciseNote(int exerciseId) {
		Intent intent = new Intent(this, NotesActivity.class);
		intent.putExtra("EXERCISE-ID", exerciseId);
		startActivity(intent);
	}

	@Override
	/**
	 * Launches the activity to display the exercise history for the activity
	 */
	public void goToExerciseHistory(int exerciseId) {
		Intent intent = new Intent(this, ExerciseHistoryActivity.class);
		intent.putExtra("EXERCISE-ID", exerciseId);
		startActivity(intent);
	}
	
	/**
	 * Top title bar on click event handler.  Takes user back to home screen.  Resets back stack to clear history.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(PerformWorkout.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
}
