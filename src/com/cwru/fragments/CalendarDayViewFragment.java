package com.cwru.fragments;

import java.util.ArrayList;
import java.util.Calendar;

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

public class CalendarDayViewFragment extends ListFragment {
	private DbAdapter mDbHelper;
	private String date;
	private Context context;
	private static onWorkoutListingClickListener listener;
	
	
	public CalendarDayViewFragment(Context context, String date) {
		this.date = date;
		this.context = context;
		mDbHelper = new DbAdapter(context);
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}		
		Log.d("Steve", "WorkoutListingFragment");
		View view = (LinearLayout) inflater.inflate(R.layout.workout_listings, container, false);
		// Set Adapter
		// Get List content
		//String [] workouts = getWorkoutList();
		ArrayList<String> workouts = new ArrayList<String>();
		workouts.add("Schedule a workout...");
		workouts.addAll(mDbHelper.getWorkoutsForDate(date));
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, workouts));

		return view;
	}
	
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
		String workoutName = (String) getListAdapter().getItem(position);
		Log.d("Steve", "Workout Name: " + workoutName);
		listener.onWorkoutListingListenerClick(workoutName);
	}
	
	private boolean dateInPast() {
		String [] dateSplit = date.split("/");
		int y = Integer.parseInt(dateSplit[0]);
		int m = Integer.parseInt(dateSplit[1]);
		int d = Integer.parseInt(dateSplit[2]);
		
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.YEAR) < y &&(calendar.get(Calendar.MONTH) + 1) < m && calendar.get(Calendar.DAY_OF_MONTH) < d) {
			return true;
		}
		return false;		
	}
	
	public static void setOnWorkoutListingClickListener(onWorkoutListingClickListener listener) {
		CalendarDayViewFragment.listener = listener;
	}
}
