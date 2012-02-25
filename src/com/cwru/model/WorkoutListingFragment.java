package com.cwru.model;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.controller.WorkoutExerciseListing;
import com.cwru.dao.DbAdapter;

public class WorkoutListingFragment extends ListFragment {
	private DbAdapter mDbHelper;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}		
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
		EditWorkoutInformation editWorkoutInformation = new EditWorkoutInformation(workoutName, this.getActivity());
		editWorkoutInformation.setRetainInstance(true);
		// if tablet
		if (HomeScreen.isTablet) {
			FragmentTransaction transaction = WorkoutListingFragment.this.getFragmentManager().beginTransaction();
			transaction.replace(R.id.flEditWorkoutInformationRightFrame, editWorkoutInformation);
			transaction.commit();		
		}
		/* Launch intent to allow exercises to be added to workout and the sequence to be set */
		/*
		Intent intent = new Intent(WorkoutListingFragment.this.getActivity(), WorkoutExerciseListing.class);
		intent.putExtra("WorkoutName", workoutName);
		startActivity(intent);			
		*/
		
	}

	private String [] getWorkoutList() {
		ArrayList<String> workoutList = new ArrayList<String>();
		mDbHelper.open();
		Cursor cursor = mDbHelper.getAllWorkouts();
		while (cursor.moveToNext()) {
			workoutList.add(cursor.getString(cursor.getColumnIndex("name")));
		}
		mDbHelper.close();
		return workoutList.toArray(new String [0]);
	}
	
}
