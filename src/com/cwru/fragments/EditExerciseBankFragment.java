package com.cwru.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseArrayAdapter;

public class EditExerciseBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
	    List<Exercise> exercises = new ArrayList<Exercise>();

		View view = (LinearLayout) inflater.inflate(R.layout.exercise_bank, container, false);
		
		exercises = mDbHelper.getAllUndeletedExercises();
		Log.d("LOWELL", "Exercise bank fragment onCreateView");
		
		ExerciseArrayAdapter adapter = new ExerciseArrayAdapter(this.getActivity(), exercises, this);
		this.setListAdapter(adapter);
		
//		View list = (ListView) view.findViewById(android.R.id.list);
//		View selection = (TextView) list.findViewById(R.id.tvEBRExerciseLabel);
//		selection.setOnClickListener(editExerciseListener);
		
//		view.setOnClickListener(editExerciseListener);
		
		return view;
	}
	
	View.OnClickListener editExerciseListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			TextView etExName = (TextView) v.findViewById(R.id.tvEBRExerciseLabel);
			String exName = etExName.getText().toString();
			
			Exercise ex;
			
			try {
				mDbHelper.open();
				ex = mDbHelper.getExerciseFromName(exName);
			} finally {
				mDbHelper.close();
			}
			
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			EditExerciseFragment fragment = new EditExerciseFragment(ex);
			
			if (!HomeScreen.isTablet) {
				transaction.replace(R.id.flEditExerciseMainFrame, fragment);	
				transaction.addToBackStack(null);
				transaction.commit();
			} else {
				transaction.replace(R.id.flEditExerciseRightFrame, fragment);
				transaction.commit();
			}
			
//			Intent intent = new Intent(EditExerciseBankFragment.this.getActivity(), EditExercise.class);
//			intent.putExtra("exercise", ex);
//			startActivity(intent);
		}
	};
}