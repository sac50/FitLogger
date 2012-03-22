package com.cwru.fragments;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.ExerciseGoal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		if (exGoal.getMode() != ExerciseGoal.SPECIFIC_CARDIO_EXERCISE
				&& exGoal.getMode() != ExerciseGoal.SPECIFIC_STRENGTH_EXERCISE) {
			String[] modes = {"Run", "Swim", "Bike", "Ski", "Row/Paddle"};
			mode.setText(modes[exGoal.getMode()]);
		} else {
			String exName = mDbHelper.getExerciseFromId(exGoal.getExerciseId()).getName();
			mode.setText(exName);
		}
		
		TextView goal = (TextView) view.findViewById(R.id.tvViewExerciseGoalGoal);
		if (exGoal.getMode() == ExerciseGoal.SPECIFIC_STRENGTH_EXERCISE) {
			goal.setText("weight: " + exGoal.getGoalOne() + "   reps: " + (int) exGoal.getGoalTwo());
		} else {
			goal.setText(exGoal.getGoalOne() + " " + exGoal.getUnit());
		}
		
		return view;
	}
}