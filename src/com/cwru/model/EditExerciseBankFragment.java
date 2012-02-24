package com.cwru.model;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;

public class EditExerciseBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	private List<Exercise> exercises = new ArrayList<Exercise>();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		View view = (LinearLayout) inflater.inflate(R.layout.exercise_bank, container, false);
		// Set DB Object
		mDbHelper = new DbAdapter(this.getActivity());
		mDbHelper.open();
		Cursor cursor = mDbHelper.getAllExercises();
		while (cursor.moveToNext()) {
			Log.d("Exercise name", cursor.getString(cursor.getColumnIndex("name")));
			String exerciseName = cursor.getString(cursor.getColumnIndex("name"));
			Long exerciseId = cursor.getLong(cursor.getColumnIndex("_id"));
			Exercise exercise = new Exercise(exerciseId, exerciseName);
			exercises.add(exercise);
		}
		cursor.close();
		mDbHelper.close();
		
		ExerciseArrayAdapter adapter = new ExerciseArrayAdapter(this.getActivity(), exercises, this);
		this.setListAdapter(adapter);
		
		return view;
	}
}