package com.cwru.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.Interval;
import com.cwru.model.IntervalResult;
import com.cwru.model.IntervalSet;
import com.cwru.model.WorkoutResult;
import com.cwru.model.goToNotesListener;

public class WorkoutWorkflowIntervalFragment extends Fragment {
	private Exercise exercise;
	private Context context;
	private int workoutId;
	private int intervalCycleNum;
	private int intervalSetNum;
	private Interval interval;
	private IntervalSet intervalSet;
	private boolean workoutResultCreated;
	private int workoutResultId;
	
	private CountDownTimer countDownTimer;
	private int seconds;
	private int minutes;
	private boolean stop;
	private boolean complete;

	
	private DbAdapter mDbHelper;

	private TextView tvExerciseName;
	private TextView tvIntervalsToDo;
	private LinearLayout llDistanceInput;
	private TextView tvDistanceInputLabel;
	private EditText etDistanceInputEntry;
	private TextView tvDistanceInputUnits;
	private LinearLayout llIntervalTimeInput;
	private TextView tvTimer;
	private Button btnStartStop;
	private Button btnRecord;
	private TableLayout tlHistoryNotes;
	private TextView tvNextIntervalLabel;
	private TableLayout tlNextIntervalList; 
	public static goToNotesListener listenerNotes;


	
	public WorkoutWorkflowIntervalFragment(Exercise exercise, Context context, int workoutId, int intervalSetNum) {
		mDbHelper = new DbAdapter(context);
		this.exercise = exercise;
		this.workoutId = workoutId;
		interval = exercise.getInterval();
		intervalSet = interval.getIntervalSets().get(intervalCycleNum);
		this.intervalCycleNum = 1;
		this.intervalSetNum = intervalSetNum;
		workoutResultCreated = false;
		this.context = context;
		stop = true;
	}
	
	public WorkoutWorkflowIntervalFragment(Exercise exercise, Context context, int workoutId, int intervalSetNum, int intervalCycleNum, boolean workoutResultCreated, int workoutResultId) {
		mDbHelper = new DbAdapter(context);
		this.exercise = exercise;
		this.workoutId = workoutId;
		interval = exercise.getInterval();
		intervalSet = interval.getIntervalSets().get(intervalCycleNum);
		this.intervalSetNum = intervalSetNum;
		this.intervalCycleNum = ++intervalCycleNum;
		this.workoutResultCreated = workoutResultCreated;
		this.workoutResultId = workoutResultId;
		this.context = context;
		stop = true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (LinearLayout) inflater.inflate(R.layout.workout_workflow_interval, container, false);
		
		// Grab Views from the XML Layout
		tvExerciseName = (TextView) view.findViewById(R.id.tvWorkoutWorkflowIntervalDistanceExerciseName);
		tvIntervalsToDo = (TextView) view.findViewById(R.id.tvWorkoutWorkflowIntervalDistanceIntervalsToDo);
		llDistanceInput = (LinearLayout) view.findViewById(R.id.llWorkoutWorkflowIntervalDistanceInput);
		tvDistanceInputLabel = (TextView) view.findViewById(R.id.tvWorkoutWorkflowIntervalDistanceInputLabel);
		etDistanceInputEntry = (EditText) view.findViewById(R.id.etWorkoutWorkflowIntervalDistanceInputEntry);
		tvDistanceInputUnits = (TextView) view.findViewById(R.id.tvWorkoutWorkflowIntervalDistanceInputUnits);
		llIntervalTimeInput = (LinearLayout) view.findViewById(R.id.llWorkoutWorkflowIntervalTimeInput);
		tvTimer = (TextView) view.findViewById(R.id.tvWorkoutWorkflowIntervalTimer);
		btnStartStop = (Button) view.findViewById(R.id.btnWorkoutWorkflowIntervalStartStop);
		btnRecord = (Button) view.findViewById(R.id.btnWorkoutWorkflowIntervalRecord);
		tlHistoryNotes = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowIntervalDistanceHistoryNoteRow);
		tvNextIntervalLabel = (TextView) view.findViewById(R.id.tvWorkoutWorkflowIntervalDistanceNextIntervalLabel);
		tlNextIntervalList = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowIntervalNextIntervalList);
		/* Determine if current interval is time or distance */
		if (intervalSet.getType().equals("distance")) {
			llIntervalTimeInput.setVisibility(LinearLayout.GONE);
			/* Set Input to user defined length */
			etDistanceInputEntry.setText(intervalSet.getLength()+ "");
			/* Set Input Label */
			Log.d("Steve", "IntervalSet.getName(): " + intervalSet.getName());
			tvDistanceInputLabel.setText(intervalSet.getName());
			/* Set Units */
			tvDistanceInputUnits.setText(intervalSet.getUnits());
		}
		else {
			llDistanceInput.setVisibility(LinearLayout.GONE);
			complete = false;
			double length = intervalSet.getLength();
			String units = intervalSet.getUnits();
			int time = 0;
			if (units.equals("seconds")) {
				time = (int) intervalSet.getLength();
				minutes = time / 60;
				seconds = time % 60;		
			} else if (units.equals("minutes")) {
				time = (int) intervalSet.getLength();
				minutes = (int)length;
				seconds = 0;
			} else if (units.equals("hours")) {
				
			}
			
			time = minutes*60 + seconds;
			time = time * 1000;
			
			tvTimer.setText(getFormatedTime());

			countDownTimer = new CountDownTimer(time, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {
					decreaseTime();
					tvTimer.setText(getFormatedTime());
				}
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
				}
			};	
			
			btnStartStop.setOnClickListener(updateTimer);
		}
		
		/* Set Exercise Name */
		tvExerciseName.setText(intervalSet.getName());
		/* Set Intervals To do */
		tvIntervalsToDo.setText((interval.getNumRepeats() - intervalSetNum) + " Intervals To Do");

		/* Show remaining exercises in interval */
		ArrayList<IntervalSet> intervalSetList = interval.getIntervalSets();
		for (int i = intervalCycleNum; i < intervalSetList.size(); i++) {
			TableRow tableRow = new TableRow(WorkoutWorkflowIntervalFragment.this.getActivity());
			tableRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tvIntervalSet = new TextView(WorkoutWorkflowIntervalFragment.this.getActivity());
			tvIntervalSet.setText(intervalSetList.get(i).getName() + ": " + intervalSetList.get(i).getLength() + " " + intervalSetList.get(i).getUnits());
			tableRow.setPadding(10, 5, 0, 0);
			tableRow.addView(tvIntervalSet);
			tlNextIntervalList.addView(tableRow, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
		
		btnRecord.setOnClickListener(recordInterval);
		
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
	
	View.OnClickListener recordInterval = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// If workout result not created, create it and set flag that it was created
			if (!workoutResultCreated) {
				workoutResultCreated = true;
				Log.d("Steve", "WorkoutID: " + workoutId);
				WorkoutResult workoutResult = new WorkoutResult(workoutId, exercise.getId());
				workoutResultId = mDbHelper.storeWorkoutResult(workoutResult);
			}
			
			
			double length = 0;
			String units = "";
			if (intervalSet.getType().equals("distance")) {
				length = Double.parseDouble(etDistanceInputEntry.getText().toString());
				units = tvDistanceInputUnits.getText().toString();
			}
			else {
				double min = Double.parseDouble(tvTimer.getText().toString().substring(0,2));
				double sec = Double.parseDouble(tvTimer.getText().toString().substring(3));
				length = min*60 + sec;
				units = "seconds";
			}
			
			IntervalResult intervalResult = new IntervalResult(workoutResultId, interval.getId(), intervalSetNum, intervalSet.getId(), length, units);
			mDbHelper.storeIntervalResult(intervalResult);		
			
			if (intervalCycleNum == interval.getIntervalSets().size()) {
				intervalCycleNum = 0;
				intervalSetNum++;
			}
			
			Log.d("Steve", "--------------------------------------------------------");
			Log.d("Steve", "IntervalSetNum: " + intervalSetNum);
			Log.d("Steve", "IntervalCycleNum: " + intervalCycleNum);
			
			if (intervalSetNum <= interval.getNumRepeats()) {
				WorkoutWorkflowIntervalFragment intervalFragment = new WorkoutWorkflowIntervalFragment(exercise, context, workoutId, intervalSetNum, intervalCycleNum, true, workoutResultId);
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				if (HomeScreen.isTablet) {
					//NotesFragment notes = new NotesFragment();
					HistoryFragment history = new HistoryFragment();
					transaction.replace(R.id.flPerformWorkoutLeftFrame, intervalFragment);
					//transaction.replace(R.id.flPerformWorkoutRightTopFrame, notes);
					transaction.replace(R.id.flPerformWorkoutRightBottomFrame, history);
				}
				else {
					transaction.replace(R.id.flPerformWorkoutMainFrame, intervalFragment);
				}
				transaction.commit();
			} 
			else {
				/* Go to next exercise */
				// Exercise Complete 
				
			}
			
			
		}

	};
	
	View.OnClickListener updateTimer = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (stop) {
				stop = false;
				btnStartStop.setText("Pause");
				countDownTimer.start();				
			}
			else {
				stop = true;
				btnStartStop.setText("Start");
				countDownTimer.cancel();
			}
		}
	};
	
	private String getFormatedTime() {
		String time = "";
		if (minutes < 10){ time += "0" + minutes + ":"; }
		else { time += minutes + ":";}
		if (seconds < 10) { time += "0" + seconds;}
		else { time += seconds;}
		return time;
	}
	
	private void decreaseTime() {
		// only update if stop is false
		if (!complete) {
			if (seconds == 0) {
				seconds = 60;
				minutes--;
				if (seconds == 0 && minutes == 0) {
					complete = true;
				}
			}
			seconds -= 1;		
		}
	}
	
	public static void setGoToNotesListener(goToNotesListener listener) {
		listenerNotes = listener;
	}
}
