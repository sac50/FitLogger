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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class ExerciseGoalBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	ExerciseGoalArrayAdapter allAdapter;
	ExerciseGoalArrayAdapter incompleteAdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		View view = (LinearLayout) inflater.inflate(R.layout.exercise_goal_bank, container, false);
		
		CheckBox cb = (CheckBox) view.findViewById(R.id.cbShowCompletedExGoals);
		cb.setOnCheckedChangeListener(listener);
		
		List<ExerciseGoal> allExerciseGoals = new ArrayList<ExerciseGoal>();
		List<ExerciseGoal> incompleteExerciseGoals = new ArrayList<ExerciseGoal>();
		ExerciseGoal goal = new ExerciseGoal();
		goal.setName("+ Add Goal");
		allExerciseGoals.add(goal);
		incompleteExerciseGoals.add(goal);
		
		List<ExerciseGoal> dbGoals = mDbHelper.getAllExerciseGoals();
		for (ExerciseGoal i : dbGoals) {
			allExerciseGoals.add(i);
			if (!i.getIsCompleted()){
				incompleteExerciseGoals.add(i);
			}
		}
		
		allAdapter = new ExerciseGoalArrayAdapter(this.getActivity(), allExerciseGoals, this);
		incompleteAdapter = new ExerciseGoalArrayAdapter(this.getActivity(), incompleteExerciseGoals, this);
		this.setListAdapter(incompleteAdapter);
		
		return view;
	}
	
	CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				setListAdapter(allAdapter);
			} else {
				setListAdapter(incompleteAdapter);
			}
		}
	};
	
}