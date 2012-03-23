package com.cwru.model;

/**
 * Object representing a Distance Result row in the Distance_result table
 * @author sacrilley
 *
 */
public class DistanceResult {
	private int id;
	private int workoutResultId;
	private double length;
	private String units;
	
	/**
	 * Constructor
	 * @param id
	 * @param workoutResultId
	 * @param length
	 * @param units
	 */
	public DistanceResult(int id, int workoutResultId, double length, String units) {
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
	public DistanceResult(int workoutResultId, double length, String units) {
		this.workoutResultId = workoutResultId;
		this.length = length;
		this.units = units;
	}
	
	/**
	 * Constructor
	 * @param length
	 * @param units
	 */
	public DistanceResult(double length, String units) {
		this.length = length;
		this.units = units;
	}
	
	/**
	 * Getter for the id of the distance result row in the database
	 * @return
	 */
	public int getId() { return id; }
	/**
	 * Getter for the workoutResultId that the distance result relates to
	 * @return
	 */
	public int getWorkoutResultId() { return workoutResultId; }
	/**
	 * Getter for the distance length that was recorded by the user
	 * @return
	 */
	public double getLength() { return length; }
	/**
	 * Getter for the units that the distance was recorded in
	 * @return
	 */
	public String getUnits() { return units; }
	
	/**
	 * Setter for the id of the distance result row in the database
	 * @param id
	 */
	public void setId(int id) { this.id = id; }
	/**
	 * Setter for the workout result id that the distance result relates to
	 * @param workoutResultId
	 */
	public void setWorkoutResultId(int workoutResultId) { this.workoutResultId = workoutResultId; }
	/**
	 * Setter for the length of the distance result 
	 * @param length
	 */
	public void setLength(double length) { this.length = length; }
	/**
	 * Setter for the units that the distance is measured in
	 * @param units
	 */
	public void setUnits(String units) { this.units = units; }
}
