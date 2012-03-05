package com.cwru.model;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;

public class WorkoutWorkflowDistanceFragment extends Fragment {
	private DbAdapter mDbHelper;
	private Exercise exercise;
	
	private TextView tvExerciseName;
	private TextView tvDistanceToDo;
	private EditText etDistanceEntry;
	private Spinner spnDistanceUnits; 
	private Button btnRecord;
	
	public WorkoutWorkflowDistanceFragment (Exercise exercise, Context context) {
		this.exercise = exercise;
		mDbHelper = new DbAdapter(context);
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (LinearLayout) inflater.inflate(R.layout.workout_workflow_distance, container, false);
		
		tvExerciseName = (TextView) view.findViewById(R.id.tvWorkoutWorkflowDistanceExerciseName);
		tvDistanceToDo = (TextView) view.findViewById(R.id.tvWorkoutWorkflowDistanceToDo);
		etDistanceEntry = (EditText) view.findViewById(R.id.etWorkoutWorkflowDistanceText);
		spnDistanceUnits = (Spinner) view.findViewById(R.id.spnWorkoutWorkflowDistanceUnits);
		btnRecord = (Button) view.findViewById(R.id.btnWorkoutWorkflowDistanceRecord);
		
		/* Set Values */
		tvExerciseName.setText(exercise.getName());
		tvDistanceToDo.setText(exercise.getDistance() + " " + exercise.getDistanceType() + " to do");

		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this.getActivity(), R.array.exerciseDistances, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    int spinnerPosition = adapter.getPosition(exercise.getDistanceType());
	    
	    spnDistanceUnits.setAdapter(adapter);
	    spnDistanceUnits.setSelection(spinnerPosition);
	    
	    
		// if phone show notes and history button
		if (!HomeScreen.isTablet) {
			TableLayout tl = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowDistanceHistoryNoteRow);
			Button btnHistory = new Button(this.getActivity());
			Button btnNotes = new Button(this.getActivity());
			btnHistory.setText("History");
			btnNotes.setText("Notes");
			
			TableRow tr = new TableRow(WorkoutWorkflowDistanceFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tr.addView(btnHistory);
			tr.addView(btnNotes);
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
		
		return view;
	}
}
