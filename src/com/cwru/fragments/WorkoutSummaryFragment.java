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
import com.cwru.model.IntervalResult;
import com.cwru.model.SetResult;
import com.cwru.model.TimeResult;
import com.cwru.model.WorkoutResult;
;

public class WorkoutSummaryFragment extends Fragment {

	private DbAdapter mDbHelper;
	private int workoutId;
	private String date;
	private String workoutName;
	private ArrayList<WorkoutResult> workoutResultList;
	
	private TableLayout tlWorkoutSummary;
	private TextView tvWorkoutName;
	
	

	public WorkoutSummaryFragment (Context context, int workoutId, String date) {
		mDbHelper = new DbAdapter(context);
		this.workoutId = workoutId;
		this.date = date;
		workoutName = mDbHelper.getWorkoutFromId(workoutId).getName();
		workoutResultList = mDbHelper.getWorkoutResultForWorkout(workoutId, date);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (ScrollView) inflater.inflate(R.layout.workout_summary, container, false);
		
		/* Get Layout Objects from xml layout file */
		tvWorkoutName = (TextView) view.findViewById(R.id.tvWorkoutSummaryWorkoutName);
		tlWorkoutSummary = (TableLayout) view.findViewById(R.id.tlWorkoutSummaryTable);
		
		/* Show Workout Name */
		tvWorkoutName.setText(workoutName);
		Log.d("STeve", "Set Layout Name" + workoutName);
		/* Generate Workout Result Summary Table */
		//for (int i = 0; i < workoutResultList.size(); i++) {
		for (int i = 0; i < workoutResultList.size(); i++) {
			WorkoutResult workoutResult = workoutResultList.get(i);
			String exerciseName = mDbHelper.getExerciseFromId(workoutResult.getExerciseId()).getName();
			
			switch (workoutResult.getMode()) {
				case WorkoutResult.DISTANCE_BASED_EXERCISE:
					TableRow trD = new TableRow(WorkoutSummaryFragment.this.getActivity());
					TextView tvExerciseDistanceName = new TextView(WorkoutSummaryFragment.this.getActivity());
					tvExerciseDistanceName.setText(exerciseName);
					trD.addView(tvExerciseDistanceName);
					tlWorkoutSummary.addView(trD, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					ArrayList<DistanceResult> distanceResults = workoutResult.getDistanceResultList();
					for (int j = 0; j < distanceResults.size(); j++) {
						DistanceResult dr = distanceResults.get(j);
						TextView tvResult = new TextView(WorkoutSummaryFragment.this.getActivity());
						tvResult.setText("\t" + dr.getLength() + " " + dr.getUnits());
						TableRow trDistance = new TableRow(WorkoutSummaryFragment.this.getActivity());
						trDistance.addView(tvResult);
						tlWorkoutSummary.addView(trDistance, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					}
					break;
					
				case WorkoutResult.INTERVAL_BASED_EXERCISE:
					TableRow trI = new TableRow(WorkoutSummaryFragment.this.getActivity());
					TextView tvExerciseIntervalName = new TextView(WorkoutSummaryFragment.this.getActivity());
					tvExerciseIntervalName.setText(exerciseName);
					trI.addView(tvExerciseIntervalName);
					tlWorkoutSummary.addView(trI, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					ArrayList<IntervalResult> intervalResults = workoutResult.getIntervalResultList();
					int intervalCycleNum = -1;
					for (int j = 0; j < intervalResults.size(); j++) {
						IntervalResult ir = intervalResults.get(j);
						// Start of new interval set, show label
						if (ir.getIntervalSetNum() != intervalCycleNum) {
							TextView tvIntervalHeading = new TextView(WorkoutSummaryFragment.this.getActivity());
							tvIntervalHeading.setText("\tInterval #" + ir.getIntervalSetNum());
							TableRow trHeading = new TableRow(WorkoutSummaryFragment.this.getActivity());
							trHeading.addView(tvIntervalHeading);
							tlWorkoutSummary.addView(trHeading, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
							intervalCycleNum++;
						}
						TextView tvIntervalResult = new TextView(WorkoutSummaryFragment.this.getActivity());
						tvIntervalResult.setText("\t\t" + ir.getName() + ": " + ir.getLength() + " " + ir.getUnits());
						TableRow trIntervalResult = new TableRow(WorkoutSummaryFragment.this.getActivity());
						trIntervalResult.addView(tvIntervalResult);
						tlWorkoutSummary.addView(trIntervalResult, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					}
					break;
				case WorkoutResult.SET_BASED_EXERCISE:
					TableRow trS = new TableRow(WorkoutSummaryFragment.this.getActivity());
					TextView tvExerciseSetName = new TextView(WorkoutSummaryFragment.this.getActivity());
					tvExerciseSetName.setText(exerciseName);
					trS.addView(tvExerciseSetName);
					tlWorkoutSummary.addView(trS, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					ArrayList<SetResult> setResults = workoutResult.getSetResultList();
					for (int j = 0; j < setResults.size(); j++) {
						SetResult sr = setResults.get(j);
						TextView tvResult = new TextView(WorkoutSummaryFragment.this.getActivity());
						tvResult.setText("\tSet " + sr.getSetNumber() + ": " + sr.getReps() + "x" + sr.getWeight());
						TableRow trSet = new TableRow(WorkoutSummaryFragment.this.getActivity());
						trSet.addView(tvResult);
						tlWorkoutSummary.addView(trSet, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					}
					break;
				case WorkoutResult.TIME_BASED_EXERCISE:
					TableRow trT = new TableRow(WorkoutSummaryFragment.this.getActivity());
					TextView tvExerciseTimeName = new TextView(WorkoutSummaryFragment.this.getActivity());
					tvExerciseTimeName.setText(exerciseName);
					trT.addView(tvExerciseTimeName);
					tlWorkoutSummary.addView(trT, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					ArrayList<TimeResult> timeResults = workoutResult.getTimeResultList();
					for (int j = 0; j < timeResults.size(); j ++) {
						TimeResult tr = timeResults.get(j);
						TextView tvResult = new TextView(WorkoutSummaryFragment.this.getActivity());
						tvResult.setText("\t" + tr.getLength() + " " + tr.getUnits());
						TableRow trTime = new TableRow(WorkoutSummaryFragment.this.getActivity());
						trTime.addView(tvResult);
						tlWorkoutSummary.addView(trTime, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					}
					break;
					
			}
		
		}
		
		
		/** Construct Table */
		return view;
	}
}
