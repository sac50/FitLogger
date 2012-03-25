package com.cwru.fragments;

import java.util.ArrayList;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.DistanceResult;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseGoal;
import com.cwru.model.TimeResult;
import com.cwru.utils.MeasurementConversions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		
		TextView start = (TextView) view.findViewById(R.id.tvViewExerciseGoalStarted);
		TextView current = (TextView) view.findViewById(R.id.tvViewExerciseGoalCurrent);
		TextView goal = (TextView) view.findViewById(R.id.tvViewExerciseGoalGoal);
		
		Double val = 0.0;
		
		if (exGoal.getMode() != ExerciseGoal.SET) {
			String exType = new String();
			
			switch (exGoal.getType()) {
			
			case ExerciseGoal.RUN:
				exType = "Cardio - Run";
				break;
			case ExerciseGoal.SWIM:
				exType = "Cardio - Swim";
				break;
			case ExerciseGoal.BIKE:
				exType = "Cardio - Bike";
				break;
			case ExerciseGoal.SKI:
				exType = "Cardio - Ski";
				break;
			case ExerciseGoal.PADDLE:
				exType = "Cardio - Row/Paddle";
				break;
			default:
				break;
			}
		
			ArrayList<Exercise> exercises = new ArrayList<Exercise>();
			
			switch (exGoal.getMode()) {
			
			case ExerciseGoal.DISTANCE:
				if (exGoal.getExerciseId() > 0) {
					exercises.add(mDbHelper.getExerciseFromId(exGoal.getExerciseId()));
				} else {
					exercises = mDbHelper.getAllExercisesForTypeAndMode(exType, Exercise.DISTANCE_BASED_EXERCISE);
				}
				DistanceResult distanceResult = mDbHelper.getGreatestDistanceResult(exercises);
				val = MeasurementConversions.convert(distanceResult.getLength(), distanceResult.getUnits(), exGoal.getUnit());
				break;
			case ExerciseGoal.TIME:
				if (exGoal.getExerciseId() > 0) {
					exercises.add(mDbHelper.getExerciseFromId(exGoal.getExerciseId()));
				} else {
					exercises = mDbHelper.getAllExercisesForTypeAndMode(exType, Exercise.COUNTDOWN_BASED_EXERCISE);
					exercises.addAll(mDbHelper.getAllExercisesForTypeAndMode(exType, Exercise.COUNTUP_BASED_EXERCISE));
				}
				TimeResult timeResult= mDbHelper.getGreatestTimeResult(exercises);
				val = MeasurementConversions.convert(timeResult.getLength(), timeResult.getUnits(), exGoal.getUnit());
				break;
			default:
				break;
			}
		}
		
		if (exGoal.getType() == ExerciseGoal.SPECIFIC_STRENGTH_EXERCISE) {
			goal.setText("weight: " + exGoal.getGoalOne() + "   reps: " + (int) exGoal.getGoalTwo());
		} else {
			start.setText(exGoal.getStartingBestOne() + " " + exGoal.getUnit());
			current.setText(val + " " + exGoal.getUnit());
			goal.setText(exGoal.getGoalOne() + " " + exGoal.getUnit());	
		}
		
		ProgressBar progress = (ProgressBar) view.findViewById(R.id.pbViewExerciseGoal);
		int percent = (int) ((int) (val - exGoal.getStartingBestOne()) / (exGoal.getGoalOne() - exGoal.getStartingBestOne()) * 100);
		
		progress.setMax(100);
		progress.setProgress(percent);
		
		TextView tvProgress = (TextView) view.findViewById(R.id.tvViewExerciseGoalProgress);
		tvProgress.setText(String.valueOf(percent) + "%");
		
		return view;
	}
}