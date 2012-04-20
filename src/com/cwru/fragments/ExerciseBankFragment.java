package com.cwru.fragments;

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
import com.cwru.model.CheckBoxArrayAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseBankRow;

/**
 * 
 * @author sacrilley
 *
 */
public class ExerciseBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	private String workoutName;
	private static onGoToExerciseSequenceListener listener;
	
	/**
	 * Constructor
	 * @param workoutName
	 */
	public ExerciseBankFragment (String workoutName) { 
		this.workoutName = workoutName;
	}

	@Override
	/**
	 * 
	 */
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
		//	FragmentTransaction transaction = getFragmentManager().beginTransaction();
		//	ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
			//transaction.add(R.id.flWorkoutExerciseListingRightFrame, esequence, "exerciseSequence");
			// Replace the workout information fragment with the exercise bank
			listener.goToExerciseSequence(workoutName);
			/*
			transaction.replace(R.id.flWorkoutExerciseListingMainFrame, esequence);	
			transaction.addToBackStack(null);
			transaction.commit();			
			*/
		}
		
	};
	
	/**
	 * 
	 * @return
	 */
	private List<ExerciseBankRow> getExerciseBankList() {
		List<ExerciseBankRow> list = new ArrayList<ExerciseBankRow>();
		Hashtable<Integer, Boolean> exercisesChecked = getCheckedExercises();
		/* Query the DB to get the exercises available */
//		mDbHelper.open();
		List<Exercise> exercises = mDbHelper.getAllUndeletedExercises();
		for (Exercise exercise : exercises) {
			if (exercisesChecked.containsKey(exercise.getId())) {
				Log.d("getExerciseBankList", "set selected TRUE");
				list.add(get(exercise, workoutName, true));
			} else {
				list.add(get(exercise, workoutName, false));
				Log.d("getExerciseBankList", "set selected FALSE");
			}
		}
//		while (cursor.moveToNext()) {
//			Log.d("Exercise name", cursor.getString(cursor.getColumnIndex("name")));
//			String exerciseName = cursor.getString(cursor.getColumnIndex("name"));
//			Long exerciseId = cursor.getLong(cursor.getColumnIndex("_id"));
//			Exercise exercise = new Exercise(exerciseId, exerciseName);
//			if (exercisesChecked.containsKey(exerciseId)) {
//				Log.d("getExerciseBankList", "set selected TRUE");
//				list.add(get(exercise, workoutName, true));
//			} else {
//				list.add(get(exercise, workoutName, false));
//				Log.d("getExerciseBankList", "set selected FALSE");
//			}
//		}
//		mDbHelper.close();
		return list;
	}
	
	/**
	 * Hash table to get exercises that belong to workout
	 * in the get exercise method we check for collisions to see if exercise should be marked checked on load
	 * @return
	 */
	private Hashtable<Integer, Boolean> getCheckedExercises() {
		Hashtable<Integer, Boolean> exercises = new Hashtable<Integer, Boolean> ();
		String exerciseSequence = mDbHelper.getExerciseSequence(workoutName);
		StringTokenizer st = new StringTokenizer(exerciseSequence,",");
		while (st.hasMoreTokens()) {
			int exerciseId =  Integer.parseInt(st.nextToken());
			Exercise exercise = mDbHelper.getExerciseFromId(exerciseId);
			exercises.put(exerciseId, true);
		}
		return exercises;
		/** TODO
		 * Add selected parameter for get below and in checkbox adapter check the status of that var to 
		 * see if it should be checked or not
		 */
	}
	
	/**
	 * 
	 * @param exercise
	 * @param workoutName
	 * @param selectedStatus
	 * @return
	 */
	private ExerciseBankRow get(Exercise exercise, String workoutName, boolean selectedStatus) {
		return new ExerciseBankRow(exercise, workoutName, selectedStatus);
	}
	
	/**
	 * 
	 * @author sacrilley
	 *
	 */
	public interface onGoToExerciseSequenceListener {
		void goToExerciseSequence(String workoutName);
	}
	
	/**
	 * 
	 * @param listener
	 */
	public static void setExerciseSequenceListener(onGoToExerciseSequenceListener listener) {
		ExerciseBankFragment.listener = listener;
	}
		
		
}
