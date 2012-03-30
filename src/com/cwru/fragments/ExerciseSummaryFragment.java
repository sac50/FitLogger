package com.cwru.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.DistanceResult;
import com.cwru.model.Exercise;
import com.cwru.model.IntervalResult;
import com.cwru.model.SetResult;
import com.cwru.model.TimeResult;
import com.cwru.model.WorkoutResult;

public class ExerciseSummaryFragment extends Fragment {
	
	private DbAdapter mDbHelper;
	private int exerciseId;
	private ArrayList<WorkoutResult> workoutResultList;
	
	private TextView tvExerciseName;
	private TableLayout tlExerciseSummary;
	String exerciseName;
		
	public ExerciseSummaryFragment (Context context, int exerciseId) {
		mDbHelper = new DbAdapter(context);
		this.exerciseId = exerciseId;
		exerciseName = mDbHelper.getExerciseFromId(exerciseId).getName();
		workoutResultList = mDbHelper.getWorkoutResultsForExercise(exerciseId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (ScrollView) inflater.inflate(R.layout.exercise_summary, container, false);
		
		/* Get Layout Objects from xml layout file */
		tvExerciseName = (TextView) view.findViewById(R.id.tvExerciseSummaryExerciseName);
		tlExerciseSummary = (TableLayout) view.findViewById(R.id.tlExerciseSummaryTable);
		
		Log.d("Steve", "-----------------------------------------------------------");
		if (tlExerciseSummary == null)
			Log.d("Steve", "tlExerciseSummary NULL");
		
		/* Show ExerciseName Name */
		tvExerciseName.setText(exerciseName);
		/* Generate Workout Result Summary Table */
		//for (int i = 0; i < workoutResultList.size(); i++) {
		for (int i = 0; i < workoutResultList.size(); i++) {
			WorkoutResult workoutResult = workoutResultList.get(i);
			TextView tvExerciseDate = new TextView(ExerciseSummaryFragment.this.getActivity());
			tvExerciseDate.setText(workoutResult.getDate());
			TableRow trDate =  new TableRow(ExerciseSummaryFragment.this.getActivity());
			trDate.addView(tvExerciseDate);
			tlExerciseSummary.addView(trDate, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			switch (workoutResult.getMode()) {
				case WorkoutResult.DISTANCE_BASED_EXERCISE:
					ArrayList<DistanceResult> distanceResults = workoutResult.getDistanceResultList();
					for (int j = 0; j < distanceResults.size(); j++) {
						DistanceResult dr = distanceResults.get(j);
						TextView tvResult = new TextView(ExerciseSummaryFragment.this.getActivity());
						tvResult.setText("\t" + dr.getLength() + " " + dr.getUnits());
						TableRow trDistance = new TableRow(ExerciseSummaryFragment.this.getActivity());
						trDistance.addView(tvResult);
						tlExerciseSummary.addView(trDistance, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					}
					break;
					
				case WorkoutResult.INTERVAL_BASED_EXERCISE:
					ArrayList<IntervalResult> intervalResults = workoutResult.getIntervalResultList();
					int intervalCycleNum = -1;
					for (int j = 0; j < intervalResults.size(); j++) {
						IntervalResult ir = intervalResults.get(j);
						// Start of new interval set, show label
						if (ir.getIntervalSetNum() != intervalCycleNum) {
							TextView tvIntervalHeading = new TextView(ExerciseSummaryFragment.this.getActivity());
							tvIntervalHeading.setText("\tInterval #" + ir.getIntervalSetNum());
							TableRow trHeading = new TableRow(ExerciseSummaryFragment.this.getActivity());
							trHeading.addView(tvIntervalHeading);
							tlExerciseSummary.addView(trHeading, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
							intervalCycleNum++;
						}
						TextView tvIntervalResult = new TextView(ExerciseSummaryFragment.this.getActivity());
						tvIntervalResult.setText("\t\t" + ir.getName() + ": " + ir.getLength() + " " + ir.getUnits());
						TableRow trIntervalResult = new TableRow(ExerciseSummaryFragment.this.getActivity());
						trIntervalResult.addView(tvIntervalResult);
						tlExerciseSummary.addView(trIntervalResult, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					}
					break;
				case WorkoutResult.SET_BASED_EXERCISE:
					ArrayList<SetResult> setResults = workoutResult.getSetResultList();
					for (int j = 0; j < setResults.size(); j++) {
						SetResult sr = setResults.get(j);
						TextView tvResult = new TextView(ExerciseSummaryFragment.this.getActivity());
						tvResult.setText("\tSet " + sr.getSetNumber() + ": " + sr.getReps() + "x" + sr.getWeight());
						TableRow trSet = new TableRow(ExerciseSummaryFragment.this.getActivity());
						trSet.addView(tvResult);
						tlExerciseSummary.addView(trSet, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					}
					break;
				case WorkoutResult.TIME_BASED_EXERCISE:
					ArrayList<TimeResult> timeResults = workoutResult.getTimeResultList();
					for (int j = 0; j < timeResults.size(); j ++) {
						TimeResult tr = timeResults.get(j);
						TextView tvResult = new TextView(ExerciseSummaryFragment.this.getActivity());
						tvResult.setText("\t" + tr.getLength() + " " + tr.getUnits());
						TableRow trTime = new TableRow(ExerciseSummaryFragment.this.getActivity());
						trTime.addView(tvResult);
						tlExerciseSummary.addView(trTime, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					}
					break;
			}
		}		
		/** Construct Table */
		return view;
	}
	

}
