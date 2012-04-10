package com.cwru.fragments;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.BodyGoal;
import com.cwru.model.BodyGoalArrayAdapter;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class BodyGoalBankFragment extends ListFragment {
	private DbAdapter mDbHelper;
	private BodyGoalArrayAdapter allAdapter;
	private BodyGoalArrayAdapter incompleteAdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		View view = (LinearLayout) inflater.inflate(R.layout.goal_bank, container, false);
		
		CheckBox cb = (CheckBox) view.findViewById(R.id.cbShowCompletedGoals);
		cb.setOnCheckedChangeListener(listener);
		
		List<BodyGoal> allBodyGoals = new ArrayList<BodyGoal>();
		List<BodyGoal> incompleteBodyGoals = new ArrayList<BodyGoal>();
		BodyGoal goal = new BodyGoal();
		goal.setCategory("+ Add Goal");
		allBodyGoals.add(goal);
		incompleteBodyGoals.add(goal);
		
		List<BodyGoal> dbGoals = mDbHelper.getAllBodyGoals();
		for (BodyGoal i : dbGoals) {
			if (!i.isCompleted()) {
				incompleteBodyGoals.add(i);
			}
			allBodyGoals.add(i);
		}
		
		allAdapter = new BodyGoalArrayAdapter(this.getActivity(), allBodyGoals, this);
		incompleteAdapter = new BodyGoalArrayAdapter(this.getActivity(), incompleteBodyGoals, this);
		
		setListAdapter(incompleteAdapter);
		
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