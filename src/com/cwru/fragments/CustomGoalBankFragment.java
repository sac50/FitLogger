package com.cwru.fragments;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.CustomGoal;
import com.cwru.model.CustomGoalArrayAdapter;
import com.cwru.model.ExerciseGoalArrayAdapter;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * 
 * @author lkissling
 *
 */
public class CustomGoalBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	CustomGoalArrayAdapter allAdapter;
	CustomGoalArrayAdapter incompleteAdapter;
	
	/**
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		View view = (LinearLayout) inflater.inflate(R.layout.goal_bank, container, false);
		
		CheckBox cb = (CheckBox) view.findViewById(R.id.cbShowCompletedGoals);
		cb.setOnCheckedChangeListener(listener);
		
		List<CustomGoal> allCustomGoals = new ArrayList<CustomGoal>();
		List<CustomGoal> incompleteCustomGoals = new ArrayList<CustomGoal>();
		CustomGoal goal = new CustomGoal();
		goal.setName("+ Add Goal");
		allCustomGoals.add(goal);
		incompleteCustomGoals.add(goal);
		
		List<CustomGoal> dbGoals = mDbHelper.getAllCustomGoals();
		for (CustomGoal i : dbGoals) {
			allCustomGoals.add(i);
			if (!i.getIsCompleted()) {
				incompleteCustomGoals.add(i);
			}
		}
		
		allAdapter = new CustomGoalArrayAdapter(this.getActivity(), allCustomGoals, this);
		incompleteAdapter = new CustomGoalArrayAdapter(this.getActivity(), incompleteCustomGoals, this);
		
		this.setListAdapter(incompleteAdapter);
		return view;
	}
	
	/**
	 * 
	 */
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