package com.cwru.model;

/**
 * 
 * @author sacrilley
 *
 */
public class Distance {
	private int id;
	private int exerciseId;
	private double length;
	private String units;
	
	/**
	 * 
	 * @param id
	 * @param exerciseId
	 * @param length
	 * @param units
	 */
	public Distance(int id, int exerciseId, double length, String units) {
		this.id = id;
		this.exerciseId = exerciseId;
		this.length = length;
		this.units = units;
	}
	
	/**
	 * 
	 * @param exerciseId
	 * @param length
	 * @param units
	 */
	public Distance(int exerciseId, double length, String units) { 
		this.exerciseId = exerciseId;
		this.length = length; 
		this.units = units;
	}
	
	/**
	 * 
	 * @param length
	 * @param units
	 */
	public Distance(double length, String units) { 
		this.length = length;
		this.units = units;
	}
	
	public int getId() { return id; }
	/**
	 * 
	 * @return
	 */
	public int getExerciseId() { return exerciseId; }
	/**
	 * 
	 * @return
	 */
	public double getLength() { return length; }
	/**
	 * 
	 * @return
	 */
	public String getUnits() { return units; }
	
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) { this.id = id; }
	/**
	 * 
	 * @param exerciseId
	 */
	public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
	/**
	 * 
	 * @param length
	 */
	public void setLength(double length) { this.length = length; }
	/**
	 * 
	 * @param units
	 */
	public void setUnits(String units) { this.units = units; }
	
}
