package com.cwru.model;

public class ExerciseBankRow {
	private String exerciseName;
	private boolean selected;
	
	public ExerciseBankRow(String exerciseName) {
		this.exerciseName = exerciseName;
	}
	
	public String getExerciseName() { return exerciseName; }
	public boolean isSelected() { return selected; }
	public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
	public void setSelected(boolean selected) { this.selected = selected; }

}

