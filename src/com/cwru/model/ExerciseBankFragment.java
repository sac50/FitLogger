package com.cwru.model;

import java.util.ArrayList;
import java.util.List;

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

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		View view = (LinearLayout) inflater.inflate(R.layout.exercise_bank, container, false);
		// Set DB Object
		mDbHelper = new DbAdapter(this.getActivity());
		
		CheckBoxArrayAdapter adapter = new CheckBoxArrayAdapter(this.getActivity(), getExerciseBankList(), this);
		
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
			// Replace the workout information fragment with the exercise bank
			transaction.replace(R.id.llWorkoutExerciseListingContainer, new ExerciseSequenceFragment(workoutName));	
			transaction.addToBackStack(null);
			transaction.commit();			
		}
		
	};
	
	private List<ExerciseBankRow> getExerciseBankList() {
		List<ExerciseBankRow> list = new ArrayList<ExerciseBankRow>();
		/* Query the DB to get the exercises available */
		mDbHelper.open();
		Cursor cursor = mDbHelper.getAllExercises();
		while (cursor.moveToNext()) {
			Log.d("Exercise name", cursor.getString(cursor.getColumnIndex("name")));
			String exerciseName = cursor.getString(cursor.getColumnIndex("name"));
			Long exerciseId = cursor.getLong(cursor.getColumnIndex("_id"));
			Exercise exercise = new Exercise(exerciseId, exerciseName);
			list.add(get(exercise, workoutName));
		}
		mDbHelper.close();
		return list;
	}
	
	private ExerciseBankRow get(Exercise exercise, String workoutName) {
		return new ExerciseBankRow(exercise, workoutName);
	}
		
		
}
