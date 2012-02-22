package com.cwru.model;

public class Workout {
	private String name;
	private String type;
	private String exerciseSequence;
	private String comment;
	
	public Workout (String name, String type, String exerciseSequence) {
		this.name = name;
		this.type = type;
		this.exerciseSequence = exerciseSequence;
	}
	
	public Workout (String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() { return name; }
	public String getType() { return type; }
	public String getExerciseSequence() { return exerciseSequence; }
	public String getComment() { return comment; }
	
	public void setName(String name) { this.name = name; }
	public void setType(String type) { this.type = type; }
	public void setExerciseSequence(String exerciseSequence) { this.exerciseSequence = exerciseSequence; }
	public void setComment(String comment) { this.comment = comment; }
}
