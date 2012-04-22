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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Distance;
import com.cwru.model.DistanceResult;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseGoal;
import com.cwru.model.WorkoutResult;
import com.cwru.model.goToHistoryListener;
import com.cwru.model.goToNotesListener;
import com.cwru.utils.MeasurementConversions;

/**
 * Fragment that provides the interface and back-end support for a Distance based exercise
 * @author scrilley
 *
 */
public class WorkoutWorkflowDistanceFragment extends Fragment {
	private Context context;
	private DbAdapter mDbHelper;
	private Exercise exercise;
	private Distance distance;
	private int workoutId;
	private String currentUnits;  // Used to know what were the previous units on spinner unit selection change
	
	private TextView tvExerciseName;
	private TextView tvDistanceToDo;
	private EditText etDistanceEntry;
	private Spinner spnDistanceUnits; 
	private Button btnRecord;
	private TableLayout tlResults;
	
	public static goToNotesListener listenerNotes;
	public static goToHistoryListener listenerHistory;

	/**
	 * Constructor
	 * @param exercise
	 * @param context
	 * @param workoutId
	 */
	public WorkoutWorkflowDistanceFragment (Exercise exercise, Context context, int workoutId) {
		this.context = context;
		this.exercise = exercise;
		mDbHelper = new DbAdapter(context);
		distance = mDbHelper.getDistanceForExercise(exercise.getId());
		currentUnits = distance.getUnits();
		this.workoutId = workoutId;
	}
		
	@Override
	/**
	 * Set layout and layout fields
	 */
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
		tlResults = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowDistanceResults);
		
		/* Set Values */
		tvExerciseName.setText(exercise.getName());
		tvDistanceToDo.setText(distance.getLength() + " " + distance.getUnits() + " to do");
		
		/* Set the user input to be the default value for easy access */
		etDistanceEntry.setText(distance.getLength() + "");
		/* Generate drop down values for distance units options */
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this.getActivity(), R.array.exerciseDistances, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	    
	    spnDistanceUnits.setAdapter(adapter);
	    /* Set default unit option to predefined user option */
	    spnDistanceUnits.setSelection(adapter.getPosition(distance.getUnits()));	    
	    
	    /* Selection listener to change the units of to do after spinner changes */
	    spnDistanceUnits.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View selectedItemView,
					int position, long id) {
				// TODO Auto-generated method stub
				String text = (String) spnDistanceUnits.getItemAtPosition(position);
				Log.d("Steve", "Spinner Selected Text: " + text);
				double distanceToDoConverted = distanceToDoConversion(text);
				tvDistanceToDo.setText(distanceToDoConverted + " " + text);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    	    
	    /* Set Record Button Listener */
	    btnRecord.setOnClickListener(recordDistanceListener);
		// if phone show notes and history button
		if (!HomeScreen.isTablet) {
			TableLayout tl = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowDistanceHistoryNoteRow);
			Button btnHistory = new Button(this.getActivity());
			Button btnNotes = new Button(this.getActivity());
			btnHistory.setText("History");
			btnNotes.setText("Notes");
			btnHistory.setOnClickListener(historyButtonListener);
			btnNotes.setOnClickListener(notesButtonListener);
			
			TableRow tr = new TableRow(WorkoutWorkflowDistanceFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tr.addView(btnHistory);
			tr.addView(btnNotes);
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
		
		return view;
	}
	
	/**
	 * Listener when history is clicked.  Calls the activity that set the listener.  Launches exercise history for the current exercise
	 */
	View.OnClickListener historyButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			listenerHistory.goToExerciseHistory(exercise.getId());
		}
	};	
	
	/**
	 * Listener when notes button is clicked.  Calls the activity that set the listener.  Launches the notes activity for the current exercise
	 */
	View.OnClickListener notesButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			listenerNotes.goToExerciseNote(exercise.getId());
		}
	};
	
	/**
	 * Records the values set by the user in the exercise
	 */
	View.OnClickListener recordDistanceListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			/* Generate Workout Result Row in Database */
			Log.d("Steve", "WorkoutID: " + workoutId);
			WorkoutResult workoutResult = new WorkoutResult(workoutId, exercise.getId());
			int workoutResultId = mDbHelper.storeWorkoutResult(workoutResult);
			/* Generate Distance Result Row in Database */
			double length = Double.parseDouble(etDistanceEntry.getText().toString());
			String units = (String) spnDistanceUnits.getSelectedItem();
			DistanceResult distanceResult = new DistanceResult(workoutResultId, length, units);
			mDbHelper.storeDistanceResult(distanceResult);	
			
			workoutResult.setId(workoutResultId);
			workoutResult.setMode(WorkoutResult.DISTANCE_BASED_EXERCISE);
			ArrayList<ExerciseGoal> goals = mDbHelper.getNewlyCompletedExerciseGoals(workoutResult);
			for (ExerciseGoal goal : goals) {
				Context context = v.getContext().getApplicationContext();
				CharSequence text = "Goal completed: " + goal.getName();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
			/* Show recorded time on screen */
			TableRow tr = new TableRow(WorkoutWorkflowDistanceFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tvTimeRecord = new TextView(WorkoutWorkflowDistanceFragment.this.getActivity());
			tvTimeRecord.setText("Distance recorded: " + length + " " + units);
			tr.addView(tvTimeRecord);

			tlResults.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
	};
	
	/**
	 * Returns the converted length upon unit change from the distance units spinner
	 * @param units
	 * @return
	 */
	private double distanceToDoConversion(String units) {
		double length = distance.getLength();
		/**
		 * TODO REFACTOR THIS TO INCLUDE THE VALUES FROM THE RESOURCE FILE, MAKES HARD TO CHANGE UNITS IF ITS HARDCODED
		 */
		if (currentUnits.equals("mile") ) {
			if (units.equals("Km.")) {
				return MeasurementConversions.milesToKm(length);
			} else if (units.equals("m.")) {
				return MeasurementConversions.milesToM(length);
			} else if (units.equals("yd.")) {
				return MeasurementConversions.milesToYd(length);
			}
		} else if (currentUnits.equals("Km.")) {
			if (units.equals("mile")) {
				return MeasurementConversions.kmToMile(length);
			} else if (units.equals("m.")) {
				return MeasurementConversions.kmToM(length);
			} else if (units.equals("yd.")) {
				return MeasurementConversions.kmToYd(length);
			}
		} else if (currentUnits.equals("m.")) {
			if (units.equals("mile")) {
				return MeasurementConversions.mToMile(length);
			} else if (units.equals("Km.")) {
				return MeasurementConversions.mToKm(length);
			} else if (units.equals("yd.")) {
				return MeasurementConversions.mToYd(length);
			}
		} else if (currentUnits.equals("yd.")) {
			if (units.equals("mile")) {
				return MeasurementConversions.ydToMile(length);
			} else if (units.equals("Km.")) {
				return MeasurementConversions.ydToKm(length);
			} else if (units.equals("m.")) {
				return MeasurementConversions.ydToM(length);
			}
		}
		
		return length;  //  Value is the same units as the default option as defined by user
	}
	
	/**
	 * @param listener
	 */
	public static void setGoToNotesListener(goToNotesListener listener) {
		listenerNotes = listener;
	}
	
	/**
	 * 
	 * @param listener
	 */
	public static void setGoToHistoryListener(goToHistoryListener listener) {
		listenerHistory = listener;
	}
}
