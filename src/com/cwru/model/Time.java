package com.cwru.model;

public class Time {
	private int id;
	private int exerciseId;
	private int length;
	private String units;
	
	public Time (int id, int exerciseId, int length, String units) {
		this.id = id;
		this.exerciseId = exerciseId;
		this.length = length;
		this.units = units;
	}
	
	public Time (int exerciseId, int length, String units) {
		this.exerciseId = exerciseId;
		this.length = length;
		this.units = units;
	}
	
	public Time (int length, String units) {
		this.length = length;
		this.units = units;
	}
	
	public int getId() { return id; }
	public int getExerciseId() { return exerciseId; }
	public int getLength() { return length; }
	public String getUnits() { return units; }
	
	public void setId(int id) { this.id = id; }
	public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
	public void setLength(int length) { this.length = length; }
	public void setUnits(String units) { this.units = units; }

}
