package com.cwru.model;

/**
 * Object to represent a row of the Set Result table
 * @author sacrilley
 *
 */
public class SetResult {
	private int id;
	private int workoutResultId;
	private int setNumber;
	private int reps;
	private double weight;
	
	/**
	 * Constructor
	 * @param id
	 * @param workoutResultId
	 * @param setNumber
	 * @param reps
	 * @param weight
	 */
	public SetResult(int id, int workoutResultId, int setNumber, int reps, double weight) {
		this.id = id;
		this.workoutResultId = workoutResultId;
		this.setNumber = setNumber;
		this.reps = reps;
		this.weight = weight;
	}
	
	/**
	 * Constructor
	 * @param workoutResultId
	 * @param setNumber
	 * @param reps
	 * @param weight
	 */
	public SetResult(int workoutResultId, int setNumber, int reps, double weight) {
		this.workoutResultId = workoutResultId;
		this.setNumber = setNumber;
		this.reps = reps;
		this.weight = weight;
	}
	
	/**
	 * Getter for the id of the row in the set result table
	 * @return
	 */
	public int getId() { return id; }
	/**
	 * Getter for the workout result that the set result relates to
	 * @return
	 */
	public int getWorkoutResultId() { return workoutResultId; }
	/**
	 * Getter for the set number that the recording is.  First set, second set...
	 * @return
	 */
	public int getSetNumber() { return setNumber; }
	/**
	 * Getter for the reps that are done for the set
	 * @return
	 */
	public int getReps() { return reps; }
	/**
	 * Getter for the weight that was done for the set
	 * @return
	 */
	public double getWeight() { return weight; }
	
	/**
	 * Setter for id of the row in the set result table
	 * @param id
	 */
	public void setId(int id) { this.id = id; }
	/**
	 * Setter for the workoutResult id that the set result relates to
	 * @param workoutResultId
	 */
	public void setWorkoutResultId(int workoutResultId) { this.workoutResultId = workoutResultId;  }
	/**
	 * setter for the set number of the set
	 * @param setNumber
	 */
	public void setSetNumber(int setNumber) { this.setNumber = setNumber; }
	/**
	 * Setter for the number of reps in this set
	 * @param reps
	 */
	public void setReps(int reps) { this.reps = reps; }
	/**
	 * Setter for the weight done in the set
	 * @param weight
	 */
	public void setWeight(double weight) { this.weight = weight; }
}
