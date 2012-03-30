package com.cwru.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;
import com.cwru.model.Exercise;

public class ExerciseListingFragment extends ListFragment {
	private DbAdapter mDbHelper;
	private int mode;
	private static onExerciseListingClickListener listener;

	public ExerciseListingFragment(){
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}		
		Log.d("Steve", "WorkoutListingFragment");
		View view = (LinearLayout) inflater.inflate(R.layout.workout_listings, container, false);
		// Set Adapter
		mDbHelper = new DbAdapter(this.getActivity());
		// Get List content
		String [] exercises = getExerciseList();
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, exercises));

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getExerciseList()));
	}
	
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
		String exerciseName = (String) getListAdapter().getItem(position);
		int exerciseId = mDbHelper.getExerciseFromName(exerciseName).getId();
		//listener.onWorkoutListingListenerClick(workoutName);
		listener.onExerciseListingListenerClick(exerciseId);
	}

	public String [] getExerciseList() {
		ArrayList<Exercise> exerciseList = mDbHelper.getAllUndeletedExercises();
		String [] exerciseNames = new String [exerciseList.size()];
		for (int i = 0; i < exerciseList.size(); i++) {
			exerciseNames[i] = exerciseList.get(i).getName();
		}
		return exerciseNames;
	}
	
	
	public interface onExerciseListingClickListener {
		void onExerciseListingListenerClick(int exerciseName);
	}
	
	public static void setOnExerciseListingClickListener(onExerciseListingClickListener listener) {
		ExerciseListingFragment.listener = listener;
	}
	
	
	

}
