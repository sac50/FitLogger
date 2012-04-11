package com.cwru.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;

public class CalendarScheduleWorkoutFragment extends Fragment {
	private DbAdapter mDbHelper;
	private String date;
	private Context context;
	private static onWorkoutListingClickListener listener;
	
	
	public CalendarScheduleWorkoutFragment(Context context) {
		this.context = context;
		mDbHelper = new DbAdapter(context);
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}		
		Log.d("Steve", "WorkoutListingFragment");
		View view = (ScrollView) inflater.inflate(R.layout.calendar_workout_schedule, container, false);

		return view;
	}
}
