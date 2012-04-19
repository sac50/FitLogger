package com.cwru.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.Toast;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseGoal;
import com.cwru.model.Set;
import com.cwru.model.SetResult;
import com.cwru.model.WorkoutResult;
import com.cwru.model.goToHistoryListener;
import com.cwru.model.goToNotesListener;

public class WorkoutSetFragment extends Fragment {
	
	private DbAdapter mDbHelper;
	private Exercise exercise;
	private Set[] sets;
	private int setCounter;
	private Context context;
	private int workoutId;
	private int workoutResultId = -2;
	
	private TextView tvExerciseName;
	private TextView tvSetsToDo;
	private EditText etWeight;
	private EditText etReps;
	private TableLayout tlRepResults;
	private Button btnRecord;
	
	private static goToNotesListener listenerNotes;
	private static goToHistoryListener listenerHistory;
	
	public WorkoutSetFragment(Exercise exercise, Context context, int workoutId) {
		// Set Adapter
		mDbHelper = new DbAdapter(context);
		this.exercise = exercise;
		sets = mDbHelper.getSetsForExercise(exercise.getId()).toArray(new Set [0]);
		setCounter = 0;
		this.workoutId = workoutId;
		this.context = context;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (LinearLayout) inflater.inflate(R.layout.workout_workflow_set, container, false);
		tvExerciseName = (TextView) view.findViewById(R.id.tvWorkoutWorkflowSetExerciseName);
		tvSetsToDo = (TextView) view.findViewById(R.id.tvWorkoutWorkflowSetsToDo);
		etWeight = (EditText) view.findViewById(R.id.etWorkoutWorkflowSetWeightText);
		etReps = (EditText) view.findViewById(R.id.etWorkoutWorkflowSetRepsText);
		tlRepResults = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowSetResults);
		btnRecord = (Button) view.findViewById(R.id.btnWorkoutWorkflowSetRecord);
		btnRecord.setOnClickListener(recordSetListener);
		
		tvExerciseName.setText(exercise.getName());
		tvSetsToDo.setText(sets.length + " Sets to Do");
		if (setCounter < sets.length) { 
			etWeight.setText(sets[setCounter].getWeight() + "");
			etReps.setText(sets[setCounter].getReps() + "");
		}
		else {
			etWeight.setText(sets[sets.length-1].getWeight() + "");
			etReps.setText(sets[sets.length-1].getReps() + "");
		}
		
		
		
		// if phone show notes and history button
		if (!HomeScreen.isTablet) {
			TableLayout tl = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowSetHistoryNoteRow);
			Button btnHistory = new Button(this.getActivity());
			Button btnNotes = new Button(this.getActivity());
			btnHistory.setText("History");
			btnNotes.setText("Notes");
			btnHistory.setOnClickListener(historyButtonListener);
			btnNotes.setOnClickListener(notesButtonListener);
			
			TableRow tr = new TableRow(WorkoutSetFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tr.addView(btnHistory);
			tr.addView(btnNotes);
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
		}
		
		return view;
	}
	
	View.OnClickListener historyButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			listenerHistory.goToExerciseHistory(exercise.getId());
		}
	};	
	
	View.OnClickListener notesButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			listenerNotes.goToExerciseNote(exercise.getId());
		}
	};
	
	
	@Override
	public void onResume() {
		super.onResume();
		// Get Date 
		/*
		 * 
		Calendar calendar = Calendar.getInstance();
		String date = "" + calendar.get(Calendar.YEAR) + "/";
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) { date += "0" + month + "/"; }
		else { date += month; }
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day < 10) { date += "0" + day; }
		else { date += day; }
		ArrayList<SetResult> results = mDbHelper.getSetResultsForADay(date, workoutId, exercise.getId());
		for (int i = 0; i < results.size(); i++) {
			SetResult setResult = results.get(i);
			if (workoutResultId < 0) {
				workoutResultId = setResult.getWorkoutResultId();
			}
			
			// increment setCounter
			setCounter++;
			
		
			// WorkoutResults (int workout_id, int exercise_id, int setNumber, int reps, double weight)
			// If more sets, populate with pre determined value
			if (setCounter < sets.length) { 
				etWeight.setText(sets[setCounter].getWeight() + "");
				etReps.setText(sets[setCounter].getReps() + "");
			}
			// Adds set to on screen log
			TableRow tr = new TableRow(WorkoutSetFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tvWeightRecord = new TextView(WorkoutSetFragment.this.getActivity());
			TextView tvRepsRecord = new TextView(WorkoutSetFragment.this.getActivity());
			tvWeightRecord.setText(setResult.getWeight() + " wt");
			tvRepsRecord.setText(setResult.getReps() + " reps");
			tr.addView(tvWeightRecord);
			tr.addView(tvRepsRecord);

			tlRepResults.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			// Decrease sets to do by 1.  if sets to do 0; 
			int setsToDo = sets.length - setCounter;
			if (setsToDo < 0) {
				setsToDo = 0;
			}
			tvSetsToDo.setText(setsToDo + " Sets to Do");
	
		}
		*/
		
	}
	View.OnClickListener recordSetListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (workoutResultId < 0) {
				WorkoutResult workoutResult = new WorkoutResult(workoutId, exercise.getId());
				Log.d("Steve", "WorkoutID: " + workoutId);
				workoutResultId = mDbHelper.storeWorkoutResult(workoutResult);
			}
			
			// increment setCounter
			setCounter++;
			double weight = Double.parseDouble(etWeight.getText().toString());
			int reps = Integer.parseInt(etReps.getText().toString());
			int setNum = setCounter;
			
			SetResult setResult = new SetResult(workoutResultId, setNum, reps, weight);
			mDbHelper.storeSetResult(setResult);
			
			WorkoutResult wResult = new WorkoutResult(workoutId, exercise.getId());
			wResult.setId(workoutResultId);
			wResult.setMode(WorkoutResult.SET_BASED_EXERCISE);
			/** TODO
			 * EXAMPLE OF CALLING FOR COMPLETED EXERCISE GOALS
			 * NOTE THAT THE WORKOUTRESULT MUST HAVE ITS ID AND MODE SET,
			 * WHICH I ADDED ON LINE 178-179
			 */
			ArrayList<ExerciseGoal> goals = mDbHelper.getNewlyCompletedExerciseGoals(wResult);
			for (ExerciseGoal goal : goals) {
				Context context = v.getContext().getApplicationContext();
				CharSequence text = "Goal completed: " + goal.getName();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
			// WorkoutResults (int workout_id, int exercise_id, int setNumber, int reps, double weight)
			// If more sets, populate with pre determined value
			if (setCounter < sets.length) { 
				etWeight.setText(sets[setCounter].getWeight() + "");
				etReps.setText(sets[setCounter].getReps() + "");
			}
			// Adds set to on screen log
			TableRow tr = new TableRow(WorkoutSetFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tvWeightRecord = new TextView(WorkoutSetFragment.this.getActivity());
			TextView tvRepsRecord = new TextView(WorkoutSetFragment.this.getActivity());
			tvWeightRecord.setText(weight + " wt");
			tvRepsRecord.setText(reps + " reps");
			tr.addView(tvWeightRecord);
			tr.addView(tvRepsRecord);

			tlRepResults.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			// Decrease sets to do by 1.  if sets to do 0; 
			int setsToDo = sets.length - setCounter;
			if (setsToDo < 0) {
				setsToDo = 0;
			}
			tvSetsToDo.setText(setsToDo + " Sets to Do");
		}
	};
	
	public static void setGoToNotesListener(goToNotesListener listener) {
		listenerNotes = listener;
	}
	
	public static void setGoToHistoryListener(goToHistoryListener listener) {
		listenerHistory = listener;
	}
}
