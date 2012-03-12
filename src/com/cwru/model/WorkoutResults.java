package com.cwru.model;

public class WorkoutResults {
	int id;
	int workout_id;
	long exercise_id;
	int setNumber;
	int reps;
	double weight;
	int time;
	boolean time_type;
	double distance;
	int interval;
	String comment; // Is this needed??  Now moved to notes fragment
	
	public WorkoutResults (int workout_id, int exercise_id) { 
		this.workout_id = workout_id;
		this.exercise_id = exercise_id;
	}
	
	public WorkoutResults (int workout_id, long exercise_id, int setNumber, int reps, double weight) {
		this.workout_id = workout_id;
		this.exercise_id = exercise_id;
		this.setNumber = setNumber;
		this.reps = reps;
		this.weight = weight;
	}
	
	public int getId() { return id; }
	public int getWorkoutId() { return workout_id; }
	public long getExerciseId() { return exercise_id; }
	public int getSetNumber() { return setNumber; }
	public int getReps() { return reps; }
	public double getWeight() { return weight; } 
	public int getTime() { return time; }
	public boolean getTimeType() { return time_type; }
	public double getDistance() { return distance; }
	public int getInterval() { return interval; }
	public String getComment() { return comment; }
	
	public void setWorkoutId(int workoutId) { this.workout_id = workoutId; }
	public void setExerciseId(long exerciseId) { this.exercise_id = exerciseId; }
	public void setSetNumber(int setNumber) { this.setNumber = setNumber; }
	public void setReps(int reps) { this.reps = reps; }
	public void setWeight(double weight) { this.weight = weight; }
	public void setTime(int time) { this.time = time; }
	public void setTimeType(boolean timeType) { this.time_type = timeType; }
	public void setDistance(double distance) { this.distance = distance; }
	public void setInterval(int interval) { this.interval = interval; }
	
}
