package com.cwru.model;

/**
 * Object created from the Workout Result Table.
 * @author sacrilley
 *
 */
public class IntervalResult {
	private int id;
	private int workoutResultId;
	private String name;
	private double length;
	private String type;
	private String units;
	
	/**
	 * Constructor
	 * @param id
	 * @param workoutResultId
	 * @param name
	 * @param length
	 * @param type
	 * @param units
	 */
	public IntervalResult(int id, int workoutResultId, String name, double length, String type, String units) {
		this.id = id;
		this.workoutResultId = workoutResultId;
		this.name = name;
		this.length = length;
		this.type = type;
		this.units = units;
	}
	
	/**
	 * Constructor
	 * @param workoutResultId
	 * @param name
	 * @param length
	 * @param type
	 * @param units
	 */
	public IntervalResult(int workoutResultId, String name, double length, String type, String units) {
		this.workoutResultId = workoutResultId;
		this.name = name;
		this.length = length;
		this.type = type;
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
	 * Getter for the name of the interval
	 * @return
	 */
	public String getName() { return name; }
	/**
	 * Getter for the length of the interval
	 * @return
	 */
	public double getLength() { return length; }
	/** 
	 * Getter for the type of the interval, either distance or time
	 * @return
	 */
	public String getType() { return type; }
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
	 * Setter for the name of the interval result
	 * @param name
	 */
	public void setName(String name) { this.name = name; }
	/**
	 * Setter for the length of the interval result
	 * @param length
	 */
	public void setLength(double length) { this.length = length; }
	/**
	 * Setter for the type of the interval result, either time or distance
	 * @param type
	 */
	public void setType(String type) { this.type = type; }
	/**
	 * Setter for the units that the interval result is measured in
	 * @param units
	 */
	public void setUnits(String units) { this.units = units; }
}
