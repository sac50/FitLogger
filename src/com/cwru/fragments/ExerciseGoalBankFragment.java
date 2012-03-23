package com.cwru.fragments;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.ExerciseGoal;
import com.cwru.model.ExerciseGoalArrayAdapter;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ExerciseGoalBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		View view = (LinearLayout) inflater.inflate(R.layout.exercise_goal_bank, container, false);
		
		List<ExerciseGoal> exerciseGoals = new ArrayList<ExerciseGoal>();
		ExerciseGoal goal = new ExerciseGoal();
		goal.setName("+ Add Goal");
		exerciseGoals.add(goal);
		
		List<ExerciseGoal> dbGoals = mDbHelper.getAllExerciseGoals();
		for (ExerciseGoal i : dbGoals) {
			exerciseGoals.add(i);
		}
		
		ExerciseGoalArrayAdapter adapter = new ExerciseGoalArrayAdapter(this.getActivity(), exerciseGoals, this);
		this.setListAdapter(adapter);
		
		return view;
	}
}