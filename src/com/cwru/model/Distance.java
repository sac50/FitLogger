package com.cwru.model;

public class Distance {
	private int id;
	private int exerciseId;
	private double length;
	private String units;
	
	public Distance(int id, int exerciseId, double length, String units) {
		this.id = id;
		this.exerciseId = exerciseId;
		this.length = length;
		this.units = units;
	}
	
	public Distance(int exerciseId, double length, String units) { 
		this.exerciseId = exerciseId;
		this.length = length; 
		this.units = units;
	}
	
	public Distance(double length, String units) { 
		this.length = length;
		this.units = units;
	}
	
	public int getId() { return id; }
	public int getExerciseId() { return exerciseId; }
	public double getLength() { return length; }
	public String getUnits() { return units; }
	
	public void setId(int id) { this.id = id; }
	public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
	public void setLength(double length) { this.length = length; }
	public void setUnits(String units) { this.units = units; }
	
}
