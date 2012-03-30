package com.cwru.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.controller.PerformWorkout;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.ExerciseBankFragment.onGoToExerciseSequenceListener;
import com.cwru.model.Workout;

public class WorkoutListingFragment extends ListFragment {	
	private DbAdapter mDbHelper;
	private int mode;
	private static onWorkoutListingClickListener listener;

	public WorkoutListingFragment(){
		
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
		String [] workouts = getWorkoutList();
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, workouts));

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getWorkoutList()));
	}
	
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
		String workoutName = (String) getListAdapter().getItem(position);
		Log.d("Steve", "Workout Name: " + workoutName);
		listener.onWorkoutListingListenerClick(workoutName);
	}
	
	private void goToWorkoutWorkflow(String workoutName) {
		Intent intent = new Intent(this.getActivity(), PerformWorkout.class);
		intent.putExtra("workoutName", workoutName);
		this.getActivity().startActivity(intent);
	}
	
	
	
	private void goToEditWorkoutInformation(String workoutName) {
		EditWorkoutInformationFragment editWorkoutInformation = new EditWorkoutInformationFragment(workoutName, this.getActivity());
		editWorkoutInformation.setRetainInstance(true);
		// if tablet
		if (HomeScreen.isTablet) {
			FragmentTransaction transaction = WorkoutListingFragment.this.getFragmentManager().beginTransaction();
			transaction.replace(R.id.flEditWorkoutInformationRightFrame, editWorkoutInformation);
			transaction.commit();		
		} else {
			FragmentTransaction transaction = WorkoutListingFragment.this.getFragmentManager().beginTransaction();
			transaction.addToBackStack(null);
			transaction.replace(R.id.flEditWorkoutInformationMainFrame, editWorkoutInformation);
			transaction.commit();
		}
	}

	private String [] getWorkoutList() {
		Log.d("Steve", "getWorkoutList");
		ArrayList<String> workoutList = new ArrayList<String>();
		Workout [] workouts = mDbHelper.getAllWorkouts();
		// Get array of workout names for list
		for (int i = 0; i < workouts.length; i++) { 
			workoutList.add(workouts[i].getName());
			Log.d("Steve", "Workout name " + workouts[i].getName());
		}
		return workoutList.toArray(new String [0]);
	}
	
	public interface onWorkoutListingClickListener {
		void onWorkoutListingListenerClick(String workoutName);
	}
	
	public static void setOnWorkoutListingClickListener(onWorkoutListingClickListener listener) {
		WorkoutListingFragment.listener = listener;
	}
	
}
