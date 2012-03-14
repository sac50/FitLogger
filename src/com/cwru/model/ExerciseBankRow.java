package com.cwru.model;

public class ExerciseBankRow {
	private Exercise exercise;
	private boolean selected;
	private String workoutName;
	
	public ExerciseBankRow(Exercise exercise, String workoutName, boolean selected) {
		this.exercise = exercise;
		this.workoutName = workoutName;
		this.selected = selected;
	}
	
	public String getWorkoutName() { return workoutName; }
	public String getExerciseName() { return exercise.getName(); }
	public int getExerciseId() { return exercise.getId(); }
	public boolean isSelected() { return selected; }
	public void setSelected(boolean selected) { this.selected = selected; }

}

