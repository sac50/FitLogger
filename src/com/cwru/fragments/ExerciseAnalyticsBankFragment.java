package com.cwru.fragments;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;

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

/**
 * 
 * @author lkissling
 *
 */
public class ExerciseAnalyticsBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	ArrayList<Exercise> exercises;
	
	@Override
	/**
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		exercises = new ArrayList<Exercise>();
	    ArrayList<Exercise> all = mDbHelper.getAllUndeletedExercises();
	   
		View view = (LinearLayout) inflater.inflate(R.layout.exercise_bank, container, false);
		
		for (int i = 0; i < all.size(); i++) {
			if (mDbHelper.getExerciseMode(all.get(i).getId()) != Exercise.INTERVAL_BASED_EXERCISE) {
				exercises.add(all.get(i));
			}
		}
		
		List<String> names = getExerciseNames(exercises);
		
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, names));
		
		Log.d("LOWELL", "Exercise Analytics bank fragment onCreateView");
		
		return view;
	}
	
	@Override 
	/**
	 * 
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
		FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
		ExerciseAnalyticsFragment newFrag = new ExerciseAnalyticsFragment(exercises.get(position));
		
		if (!HomeScreen.isTablet) {
			transaction.remove(this);
			transaction.replace(R.id.flAnalyticsMainFrame, newFrag);
			transaction.addToBackStack(null);
			transaction.commit();
		} else {
			transaction.replace(R.id.flAnalyticsRightFrame, newFrag);
			transaction.commit();
		}
	}
	
	/**
	 * 
	 * @param exercises
	 * @return
	 */
	private ArrayList<String> getExerciseNames(ArrayList<Exercise> exercises) {
		ArrayList<String> strings = new ArrayList<String>();
		for (Exercise ex : exercises) {
			strings.add(ex.getName());
		}
		return strings;
	}
}