package com.cwru.model;

public class Time {
	private int id;
	private int exerciseId;
	private int length;
	private String units;
	private boolean isCountUp;
	private boolean isCountdown;
	
	public Time (int id, int exerciseId, int length, String units, boolean isCountUp, boolean isCountdown) {
		this.id = id;
		this.exerciseId = exerciseId;
		this.length = length;
		this.units = units;
		this.isCountUp = isCountUp;
		this.isCountdown = isCountdown;
	}
	
	public Time (int exerciseId, int length, String units, boolean isCountUp, boolean isCountdown) {
		this.exerciseId = exerciseId;
		this.length = length;
		this.units = units;
		this.isCountUp = isCountUp;
		this.isCountdown = isCountdown;
	}
	
	public int getId() { return id; }
	public int getExerciseId() { return exerciseId; }
	public int getLength() { return length; }
	public String getUnits() { return units; }
	public boolean isCountUp() { return isCountUp; }
	public boolean isCountdown() { return isCountdown; }
	
	public void setId(int id) { this.id = id; }
	public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
	public void setLength(int length) { this.length = length; }
	public void setUnits(String units) { this.units = units; }
	public void setIsCountUp(boolean isCountUp) { this.isCountUp = isCountUp; }
	public void setIsCountdown(boolean isCountdown) { this.isCountdown = isCountdown; }

}
