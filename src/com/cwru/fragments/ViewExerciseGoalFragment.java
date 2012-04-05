package com.cwru.fragments;

import java.util.ArrayList;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.DistanceResult;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseGoal;
import com.cwru.model.TimeResult;
import com.cwru.utils.MeasurementConversions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewExerciseGoalFragment extends Fragment {
	private DbAdapter mDbHelper;
	private ExerciseGoal exGoal;
	private View view;
	
	/**
	 * Constructor accepting an exercise goal
	 * 
	 * @param exGoal
	 */
	public ViewExerciseGoalFragment(ExerciseGoal exGoal) {
		this.exGoal = exGoal;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		view = inflater.inflate(R.layout.view_exercise_goal, container, false);
		
		TextView summary = (TextView) view.findViewById(R.id.tvViewExerciseGoalSummary);
		summary.setText(exGoal.getName());
		
		TextView mode = (TextView) view.findViewById(R.id.tvViewExerciseGoalMode);
		if (exGoal.getType() != ExerciseGoal.SPECIFIC_CARDIO_EXERCISE
				&& exGoal.getType() != ExerciseGoal.SPECIFIC_STRENGTH_EXERCISE) {
			String[] modes = {"Run", "Swim", "Bike", "Ski", "Row/Paddle"};
			mode.setText(modes[exGoal.getType()]);
		} else {
			String exName = mDbHelper.getExerciseFromId(exGoal.getExerciseId()).getName();
			mode.setText(exName);
		}
		
		TextView current = (TextView) view.findViewById(R.id.tvViewExerciseGoalCurrent);
		TextView currentLabel = (TextView) view.findViewById(R.id.tvViewExerciseGoalCurrentLabel);
		
		TextView goal = (TextView) view.findViewById(R.id.tvViewExerciseGoalGoal);
		
		Double val = 0.0;
		
		ProgressBar progress = (ProgressBar) view.findViewById(R.id.pbViewExerciseGoal);
		TextView tvProgress = (TextView) view.findViewById(R.id.tvViewExerciseGoalProgress);
		
		if (exGoal.getType() == ExerciseGoal.SPECIFIC_STRENGTH_EXERCISE) {
			if (exGoal.getIsCumulative()) {
				currentLabel.setText("Total weight: ");
				current.setText(Double.toString(exGoal.getCurrentBestOne()));
				goal.setText(Double.toString(exGoal.getGoalOne()));
			} else {
				current.setText("weight: " + exGoal.getCurrentBestOne() + "   reps: " + (int) exGoal.getCurrentBestTwo());
				goal.setText("weight: " + exGoal.getGoalOne() + "   reps: " + (int) exGoal.getGoalTwo());
				progress.setVisibility(8);
				tvProgress.setVisibility(8);
			}
		} else {
			if (exGoal.getMode() == ExerciseGoal.DISTANCE && exGoal.getIsCumulative()) {
				currentLabel.setText("Total distance: ");
			} else if (exGoal.getMode() == ExerciseGoal.TIME && exGoal.getIsCumulative()) {
				currentLabel.setText("Total time: ");
			}
			current.setText(exGoal.getCurrentBestOne() + " " + exGoal.getUnit());
			goal.setText(exGoal.getGoalOne() + " " + exGoal.getUnit());	
		}
		
		int percent = (int) (exGoal.getCurrentBestOne() / exGoal.getGoalOne() * 100);
		
		progress.setMax(100);
		progress.setProgress(percent);
		
		tvProgress.setText(String.valueOf(percent) + "%");
		
		Button delete = (Button) view.findViewById(R.id.btnViewExerciseGoalDelete);
		delete.setOnClickListener(deleteListener);
		
		return view;
	}
	
	View.OnClickListener deleteListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mDbHelper.deleteExerciseGoal(exGoal.getId());
			
			if (HomeScreen.isTablet) {
				FragmentTransaction transaction = ViewExerciseGoalFragment.this.getFragmentManager().beginTransaction();
				ExerciseGoalBankFragment newFrag = new ExerciseGoalBankFragment();
				
				transaction.replace(R.id.flExerciseGoalLeftFrame, newFrag);
				transaction.remove(ViewExerciseGoalFragment.this);
				transaction.commit();
			} else {
				FragmentTransaction transaction = ViewExerciseGoalFragment.this.getFragmentManager().beginTransaction();
				ExerciseGoalBankFragment newFrag = new ExerciseGoalBankFragment();
				
				transaction.replace(R.id.flExerciseGoalMainFrame, newFrag);
				transaction.commit();
			}
		}
	};
}