package com.cwru.model;

import java.util.ArrayList;

public class WorkoutResult {
	public static final int SET_BASED_EXERCISE = 0;
	public static final int DISTANCE_BASED_EXERCISE = 1;
	public static final int TIME_BASED_EXERCISE = 2;
	public static final int INTERVAL_BASED_EXERCISE = 3;
	
	private int id;
	private int workoutId;
	private int exerciseId;
	private String date;
	private int mode;
	
	private ArrayList<SetResult> setResultList;
	private ArrayList<TimeResult> timeResultList;
	private ArrayList<DistanceResult> distanceResultList;
	private ArrayList<IntervalResult> intervalResultList;
	
	public WorkoutResult (int workoutId, int exerciseId) { 
		this.workoutId = workoutId;
		this.exerciseId = exerciseId;
		setResultList = new ArrayList<SetResult> ();
		timeResultList = new ArrayList<TimeResult>();
		distanceResultList = new ArrayList<DistanceResult>();
		intervalResultList = new ArrayList<IntervalResult>();
	}
	
	public WorkoutResult (int id, int workoutId, int exerciseId, String date) {
		this.id = id; 
		this.workoutId = workoutId;
		this.exerciseId = exerciseId;
		this.date = date;
		setResultList = new ArrayList<SetResult> ();
		timeResultList = new ArrayList<TimeResult>();
		distanceResultList = new ArrayList<DistanceResult>();
		intervalResultList = new ArrayList<IntervalResult>();
	}
	
	public WorkoutResult (int id, int workoutId, int exerciseId, String date, int mode) {
		this.id = id;
		this.workoutId = workoutId;
		this.exerciseId = exerciseId;
		this.date = date;
		this.mode = mode;
		setResultList = new ArrayList<SetResult> ();
		timeResultList = new ArrayList<TimeResult>();
		distanceResultList = new ArrayList<DistanceResult>();
		intervalResultList = new ArrayList<IntervalResult>();
	}
	
	public int getId() { return id; }
	public int getWorkoutId() { return workoutId; }
	public int getExerciseId() { return exerciseId; }
	public String getDate() { return date; }
	public int getMode() { return mode; }
	public ArrayList<SetResult> getSetResultList() { return setResultList; }
	public ArrayList<TimeResult> getTimeResultList() { return timeResultList; }
	public ArrayList<DistanceResult> getDistanceResultList() { return distanceResultList; }
	public ArrayList<IntervalResult> getIntervalResultList() { return intervalResultList; }
	
	public void setId(int id) { this.id = id; }
	public void setWorkoutId(int workoutId) { this.workoutId = workoutId;  }
	public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
	public void setDate(String date) { this.date = date; }
	public void setMode(int mode) { this.mode = mode; }
	public void setSetResultList(ArrayList<SetResult> resultList) {
		this.setResultList = resultList;
	}
	public void setTimeResultList(ArrayList<TimeResult> resultList) {
		this.timeResultList = resultList;
	}
	public void setDistanceResultList(ArrayList<DistanceResult> resultList) {
		this.distanceResultList = resultList;
	}
	public void setIntervalResultList(ArrayList<IntervalResult> resultList) {
		this.intervalResultList = resultList;
	}
	
	public void addSetResult(SetResult setResult) { 
		setResultList.add(setResult);
	}
	
}
