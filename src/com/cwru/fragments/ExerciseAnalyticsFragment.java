package com.cwru.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Distance;
import com.cwru.model.DistanceResult;
import com.cwru.model.Exercise;
import com.cwru.model.SetResult;
import com.cwru.model.Time;
import com.cwru.model.TimeResult;
import com.cwru.model.WorkoutResult;
import com.cwru.utils.DateConverter;
import com.cwru.utils.GraphBuilder;
import com.cwru.utils.MeasurementConversions;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * 
 * @author lkissling
 *
 */
public class ExerciseAnalyticsFragment extends Fragment {
	DbAdapter mDbHelper;
	Exercise ex;
	View view;
	RadioGroup rg;
	LinearLayout ll;
	WebView wv;
	WebView wv2;
	ArrayList<WorkoutResult> results = new ArrayList<WorkoutResult>();
	ArrayList<DistanceResult> distanceResults = new ArrayList<DistanceResult>();
	ArrayList<TimeResult> timeResults = new ArrayList<TimeResult>();
	ArrayList<SetResult> setResults = new ArrayList<SetResult>();
	
	
	/**
	 * Constructor
	 * @param ex
	 */
	public ExerciseAnalyticsFragment(Exercise ex) {
		this.ex = ex;
	}
	
	/**
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		view = inflater.inflate(R.layout.exercise_analytics, container, false);
		
		results = mDbHelper.getWorkoutResultsForExercise(ex.getId());
		
		String html = null;
		
		ex.setMode(mDbHelper.getExerciseMode(ex.getId()));
		switch (ex.getMode()) {
		case Exercise.COUNTDOWN_BASED_EXERCISE:
			html = generateTimeHTML();
			break;
		case Exercise.COUNTUP_BASED_EXERCISE:
			html = generateTimeHTML();
			break;
		case Exercise.DISTANCE_BASED_EXERCISE:
			html = generateDistanceHTML();
			break;
		case Exercise.SET_BASED_EXERCISE:
			html = initializeSetHTML();
			break;
		}
		
		wv = (WebView) view.findViewById(R.id.wvExerciseAnalytics);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setBackgroundColor(0xFFFFFFFF);
		wv.loadDataWithBaseURL("file:///android_asset/js/", html, "text/html", "utf-8", null);
		
		return view;
	}
	
	/**
	 * 
	 * @return
	 */
	private String generateTimeHTML() {
		String html = null;
		HashMap<Integer, Double> map = new LinkedHashMap<Integer, Double>();
		Time time = mDbHelper.getTimeForExercise(ex.getId());
		
		for (int i = results.size() - 1; i >= 0; i--) {
			WorkoutResult r = results.get(i);
			timeResults.addAll(mDbHelper.getTimeResultForWorkoutResult(r.getId()));
		}
		
		int start = timeResults.size() > 50 ? timeResults.size() - 50 : 0;
		String units = null;
		
		for (int i = start; i < timeResults.size(); i++) {
			TimeResult result = timeResults.get(i);
			if (time != null) {
				units = time.getUnits();
				double num = MeasurementConversions.convert(result.getLength(), result.getUnits(), units);
				map.put(i, num);
			} else {
				units = "seconds";
				double num = MeasurementConversions.convert(result.getLength(), result.getUnits(), units);
				map.put(i, num);
			}
		}
		
		if (map.isEmpty()) {
			for (int i = 0; i < 5; i ++) {
				map.put(5, 0.0);
			}
		}
		
		GraphBuilder gb = new GraphBuilder();
		html = gb.buildHTML(map, "Recent (up to 50) results for " + ex.getName() + ", in " + units);
				
		return html;
	}
	
	/**
	 * 
	 * @return
	 */
	private String generateDistanceHTML() {
		String html = null;
		HashMap<Integer, Double> map = new LinkedHashMap<Integer, Double>();
		Distance distance = mDbHelper.getDistanceForExercise(ex.getId());
		
		for (int i = results.size() - 1; i >= 0; i--) {
			WorkoutResult r = results.get(i);
			distanceResults.addAll(mDbHelper.getDistanceResultForWorkoutResult(r.getId()));
		}
		
		int start = distanceResults.size() > 50 ? distanceResults.size() - 50 : 0;
		
		for (int i = start; i < distanceResults.size(); i++) {
			DistanceResult result = distanceResults.get(i);
			double num = MeasurementConversions.convert(result.getLength(), result.getUnits(), distance.getUnits());
			map.put(i, num);
		}
		
		if (map.isEmpty()) {
			for (int i = 0; i < 5; i ++) {
				map.put(i, 0.0);
			}
		}
		
		GraphBuilder gb = new GraphBuilder();
		html = gb.buildHTML(map, "Recent (up to 50) results for " + ex.getName() + ", in " + distance.getUnits());
		
		return html;
	}
	
	/**
	 * 
	 * @return
	 */
	private String initializeSetHTML() {
		String html = null;
		HashMap<Integer, Double> map = new LinkedHashMap<Integer, Double>();
		
		for (int i = results.size() - 1; i >= 0; i--) {
			WorkoutResult r = results.get(i);
			setResults.addAll(mDbHelper.getSetResultsForWorkoutResult(r.getId()));
		}
		
		rg = (RadioGroup) view.findViewById(R.id.rgSetAnalytics);
		rg.setVisibility(0);
		rg.setOnCheckedChangeListener(checkedListener);
		
		ll = (LinearLayout) view.findViewById(R.id.llSetAnalyticsRepBased);
		
		wv2 = (WebView) view.findViewById(R.id.wv2ExerciseAnalytics);
		wv2.getSettings().setJavaScriptEnabled(true);
		wv2.setBackgroundColor(0xFFFFFFFF);
		
		Button generate = (Button) view.findViewById(R.id.btnSetAnalyticsGenerate);
		generate.setOnClickListener(generateListener);
		
		long current = System.currentTimeMillis();
		long decrWeek = 7 * 24 * 60 * 60 * 1000;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		int now = Integer.parseInt(sdf.format(date));
		
		ArrayList<Integer> weekList = new ArrayList<Integer>();
		
		for (int i = 12; i > 0; i--) {
			Date d = new Date(current - i * decrWeek);
			int j = Integer.parseInt(sdf.format(d));
			map.put(j, 0.0);
			weekList.add(j);
		}
		
		DateConverter dc = new DateConverter();
		
		for (int i = 0; i < results.size(); i++) {
			WorkoutResult r = results.get(i);
			int d = dc.stringDateToInt(r.getDate());
			for (int j = 11; j >= 0; j--) {
				if (d > weekList.get(j) && (j == 11 || d <= weekList.get(j + 1))) {
					setResults = mDbHelper.getSetResultsForWorkoutResult(r.getId());
					for (SetResult sr : setResults) {
						map.put(weekList.get(j), map.get(weekList.get(j)) + sr.getWeight() * sr.getReps());
					}
				}
			}
		}
		
		if (map.isEmpty()) {
			for (int i = 0; i < 5; i++) {
				map.put(i, 0.0);
			}
		}
		
		GraphBuilder gb = new GraphBuilder();
		html = gb.buildHTML(map, "Combined weight lifted for each week of the past three months.");
		
		return html;
	}
	
	/**
	 * 
	 */
	RadioGroup.OnCheckedChangeListener checkedListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == R.id.rbSetAnalyticsAllWeight) {
				wv2.setVisibility(8);
				ll.setVisibility(8);
				
				wv.setVisibility(0);
			} else {
				wv2.setVisibility(0);
				ll.setVisibility(0);
				
				wv.setVisibility(8);
			}
		}
	};
	
	
	/**
	 * 
	 */
	View.OnClickListener generateListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText repsText = (EditText) view.findViewById(R.id.etSetAnalyticsReps);
			String repsString = repsText.getText().toString();
			
			Context context = v.getContext();
			CharSequence toastText;
			int duration = Toast.LENGTH_SHORT;
			
			if (repsString == null || repsString.length() == 0) {
				toastText = "Please enter a reps value.";
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			
			int reps = Integer.parseInt(repsString);
			
			List<SetResult> selectResults = new ArrayList<SetResult>();
			HashMap<Integer, Double> map = new LinkedHashMap<Integer, Double>();
			
			for (SetResult result : setResults) {
				if (result.getReps() == reps) {
					selectResults.add(result);
				}
			}
			
			int start = selectResults.size() > 50 ? selectResults.size() - 50 : 0;
			
			for (int i = start; i < selectResults.size(); i++) {
				map.put(i, selectResults.get(i).getWeight());
			}
			
			if (map.isEmpty()) {
				for (int i = 0; i < 5; i++) {
					map.put(i, 0.0);
				}
			}
			
			GraphBuilder gb = new GraphBuilder();
			String html = gb.buildHTML(map, "Recent (up to 50) results for " + ex.getName() + " set with " + reps + " rep(s)");
			wv2.loadDataWithBaseURL("file:///android_asset/js/", html, "text/html", "utf-8", null);
			
		}
	};
}