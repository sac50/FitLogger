package com.cwru.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;

public class ExerciseBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	private String workoutName;
	
	public ExerciseBankFragment (String workoutName) { 
		this.workoutName = workoutName;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		View view = (LinearLayout) inflater.inflate(R.layout.exercise_bank, container, false);
		// Set DB Object
		mDbHelper = new DbAdapter(this.getActivity());
		
		List<ExerciseBankRow> list = getExerciseBankList();
		CheckBoxArrayAdapter adapter = new CheckBoxArrayAdapter(this.getActivity(), list, this);
		this.setListAdapter(adapter);
	
		
		if (!HomeScreen.isTablet) {
			Button button = new Button(this.getActivity());
			button.setText("Order Exercises for Workout");
			button.setOnClickListener(orderExercises);
			LinearLayout ll = (LinearLayout) view.findViewById(R.id.llExerciseBank);
			ll.addView(button);
			Log.d("BUTTON", "ADDED BUTTON TO BANK FRAGMENT");
		}
		
		return view;
	}
	
	
	/**
	 * Create Workout Button Click Listener
	 */
	View.OnClickListener orderExercises = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// Create new transaction
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
			//transaction.add(R.id.flWorkoutExerciseListingRightFrame, esequence, "exerciseSequence");
			// Replace the workout information fragment with the exercise bank
			transaction.replace(R.id.flWorkoutExerciseListingMainFrame, esequence);	
			transaction.addToBackStack(null);
			transaction.commit();			
		}
		
	};
	
	private List<ExerciseBankRow> getExerciseBankList() {
		List<ExerciseBankRow> list = new ArrayList<ExerciseBankRow>();
		Hashtable<Long, Boolean> exercisesChecked = getCheckedExercises();
		/* Query the DB to get the exercises available */
		mDbHelper.open();
		Cursor cursor = mDbHelper.getAllExercises();
		while (cursor.moveToNext()) {
			Log.d("Exercise name", cursor.getString(cursor.getColumnIndex("name")));
			String exerciseName = cursor.getString(cursor.getColumnIndex("name"));
			Long exerciseId = cursor.getLong(cursor.getColumnIndex("_id"));
			Exercise exercise = new Exercise(exerciseId, exerciseName);
			if (exercisesChecked.containsKey(exerciseId)) {
				Log.d("getExerciseBankList", "set selected TRUE");
				list.add(get(exercise, workoutName, true));
			} else {
				list.add(get(exercise, workoutName, false));
				Log.d("getExerciseBankList", "set selected FALSE");
			}
		}
		mDbHelper.close();
		return list;
	}
	
	/*
	 * Hash table to get exercises that belong to workout
	 * in the get exercise method we check for collisions to see if exercise should be marked checked on load
	 */
	private Hashtable<Long, Boolean> getCheckedExercises() {
		Hashtable<Long, Boolean> exercises = new Hashtable<Long, Boolean> ();
		mDbHelper.open();
		String exerciseSequence = mDbHelper.getExerciseSequence(workoutName);
		StringTokenizer st = new StringTokenizer(exerciseSequence,",");
		while (st.hasMoreTokens()) {
			Long exerciseId =  Long.parseLong(st.nextToken());
			Exercise exercise = mDbHelper.getExerciseFromId(exerciseId);
			exercises.put(exerciseId, true);
		}
		mDbHelper.close();
		return exercises;
		/** TODO
		 * Add selected parameter for get below and in checkbox adapter check the status of that var to 
		 * see if it should be checked or not
		 */
	}
	
	private ExerciseBankRow get(Exercise exercise, String workoutName, boolean selectedStatus) {
		return new ExerciseBankRow(exercise, workoutName, selectedStatus);
	}
		
		
}
