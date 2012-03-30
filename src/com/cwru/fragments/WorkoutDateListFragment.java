package com.cwru.fragments;

import android.content.Context;
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

public class WorkoutDateListFragment extends ListFragment {
	private DbAdapter mDbHelper;	
	private int workoutId;
	private static onWorkoutDateListingClickListener listener;
	
	public WorkoutDateListFragment(Context context, String workoutName) { 
		mDbHelper = new DbAdapter(context);
		this.workoutId = mDbHelper.getWorkoutIdFromName(workoutName);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}		
		View view = (LinearLayout) inflater.inflate(R.layout.workout_date_listings, container, false);
		// Get List content
		String [] workoutDates = getWorkoutDateList();
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, workoutDates));

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getWorkoutDateList()));
	}
	
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
		String date = (String) getListAdapter().getItem(position);
		listener.onWorkoutDateListingListenerClick(workoutId, date);
	}
	
	private String [] getWorkoutDateList() {
		return mDbHelper.getWorkoutDates(workoutId);
	}
	
	public interface onWorkoutDateListingClickListener {
		void onWorkoutDateListingListenerClick(int workoutId, String Date);
	}
	
	public static void setOnWorkoutDateListingClickListener(onWorkoutDateListingClickListener listener) {
		WorkoutDateListFragment.listener = listener;
	}

}
