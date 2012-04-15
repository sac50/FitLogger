package com.cwru.fragments;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.BodyGoal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ViewBodyGoalFragment extends Fragment {
	DbAdapter mDbHelper;
	BodyGoal goal;
	View view;
	EditText currentText;
	
	/**
	 * Constructor accepting an body goal
	 * 
	 * @param BodyGoal goal
	 */
	public ViewBodyGoalFragment(BodyGoal goal) {
		this.goal = goal;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		view = inflater.inflate(R.layout.view_body_goal, container, false);
		
		TextView categoryText = (TextView) view.findViewById(R.id.tvViewBodyGoalCategory);
		categoryText.setText(goal.getCategory());
		
		TextView startedText = (TextView) view.findViewById(R.id.tvViewBodyGoalStarted);
		startedText.setText(goal.getStarted() + " " + goal.getUnit());
		double started = goal.getStarted();
		
		currentText = (EditText) view.findViewById(R.id.etViewBodyGoalCurrent);
		currentText.setText(Double.toString(goal.getCurrent()));
		double current = goal.getCurrent();
		
		TextView endText = (TextView) view.findViewById(R.id.tvViewBodyGoalGoal);
		endText.setText(goal.getGoal() + " " + goal.getUnit());
		double end = goal.getGoal();
		
		ProgressBar pb = (ProgressBar) view.findViewById(R.id.pbViewBodyGoal);
		pb.setMax(100);
		
		int percent;
		if (started < end) {
			percent = (int) ((current - started) / (end - started) * 100);
		} else {
			percent = (int) ((started - current) / (started - end) * 100);
		}
		pb.setProgress(percent);
		
		TextView progressText = (TextView) view.findViewById(R.id.tvViewBodyGoalProgress);
		progressText.setText(percent + "%");
		
		Button update = (Button) view.findViewById(R.id.btnViewBodyGoalUpdate);
		update.setOnClickListener(updateListener);
		
		Button delete = (Button) view.findViewById(R.id.btnViewBodyGoalDelete);
		delete.setOnClickListener(deleteListener);
		
		return view;
	}
	
	View.OnClickListener updateListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Context context = v.getContext();
			CharSequence toastText;
			int duration = Toast.LENGTH_SHORT;
			
			String currentString = currentText.getText().toString();
			if (currentString == null || currentString.length() <= 0) {
				toastText = "Please specify a current value.";
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			
			goal.setCurrent(Double.parseDouble(currentString));
			
			if (goal.getStarted() < goal.getGoal() && goal.getCurrent() >= goal.getGoal()
					|| goal.getStarted() > goal.getGoal() && goal.getCurrent() <= goal.getGoal()) {
				goal.setCompleted(true);
			} else {
				goal.setCompleted(false);
			}
			
			mDbHelper.updateBodyGoal(goal);
			
			FragmentManager manager = ViewBodyGoalFragment.this.getFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			BodyGoalBankFragment newFrag = new BodyGoalBankFragment();
			
			manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			
			if (HomeScreen.isTablet) {
				transaction.replace(R.id.flGoalLeftFrame, newFrag);
				transaction.remove(ViewBodyGoalFragment.this);
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
			mDbHelper.deleteBodyGoal(goal.getId());
			
			FragmentManager manager = ViewBodyGoalFragment.this.getFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			BodyGoalBankFragment newFrag = new BodyGoalBankFragment();
			
			manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			
			if (HomeScreen.isTablet) {
				transaction.replace(R.id.flGoalLeftFrame, newFrag);
				transaction.remove(ViewBodyGoalFragment.this);
				transaction.commit();
			} else {
				transaction.replace(R.id.flGoalMainFrame, newFrag);
				transaction.commit();
			}
		}
	};
}