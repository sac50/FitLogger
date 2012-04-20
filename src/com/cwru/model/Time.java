package com.cwru.model;

/**
 * 
 * @author sacrilley
 *
 */
public class Time {
	private int id;
	private int exerciseId;
	private int length;
	private String units;
	
	/**
	 * 
	 * @param id
	 * @param exerciseId
	 * @param length
	 * @param units
	 */
	public Time (int id, int exerciseId, int length, String units) {
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
	public Time (int exerciseId, int length, String units) {
		this.exerciseId = exerciseId;
		this.length = length;
		this.units = units;
	}
	
	/**
	 * 
	 * @param length
	 * @param units
	 */
	public Time (int length, String units) {
		this.length = length;
		this.units = units;
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
	public int getExerciseId() { return exerciseId; }
	/**
	 * 
	 * @return
	 */
	public int getLength() { return length; }
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
	public void setLength(int length) { this.length = length; }
	/**
	 * 
	 * @param units
	 */
	public void setUnits(String units) { this.units = units; }

}
