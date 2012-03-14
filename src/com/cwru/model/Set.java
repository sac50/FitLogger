package com.cwru.model;


/**
 * Set object for a Set-based exercises
 * @author lkissling
 * @author scrilley
 *
 */
public class Set {
	private int id;
	private int exerciseId;
	private int reps;
	private double weight;
	
	/**
	 * Empty Constructor
	 */
	public Set () {
		
	}
	
	/**
	 * Set Constructor to take in all values and assign
	 * @param id
	 * @param exerciseId
	 * @param reps
	 * @param weight
	 */
	public Set (int id, int exerciseId, int reps, double weight) {
		this.id = id; 
		this.exerciseId = exerciseId; 
		this.reps = reps;
		this.weight = weight;
	}
	
	/**
	 * Set Constructor to assign values but not the id of the set row in the database
	 * @param exerciseId
	 * @param reps
	 * @param weight
	 */
	public Set (int exerciseId, int reps, double weight) {
		this.exerciseId = exerciseId;
		this.reps = reps;
		this.weight = weight;
	}
	
	/**
	 * Getter for the id for the set in the Set table
	 * @return
	 */
	public int getId() { return id; }
	/**
	 * Setter for the id for the set in the Set table
	 * @param id
	 */
	public void setId(int id) { this.id = id; }
	
	/**
	 * Getter for the exercise id that the set relates to
	 * @return
	 */
	public int getExerciseId() { return exerciseId; }
	/**
	 * Setter for the exercise id that the set relates to
	 * @param exerciseId
	 */
	public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
	
	/**
	 * Getter for reps that belong to the set
	 * @return
	 */
	public int getReps() { return reps;	}
	/**
	 * Setter for the reps that belong to the set
	 * @param reps
	 */
	public void setReps(int reps) { this.reps = reps; }
	
	/**
	 * Getter for the weight that belongs to the set
	 * @return
	 */
	public double getWeight() { return weight; }
	/**
	 * Setter for the weight that belongs to the set
	 * @param weight
	 */
	public void setWeight(double weight) { this.weight = weight; }
	
}