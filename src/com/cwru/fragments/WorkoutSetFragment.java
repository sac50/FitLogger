package com.cwru.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.cwru.model.Set;
import com.cwru.model.SetResult;
import com.cwru.model.WorkoutResult;

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
	
	
	
	public WorkoutSetFragment(Exercise exercise, Context context, int workoutId) {
		// Set Adapter
		mDbHelper = new DbAdapter(context);
		this.exercise = exercise;
		sets = mDbHelper.getSetsForExercise(exercise.getId()).toArray(new Set [0]);
		setCounter = 0;
		this.workoutId = workoutId;
		
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
		etWeight.setText(sets[setCounter].getWeight() + "");
		etReps.setText(sets[setCounter].getReps() + "");
		
		
		// if phone show notes and history button
		if (!HomeScreen.isTablet) {
			TableLayout tl = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowSetHistoryNoteRow);
			Button btnHistory = new Button(this.getActivity());
			Button btnNotes = new Button(this.getActivity());
			btnHistory.setText("History");
			btnNotes.setText("Notes");
			
			TableRow tr = new TableRow(WorkoutSetFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tr.addView(btnHistory);
			tr.addView(btnNotes);
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
		}
		
		return view;
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
}
