package com.cwru.model;

/**
 * 
 * @author sacrilley
 *
 */
public class ExerciseBankRow {
	private Exercise exercise;
	private boolean selected;
	private String workoutName;
	
	/**
	 * 
	 * @param exercise
	 * @param workoutName
	 * @param selected
	 */
	public ExerciseBankRow(Exercise exercise, String workoutName, boolean selected) {
		this.exercise = exercise;
		this.workoutName = workoutName;
		this.selected = selected;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getWorkoutName() { return workoutName; }
	/**
	 * 
	 * @return
	 */
	public String getExerciseName() { return exercise.getName(); }
	/**
	 * 
	 * @return
	 */
	public int getExerciseId() { return exercise.getId(); }
	/**
	 * 
	 * @return
	 */
	public boolean isSelected() { return selected; }
	/**
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) { this.selected = selected; }

}

