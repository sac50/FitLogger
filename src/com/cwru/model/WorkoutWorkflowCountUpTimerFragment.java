package com.cwru.model;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.cwru.R;
import com.cwru.controller.HomeScreen;

public class WorkoutWorkflowCountUpTimerFragment extends Fragment {

	private Chronometer chrTimer;
	private long startTime;
	private long countDown;
	private Exercise exercise;
	private Context context;
	private boolean stop;
	private boolean initializedTimer; // so timer gets set only once
	private Button btnStartStop;
	private long baseTime;
	private long stopTime;
	
	public WorkoutWorkflowCountUpTimerFragment (Exercise exercise, Context context) {
		this.exercise = exercise;
		this.context = context;
		stop = true;
		initializedTimer = false;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (LinearLayout) inflater.inflate(R.layout.workout_workflow_countup_timer, container, false);
		btnStartStop = (Button) view.findViewById(R.id.btnWorkoutWorkflowCountUpTimerStartPause);
		if (btnStartStop == null) { Log.d("STEVE", "Button null"); }
		btnStartStop.setOnClickListener(updateTimer);
		// if phone show notes and history button
		if (!HomeScreen.isTablet) {
			TableLayout tl = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowCountUpSetHistoryNoteRow);
			Button btnHistory = new Button(this.getActivity());
			Button btnNotes = new Button(this.getActivity());
			btnHistory.setText("History");
			btnNotes.setText("Notes");
			
			TableRow tr = new TableRow(WorkoutWorkflowCountUpTimerFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tr.addView(btnHistory);
			tr.addView(btnNotes);
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
		}
		
		
		
		chrTimer = (Chronometer) view.findViewById(R.id.chrWorkoutWorkflowCountUpTimer);
		chrTimer.setText("00:00:00");
		chrTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

	        @Override
	        public void onChronometerTick(Chronometer chronometer) {
	            CharSequence text = chronometer.getText();
	            if (text.length()  == 5) {
	                chronometer.setText("00:"+text);
	            } else if (text.length() == 7) {
	                chronometer.setText("0"+text);
	            }
	        }
	    });
		//chrTimer.setFormat("Formatted time (%s)");
		return view;
	}
	
	View.OnClickListener updateTimer = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!initializedTimer) {
				initializedTimer = true;
				stopTime = 0;
				chrTimer.setBase(SystemClock.elapsedRealtime());
				//baseTime = SystemClock.elapsedRealtime();
				btnStartStop.setText("Pause");
				stop = false;
				chrTimer.start();
			} else if (stop) {
				stop = false;
				btnStartStop.setText("Pause");
				chrTimer.setBase(chrTimer.getBase() + SystemClock.elapsedRealtime() - stopTime);
				chrTimer.start();				
			}
			else {
				stop = true;
				stopTime = SystemClock.elapsedRealtime();
				btnStartStop.setText("Start");
				//stop = chrTimer.getBase();
				
				chrTimer.stop();
			}

		}
		
	};
}
