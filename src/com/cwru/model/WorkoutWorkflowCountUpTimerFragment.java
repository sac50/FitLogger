package com.cwru.model;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import com.cwru.R;

public class WorkoutWorkflowCountUpTimerFragment extends Fragment {

	private Chronometer chrTimer;
	private long startTime;
	private long countDown;
	private Exercise exercise;
	private Context context;
	
	public WorkoutWorkflowCountUpTimerFragment (Exercise exercise, Context context) {
		this.exercise = exercise;
		this.context = context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (LinearLayout) inflater.inflate(R.layout.workout_workflow_countdown_timer, container, false);
		chrTimer = (Chronometer) view.findViewById(R.id.chrWorkoutWorkflowCountdownTimer);
		chrTimer.setBase(100000);
		startTime = SystemClock.elapsedRealtime();
		chrTimer.start();
		return view;
	}
}
