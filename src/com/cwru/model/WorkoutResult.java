package com.cwru.model;

import java.util.ArrayList;

/**
 * 
 * @author sacrilley
 *
 */
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
	
	/**
	 * 
	 * @param workoutId
	 * @param exerciseId
	 */
	public WorkoutResult (int workoutId, int exerciseId) { 
		this.workoutId = workoutId;
		this.exerciseId = exerciseId;
		setResultList = new ArrayList<SetResult> ();
		timeResultList = new ArrayList<TimeResult>();
		distanceResultList = new ArrayList<DistanceResult>();
		intervalResultList = new ArrayList<IntervalResult>();
	}
	
	/**
	 * 
	 * @param id
	 * @param workoutId
	 * @param exerciseId
	 * @param date
	 */
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
	
	/**
	 * 
	 * @param id
	 * @param workoutId
	 * @param exerciseId
	 * @param date
	 * @param mode
	 */
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
	
	/**
	 * 
	 * @return
	 */
	public int getId() { return id; }
	/**
	 * 
	 * @return
	 */
	public int getWorkoutId() { return workoutId; }
	/**
	 * 
	 * @return
	 */
	public int getExerciseId() { return exerciseId; }
	/**
	 * 
	 * @return
	 */
	public String getDate() { return date; }
	/**
	 * 
	 * @return
	 */
	public int getMode() { return mode; }
	/**
	 * 
	 * @return
	 */
	public ArrayList<SetResult> getSetResultList() { return setResultList; }
	/**
	 * 
	 * @return
	 */
	public ArrayList<TimeResult> getTimeResultList() { return timeResultList; }
	/**
	 * 
	 * @return
	 */
	public ArrayList<DistanceResult> getDistanceResultList() { return distanceResultList; }
	/**
	 * 
	 * @return
	 */
	public ArrayList<IntervalResult> getIntervalResultList() { return intervalResultList; }
	
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) { this.id = id; }
	/**
	 * 
	 * @param workoutId
	 */
	public void setWorkoutId(int workoutId) { this.workoutId = workoutId;  }
	/**
	 * 
	 * @param exerciseId
	 */
	public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) { this.date = date; }
	/**
	 * 
	 * @param mode
	 */
	public void setMode(int mode) { this.mode = mode; }
	/**
	 * 
	 * @param resultList
	 */
	public void setSetResultList(ArrayList<SetResult> resultList) {
		this.setResultList = resultList;
	}
	/**
	 * 
	 * @param resultList
	 */
	public void setTimeResultList(ArrayList<TimeResult> resultList) {
		this.timeResultList = resultList;
	}
	/**
	 * 
	 * @param resultList
	 */
	public void setDistanceResultList(ArrayList<DistanceResult> resultList) {
		this.distanceResultList = resultList;
	}
	/**
	 * 
	 * @param resultList
	 */
	public void setIntervalResultList(ArrayList<IntervalResult> resultList) {
		this.intervalResultList = resultList;
	}
	
	/**
	 * 
	 * @param setResult
	 */
	public void addSetResult(SetResult setResult) { 
		setResultList.add(setResult);
	}
	
}
