package com.cwru.model;



public class Set {
	private long id;
	private long exerciseId;
	private int reps;
	private double weight;
	
	public Set () {
		
	}
	
	public Set (long id, long exerciseId, int reps, double weight) {
		this.id = id; 
		this.exerciseId = exerciseId; 
		this.reps = reps;
		this.weight = weight;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(long exerciseId) {
		this.exerciseId = exerciseId;
	}
	public int getReps() {
		return reps;
	}
	public void setReps(int reps) {
		this.reps = reps;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}