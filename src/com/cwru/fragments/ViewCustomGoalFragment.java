package com.cwru.fragments;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.CustomGoal;
import com.cwru.utils.GraphBuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class ViewCustomGoalFragment extends Fragment {
	DbAdapter mDbHelper;
	CustomGoal goal;
	View view;
	
	/**
	 * Constructor accepting a custom goal
	 * 
	 * @param CustomGoal goal
	 */
	public ViewCustomGoalFragment(CustomGoal goal) {
		this.goal = goal;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		view = inflater.inflate(R.layout.view_custom_goal, container, false);
		
		TextView name = (TextView) view.findViewById(R.id.tvCustomGoalName);
		name.setText(goal.getName());
		
		TextView description = (TextView) view.findViewById(R.id.tvCustomGoalDescription);
		description.setText(goal.getDescription());
		
		Button completed = (Button) view.findViewById(R.id.btnCustomGoalCompleted);
		completed.setEnabled(!goal.getIsCompleted());
		completed.setOnClickListener(completedListener);
		
		Button delete = (Button) view.findViewById(R.id.btnCustomGoalDelete);
		delete.setOnClickListener(deleteListener);
		
		return view;
	}
	
	View.OnClickListener completedListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			goal.setIsCompleted(true);
			mDbHelper.updateCustomGoal(goal);
			
			FragmentManager manager = ViewCustomGoalFragment.this.getFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			CustomGoalBankFragment newFrag = new CustomGoalBankFragment();
			
			manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			
			if (HomeScreen.isTablet) {
				transaction.replace(R.id.flGoalLeftFrame, newFrag);
				transaction.remove(ViewCustomGoalFragment.this);
				transaction.commit();
			} else {
				transaction.replace(R.id.flGoalMainFrame, newFrag);
				transaction.commit();
			}
		}
	};
	
	View.OnClickListener deleteListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mDbHelper.deleteCustomGoal(goal.getId());
			
			FragmentManager manager = ViewCustomGoalFragment.this.getFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			CustomGoalBankFragment newFrag = new CustomGoalBankFragment();
			
			manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			
			if (HomeScreen.isTablet) {
				transaction.replace(R.id.flGoalLeftFrame, newFrag);
				transaction.remove(ViewCustomGoalFragment.this);
				transaction.commit();
			} else {
				transaction.replace(R.id.flGoalMainFrame, newFrag);
				transaction.commit();
			}
		}
	};
}