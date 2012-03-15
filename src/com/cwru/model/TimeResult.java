package com.cwru.model;

/**
 * Object for the TimeResult table in the database
 * @author sacrilley
 *
 */
public class TimeResult {
	private int id;
	private int workoutResultId;
	private int length;
	private String units;
	
	/**
	 * Constructor
	 * @param id
	 * @param workoutResultId
	 * @param length
	 * @param units
	 */
	public TimeResult(int id, int workoutResultId, int length, String units) {
		this.id = id;
		this.workoutResultId = workoutResultId;
		this.length = length;
		this.units = units;
	}
	
	/**
	 * Constructor
	 * @param workoutResultId
	 * @param length
	 * @param units
	 */
	public TimeResult(int workoutResultId, int length, String units) {
		this.workoutResultId = workoutResultId;
		this.length = length;
		this.units = units;
	}
	
	/**
	 * Getter for the id of the row in the Time result database
	 * @return
	 */
	public int getId() { return id; }
	/**
	 * Getter for the workoutResultId that the time result relates to
	 * @return
	 */
	public int getWorkoutResultId() { return workoutResultId; }
	/**
	 * Getter for the length of time
	 * @return
	 */
	public int getLength() { return length; }
	/**
	 * Getter for the units for the length of the time
	 * @return
	 */
	public String getUnits() { return units; }
	
	/**
	 * Setter for the id of the row in the time result database
	 * @param id
	 */
	public void setId(int id) { this.id = id; }
	/**
	 * Setter for the workoutResultId that the time result relates to
	 * @param workoutResultId
	 */
	public void setWorkoutResultId(int workoutResultId) { this.workoutResultId = workoutResultId; }
	/**
	 * Setter for the length of time for the exercise
	 * @param length
	 */
	public void setLength(int length) { this.length = length; }
	/**
	 * Setter for the units of time for the exercise
	 * @param units
	 */
	public void setUnits(String units) { this.units = units; }
}
