package com.cwru.model;

import com.cwru.R;
import com.cwru.dao.DbAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class WorkoutWorkflowSetFragment extends Fragment {
	private DbAdapter mDbHelper;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;			
		}
		View view =  (ScrollView) inflater.inflate(R.layout.workout_workflow_set, container, false);
		// Set DB Object
		mDbHelper = new DbAdapter(this.getActivity());
		
		/* Get information to populate view */
		/* Get workout % done */
		
		/* Get Exercise Name */
		
		/* Get Reps to do */
		
		
		return view;
	}
}
