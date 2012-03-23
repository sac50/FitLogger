package com.cwru.model;

/**
 * Object created from the Workout Result Table.
 * @author sacrilley
 *
 */
public class IntervalResult {
	private int id;
	private int workoutResultId;
	private int intervalId;
	private int intervalSetNum;
	private int intervalSetId;
	private double length;
	private String units;
	
	/**
	 * Constructor
	 * @param id
	 * @param workoutResultId
	 * @param intervalId
	 * @param intervalSetNum
	 * @param intervalSetId
	 * @param length
	 * @param units
	 */
	public IntervalResult(int id, int workoutResultId, int intervalId, int intervalSetNum, int intervalSetId, double length, String units) {
		this.id = id;
		this.workoutResultId = workoutResultId;
		this.intervalId = intervalId;
		this.intervalSetNum = intervalSetNum;
		this.intervalSetId = intervalSetId;
		this.length = length;
		this.units = units;
	}
	
	/**
	 * Constructor
	 * @param workoutResultId
	 * @param intervalId
	 * @param intervalSetNum
	 * @param intervalSetId
	 * @param length
	 * @param units
	 */
	public IntervalResult(int workoutResultId, int intervalId, int intervalSetNum, int intervalSetId, double length, String units) {
		this.workoutResultId = workoutResultId;
		this.intervalId = intervalId;
		this.intervalSetNum = intervalSetNum;
		this.intervalSetId = intervalSetId;
		this.length = length;
		this.units = units;
	}
	
	
	/**
	 * Getter for the id of the row in the interval result table.  
	 * @return
	 */
	public int getId() { return id; }
	/**
	 * Getter for the workout result id that the interval result relates to
	 * @return
	 */
	public int getWorkoutResultId() { return workoutResultId; }
	/**
	 * Getter for the interval id that the interval result relates too
	 * @return
	 */
	public int getIntervalId() { return intervalId; }
	/**
	 * Getter for the interval set number that the result is for
	 * @return
	 */
	public int getIntervalSetNum() { return intervalSetNum; }
	/**
	 * Getter for the interval set id that the result relates too
	 * @return
	 */
	public int getIntervalSetId() { return intervalSetId; }
	/**
	 * Getter for the length of the interval
	 * @return
	 */
	public double getLength() { return length; }

	/**
	 * Getter for units that correspond to the length of the interval
	 * @return
	 */
	public String getUnits() { return units; }
	
	/**
	 * Setter for the id of the row that the interval result is.
	 * @param id
	 */
	public void setId(int id) { this.id = id; }
	/**
	 * Setter for the workout result id that the interval result relates to
	 * @param workoutResultId
	 */
	public void setWorkoutResultId(int workoutResultId) { this.workoutResultId = workoutResultId; }
	/**
	 * Setter for the Interval id
	 * @param intervalId
	 */
	public void setIntervalId(int intervalId) { this.intervalId = intervalId; }
	/**
	 * Setter for the Interval Set Number, Which set number is the result for
	 * @param intervalSetNum
	 */
	public void setIntervalSetNum(int intervalSetNum) { this.intervalSetNum = intervalSetNum; }
	/** 
	 * Setter for the interval Set Id
	 * @param intervalSetId
	 */
	public void setIntervalSetId(int intervalSetId) { this.intervalSetId = intervalSetId; }
	/**
	 * Setter for the length of the interval result
	 * @param length
	 */
	public void setLength(double length) { this.length = length; }
	/**
	 * Setter for the units that the interval result is measured in
	 * @param units
	 */
	public void setUnits(String units) { this.units = units; }
}
