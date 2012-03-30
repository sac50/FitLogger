package com.cwru.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.Time;
import com.cwru.model.TimeResult;
import com.cwru.model.WorkoutResult;

public class WorkoutWorkflowCountDownTimerFragment extends Fragment{
	private CountDownTimer start;
	private DbAdapter mDbHelper;
	private Exercise exercise;
	private long seconds;
	private long minutes;
	private boolean complete;
	private boolean stop;
	private long time;
	private int workoutId;
	private TableLayout tlResults;
	
	private int secondsInCountdown;
	private TextView tvTimer;
	private TextView tvExerciseName;
	private Button btnStartStop;
	private Button btnRecord;
	
	public WorkoutWorkflowCountDownTimerFragment (Exercise exercise, Context context, int workoutId) {
		mDbHelper = new DbAdapter(context);
		this.exercise = exercise;
		this.workoutId = workoutId;
		complete = false;
		stop = true;

		Time exerciseTime = mDbHelper.getTimeForExercise(exercise.getId());
		
		String units = exerciseTime.getUnits();
		if (units.equals("seconds")) {
			time = exerciseTime.getLength();
			minutes = time / 60;
			seconds = time % 60;			
		} else if (units.equals("minutes")) {
			time = exerciseTime.getLength();
			minutes = time;
			seconds = 0;
		} 

		time = minutes*60 + seconds;
		secondsInCountdown = (int) time;
		time = time * 1000;
		Log.d("STEVE", " TIME: " + time);
		
		start = new CountDownTimer(time, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				decreaseTime();
				tvTimer.setText(getFormatedTime());
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
			}
		};	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (LinearLayout) inflater.inflate(R.layout.workout_workflow_countdown_timer, container, false);
		btnStartStop = (Button) view.findViewById(R.id.btnWorkoutWorkflowCountdownTimerStartPause);
		btnStartStop.setOnClickListener(updateTimer);
		btnRecord = (Button) view.findViewById(R.id.btnWorkoutWorkflowCountDownTimerRecord);
		btnRecord.setOnClickListener(recordTime);
		tvExerciseName = (TextView) view.findViewById(R.id.tvWorkoutWorkflowCountdownTimerExerciseName);
		tvExerciseName.setText(exercise.getName());
		tvTimer = (TextView) view.findViewById(R.id.tvWorkoutWorkflowCountdownTimerTimer);
		tvTimer.setText(getFormatedTime());
		tlResults = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowCountDownResults);
		
		if (!HomeScreen.isTablet) {
			TableLayout tl = (TableLayout) view.findViewById(R.id.tlWorkoutWorkflowCountDownHistoryNoteRow);
			Button btnHistory = new Button(this.getActivity());
			Button btnNotes = new Button(this.getActivity());
			btnHistory.setText("History");
			btnNotes.setText("Notes");
			
			TableRow tr = new TableRow(WorkoutWorkflowCountDownTimerFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tr.addView(btnHistory);
			tr.addView(btnNotes);
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));	
		}
		return view;
	}
	
	View.OnClickListener updateTimer = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (stop) {
				stop = false;
				btnStartStop.setText("Pause");
				start.start();				
			}
			else {
				stop = true;
				btnStartStop.setText("Start");
				start.cancel();
			}
		}
	};
	
	View.OnClickListener recordTime = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// stop countdown
			stop = true;
			btnStartStop.setText("Start");
			start.cancel();
			
			// get the time left on the countdown
			String timeLeft = tvTimer.getText().toString();
			int minutes = Integer.parseInt(timeLeft.substring(0, 2));
			int seconds = Integer.parseInt(timeLeft.substring(3,5));
			
			// time left in seconds
			int timeLeftSeconds = minutes * 60 + seconds;
			// compute time done
			int timeDone = secondsInCountdown - timeLeftSeconds;	
			int timeDoneMins = timeDone / 60;
			int timeDoneSecs = timeDone % 60;
			String timeDoneString = "";
			if (timeDoneMins < 10) { timeDoneString += "0" + timeDoneMins + ":"; }
			else { timeDoneString += timeDoneMins + ":"; }
			if (timeDoneSecs < 10) { timeDoneString += "0" + timeDoneSecs; }
			else { timeDoneString += timeDoneSecs; }
			
			/* Show recorded time on screen */
			TableRow tr = new TableRow(WorkoutWorkflowCountDownTimerFragment.this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tvTimeRecord = new TextView(WorkoutWorkflowCountDownTimerFragment.this.getActivity());
			tvTimeRecord.setText("Time recorded: " + timeDoneString);
			tr.addView(tvTimeRecord);

			tlResults.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			/* Generate Workout Result Row in Database */
			Log.d("Steve", "WorkoutID: " + workoutId);
			WorkoutResult workoutResult = new WorkoutResult(workoutId, exercise.getId());
			int workoutResultId = mDbHelper.storeWorkoutResult(workoutResult);
			/* Generate Time Result Row in Database */
			String units = "seconds";
			TimeResult timeResult = new TimeResult(workoutResultId, timeDone, units);
			mDbHelper.storeTimeResult(timeResult);
		}
	};
	
	private String getFormatedTime() {
		String time = "";
		if (minutes < 10){ time += "0" + minutes + ":"; }
		else { time += minutes + ":";}
		if (seconds < 10) { time += "0" + seconds;}
		else { time += seconds;}
		return time;
	}

	private void decreaseTime() {
		// only update if stop is false
		if (!complete) {
			if (seconds == 0) {
				seconds = 60;
				minutes--;
				if (seconds == 0 && minutes == 0) {
					complete = true;
				}
			}
			seconds -= 1;		
		}
	}
}
