package com.cwru.model;

import java.util.ArrayList;

public class Interval {
	private int id;
	private int exerciseId;
	private int numRepeats;
	private ArrayList<IntervalSet> intervalSets;
	
	/**
	 * Constructor taking in all parameters
	 * 
	 * @param id
	 * @param exerciseId
	 * @param numRepeats
	 * @param intervals
	 */
	public Interval(int id, int exerciseId, int numRepeats, ArrayList<IntervalSet> intervalSets) {
		this.id = id;
		this.exerciseId = exerciseId;
		this.numRepeats = numRepeats;
		this.intervalSets = intervalSets;
	}
	
	/**
	 * Constructor taking all parameters except id
	 * 
	 * @param exerciseId
	 * @param numRepeats
	 * @param intervals
	 */
	public Interval(int exerciseId, int numRepeats, ArrayList<IntervalSet> intervalSets) {
		this.exerciseId = exerciseId;
		this.numRepeats = numRepeats;
		this.intervalSets = intervalSets;
	}
	
	/**
	 * Empty constructor
	 */
	public Interval() {
		
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the exerciseId
	 */
	public int getExerciseId() {
		return exerciseId;
	}
	/**
	 * @param exerciseId the exerciseId to set
	 */
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	/**
	 * @return the numRepeats
	 */
	public int getNumRepeats() {
		return numRepeats;
	}
	/**
	 * @param numRepeats the numRepeats to set
	 */
	public void setNumRepeats(int numRepeats) {
		this.numRepeats = numRepeats;
	}
	/**
	 * 
	 * @return intervalSets
	 */
	public ArrayList<IntervalSet> getIntervalSets() {
		return intervalSets;
	}
	/**
	 * 
	 * @param intervalSets
	 */
	public void setIntervalSets(ArrayList<IntervalSet> intervalSets) {
		this.intervalSets = intervalSets;
	}
	
}
