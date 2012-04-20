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

/**
 * Fragment that displays the available workouts in a list
 * @author sacrilley
 *
 */
public class WorkoutListingFragment extends ListFragment {	
	private DbAdapter mDbHelper;
	private int mode;
	private static onWorkoutListingClickListener listener;
	private static onReturnWorkoutListener listenerReturnWorkout;
	private boolean returnWorkout;

	/**
	 * Empty Constructor
	 */
	public WorkoutListingFragment(){
		Log.d("Steve", "Empty Constructor");
		returnWorkout = false;
	}
	
	/**
	 * 
	 * @param returnWorkout
	 */
	public WorkoutListingFragment(boolean returnWorkout) {
		this.returnWorkout = returnWorkout;
		Log.d("Steve", "Return Workout: " + returnWorkout);
	}
	
	
	@Override
	/**
	 * 
	 */
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
	/**
	 * 
	 */
	public void onResume() {
		super.onResume();
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getWorkoutList()));
	}
	
	@Override 
	/**
	 * 
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
		String workoutName = (String) getListAdapter().getItem(position);
		if (returnWorkout) {
			listenerReturnWorkout.onReturnWorkoutListenerClick(workoutName);
		} else {
			listener.onWorkoutListingListenerClick(workoutName);
		}
	}
	
	/**
	 * 
	 * @param workoutName
	 */
	private void goToWorkoutWorkflow(String workoutName) {
		Intent intent = new Intent(this.getActivity(), PerformWorkout.class);
		intent.putExtra("workoutName", workoutName);
		this.getActivity().startActivity(intent);
	}
	
	/**
	 * 
	 * @param workoutName
	 */
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

	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * 
	 * @author sacrilley
	 *
	 */
	public interface onReturnWorkoutListener {
		void onReturnWorkoutListenerClick(String workoutName);
	}
	
	/**
	 * 
	 * @param listener
	 */
	public static void setOnReturnWorkoutListener(onReturnWorkoutListener listener) {
		WorkoutListingFragment.listenerReturnWorkout = listener;
	}
	
	/**
	 * 
	 * @author sacrilley
	 *
	 */
	public interface onWorkoutListingClickListener {
		void onWorkoutListingListenerClick(String workoutName);
	}
	
	/**
	 * 
	 * @param listener
	 */
	public static void setOnWorkoutListingClickListener(onWorkoutListingClickListener listener) {
		WorkoutListingFragment.listener = listener;
	}
	
}
