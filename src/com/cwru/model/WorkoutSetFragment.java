package com.cwru.model;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;

public class WorkoutSetFragment extends Fragment {
	private DbAdapter mDbHelper;
	private Exercise exercise;
	private Set[] sets;
	private int setCounter;
	private Context context;
	
	private TextView tvExerciseName;
	private TextView tvSetsToDo;
	private EditText etWeight;
	private EditText etReps;
	private TableLayout tlRepResults;
	private Button btnRecord;
	
	public WorkoutSetFragment(Exercise exercise, Context context) {
		// Set Adapter
		mDbHelper = new DbAdapter(context);
		this.exercise = exercise;
		sets = mDbHelper.getSetsForExercise1(exercise.getId());
		setCounter = 0;
		
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
		
		return view;
	}
	
	View.OnClickListener recordSetListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// increment setCounter
			setCounter++;
			double weight = Double.parseDouble(etWeight.getText().toString());
			double reps = Integer.parseInt(etReps.getText().toString());
			// If more sets, populate with pre determined value
			if (setCounter < sets.length) { 
				etWeight.setText(sets[setCounter].getWeight() + "");
				etReps.setText(sets[setCounter].getReps() + "");
			}
			TableRow tr = new TableRow(WorkoutSetFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tvWeightRecord = new TextView(WorkoutSetFragment.this.getActivity());
			TextView tvRepsRecord = new TextView(WorkoutSetFragment.this.getActivity());
			tvWeightRecord.setText(weight + " wt");
			tvRepsRecord.setText(reps + " reps");
			tr.addView(tvWeightRecord);
			tr.addView(tvRepsRecord);

			tlRepResults.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
			/*
			TableRow tr = new TableRow(WorkoutSetFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tvWeightRecord = new TextView(WorkoutSetFragment.this.getActivity());
			TextView tvRepsRecord = new TextView(WorkoutSetFragment.this.getActivity());
			tvWeightRecord.setText(weight + " wt");
			tvRepsRecord.setText(reps + " reps");
			// Add table row
			tlRepResults.addView(tr, new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			/* TODO
			 * INSERT REP AND WEIGHT INTO DB
			 */
			
			
			
		}
	};
}
