package com.cwru.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.DistanceResult;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseGoal;
import com.cwru.model.TimeResult;
import com.cwru.model.WorkoutResult;
import com.cwru.model.goToNotesListener;

public class WorkoutWorkflowCountUpTimerFragment extends Fragment {

	private Chronometer chrTimer;
	private DbAdapter mDbHelper;
	private long startTime;
	private long countDown;
	private Exercise exercise;
	private Context context;
	private boolean stop;
	private boolean initializedTimer; // so timer gets set only once
	private long baseTime;
	private long stopTime;
	private int workoutId;
	
	private TextView tvExerciseName;
	private Button btnStartStop;
	private Button btnRecord;
	private TableLayout tlResultTable;
	
	public static goToNotesListener listenerNotes;

	
	public WorkoutWorkflowCountUpTimerFragment (Exercise exercise, Context context, int workoutId) {
		this.exercise = exercise;
		Log.d("Steve", "ExerciseId: " + exercise.getId());
		this.context = context;
		mDbHelper = new DbAdapter(context);
		stop = true;
		initializedTimer = false;
		this.workoutId = workoutId;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (LinearLayout) inflater.inflate(R.layout.workout_workflow_countup_timer, container, false);
		btnStartStop = (Button) view.findViewById(R.id.btnWorkoutWorkflowCountUpTimerStartPause);
		if (btnStartStop == null) { Log.d("STEVE", "Button null"); }
		btnStartStop.setOnClickListener(updateTimer);
		
		btnRecord = (Button) view.findViewById(R.id.btnWorkoutWorkflowCountUpTimerRecord);
		btnRecord.setOnClickListener(recordTime);
		
		tvExerciseName = (TextView) view.findViewById(R.id.tvWorkoutWorkflowCountUpTimerExerciseName);
		tvExerciseName.setText(exercise.getName());
		
		tlResultTable = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowCountUpTimeResults);
		
		// if phone show notes and history button
		if (!HomeScreen.isTablet) {
			TableLayout tl = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowCountUpSetHistoryNoteRow);
			Button btnHistory = new Button(this.getActivity());
			Button btnNotes = new Button(this.getActivity());
			btnHistory.setText("History");
			btnNotes.setText("Notes");
			btnHistory.setOnClickListener(historyButtonListener);
			btnNotes.setOnClickListener(notesButtonListener);
			
			TableRow tr = new TableRow(WorkoutWorkflowCountUpTimerFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tr.addView(btnHistory);
			tr.addView(btnNotes);
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
		}

		chrTimer = (Chronometer) view.findViewById(R.id.chrWorkoutWorkflowCountUpTimer);
		chrTimer.setText("00:00:00");
		chrTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

	        @Override
	        public void onChronometerTick(Chronometer chronometer) {
	            CharSequence text = chronometer.getText();
	            if (text.length()  == 5) {
	                chronometer.setText("00:"+text);
	            } else if (text.length() == 7) {
	                chronometer.setText("0"+text);
	            }
	        }
	    });
		//chrTimer.setFormat("Formatted time (%s)");
		return view;
	}
	
	View.OnClickListener historyButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ExerciseSummaryFragment exerciseSummary = new ExerciseSummaryFragment(context, exercise.getId()); 
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.flPerformWorkoutMainFrame, exerciseSummary);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	};	
	
	View.OnClickListener notesButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			listenerNotes.goToExerciseNote(exercise.getId());
		}
	};
	
	View.OnClickListener updateTimer = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!initializedTimer) {
				initializedTimer = true;
				stopTime = 0;
				chrTimer.setBase(SystemClock.elapsedRealtime());
				//baseTime = SystemClock.elapsedRealtime();
				btnStartStop.setText("Pause");
				stop = false;
				chrTimer.start();
			} else if (stop) {
				stop = false;
				btnStartStop.setText("Pause");
				chrTimer.setBase(chrTimer.getBase() + SystemClock.elapsedRealtime() - stopTime);
				chrTimer.start();				
			}
			else {
				stop = true;
				stopTime = SystemClock.elapsedRealtime();
				btnStartStop.setText("Start");
				//stop = chrTimer.getBase();	
				chrTimer.stop();
			}
		}
	};
	
	View.OnClickListener recordTime = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String time = chrTimer.getText().toString();
			// Parse Time.  Format is hh:mm:ss
			int hours = Integer.parseInt(time.substring(0,2));
			int minutes = Integer.parseInt(time.substring(3,5));
			int seconds = Integer.parseInt(time.substring(6));
			
			Log.d("Steve", "Hours: " + hours);
			Log.d("Steve", "Minutes: " + minutes);
			Log.d("Steve", "Seconds: " + seconds);
			
			// if clock running, not paused,  pause the clock.
			if (!stop) {
				stop = true;
				stopTime = SystemClock.elapsedRealtime();
				btnStartStop.setText("Start");
				//stop = chrTimer.getBase();	
				chrTimer.stop();
				// Make it so that the chrTimer will reset to 0;
				chrTimer.setText("00:00:00");
				initializedTimer = false;
			}
			/** TODO
			 * REFACTOR TO A SINGLE CALL, STOP / START TIMER
			 */
			// Adds set to on screen log
			TableRow tr = new TableRow(WorkoutWorkflowCountUpTimerFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tvTimeRecord = new TextView(WorkoutWorkflowCountUpTimerFragment.this.getActivity());
			tvTimeRecord.setText("Time recorded: " + time);
			tr.addView(tvTimeRecord);

			tlResultTable.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
						
			/* Generate Workout Result Row in Database */
			Log.d("Steve", "WorkoutID: " + workoutId);
			WorkoutResult workoutResult = new WorkoutResult(workoutId, exercise.getId());
			Log.d("STeve", "|| WorkoutResult ExerciseID: " + workoutResult.getExerciseId());
			int workoutResultId = mDbHelper.storeWorkoutResult(workoutResult);
			Log.d("Steve", "WorkoutResultIDL : " + workoutResultId);

			workoutResult.setId(workoutResultId);
			workoutResult.setMode(WorkoutResult.TIME_BASED_EXERCISE);
			/* Generate Time Result Row in Database */
			int timeSeconds = hours * 60 * 60 + minutes * 60 + seconds;
			String units = "seconds";
			TimeResult timeResult = new TimeResult(workoutResultId, timeSeconds, units);
			mDbHelper.storeTimeResult(timeResult);
			
			/** TODO
			 * EXAMPLE OF CALLING FOR COMPLETED EXERCISE GOALS
			 * NOTE THAT THE WORKOUTRESULT MUST HAVE ITS ID AND MODE SET,
			 * WHICH I ADDED ON LINE 178-179
			 */
			ArrayList<ExerciseGoal> goals = mDbHelper.getNewlyCompletedExerciseGoals(workoutResult);
			for (ExerciseGoal goal : goals) {
				Context context = v.getContext().getApplicationContext();
				CharSequence text = "Goal completed: " + goal.getName();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}
	};
	
	public static void setGoToNotesListener(goToNotesListener listener) {
		listenerNotes = listener;
	}

}
