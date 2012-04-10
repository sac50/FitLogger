package com.cwru.model;

public class Workout {
	private int id;
	private String name;
	private String type;
	private String exerciseSequence;
	private String comment;
	private String repeatWeeks;
	/* 0 - True 1 - false */
	private int repeatSunday;
	private int repeatMonday;
	private int repeatTuesday;
	private int repeatWednesday;
	private int repeatThursday;
	private int repeatFriday;
	private int repeatSaturday;
	
	public Workout (String name, String type, String exerciseSequence) {
		this.name = name;
		this.type = type;
		this.exerciseSequence = exerciseSequence;
	}
	
	public Workout (String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public Workout (int id, String name) { 
		this.id = id; 
		this.name = name;
	}
	
	public Workout(String workoutName, String workoutType,String workoutRepeatWeeks, int repeatSunday, int repeatMonday,
			int repeatTuesday, int repeatWednesday, int repeatThursday,	int repeatFriday, int repeatSaturday) {
		this.name = workoutName;
		this.type = workoutType;
		this.repeatWeeks = workoutRepeatWeeks;
		this.repeatSunday = repeatSunday;
		this.repeatMonday = repeatMonday;
		this.repeatTuesday = repeatTuesday;
		this.repeatWednesday = repeatWednesday;
		this.repeatThursday = repeatThursday;
		this.repeatFriday = repeatFriday;
		this.repeatSaturday = repeatSaturday;
	}

	
	public Workout(String workoutName, String workoutType, String exerciseSequence, String workoutRepeatWeeks, int repeatSunday, int repeatMonday,
			int repeatTuesday, int repeatWednesday, int repeatThursday,	int repeatFriday, int repeatSaturday) {
		this.name = workoutName;
		this.type = workoutType;
		this.exerciseSequence = exerciseSequence;
		this.repeatWeeks = workoutRepeatWeeks;
		this.repeatSunday = repeatSunday;
		this.repeatMonday = repeatMonday;
		this.repeatTuesday = repeatTuesday;
		this.repeatWednesday = repeatWednesday;
		this.repeatThursday = repeatThursday;
		this.repeatFriday = repeatFriday;
		this.repeatSaturday = repeatSaturday;
	}
	
	public Workout(int workoutId, String workoutName, String workoutType, String exerciseSequence, String workoutRepeatWeeks, int repeatSunday, int repeatMonday,
			int repeatTuesday, int repeatWednesday, int repeatThursday,	int repeatFriday, int repeatSaturday) {
		this.id = workoutId;
		this.name = workoutName;
		this.type = workoutType;
		this.exerciseSequence = exerciseSequence;
		this.repeatWeeks = workoutRepeatWeeks;
		this.repeatSunday = repeatSunday;
		this.repeatMonday = repeatMonday;
		this.repeatTuesday = repeatTuesday;
		this.repeatWednesday = repeatWednesday;
		this.repeatThursday = repeatThursday;
		this.repeatFriday = repeatFriday;
		this.repeatSaturday = repeatSaturday;
	}

	public int getId() { return id;}
	public String getName() { return name; }
	public String getType() { return type; }
	public String getExerciseSequence() { return exerciseSequence; }
	public String getComment() { return comment; }
	public String getRepeatWeeks() { return repeatWeeks; }
	public int getRepeatSunday() { return repeatSunday; }
	public int getRepeatMonday() { return repeatMonday; }
	public int getRepeatTuesday() { return repeatTuesday; }
	public int getRepeatWednesday() { return repeatWednesday; }
	public int getRepeatThursday() { return repeatThursday; }
	public int getRepeatFriday() { return repeatFriday; }
	public int getRepeatSaturday() { return repeatSaturday; }
	
	public void setName(String name) { this.name = name; }
	public void setType(String type) { this.type = type; }
	public void setExerciseSequence(String exerciseSequence) { this.exerciseSequence = exerciseSequence; }
	public void setComment(String comment) { this.comment = comment; }
	public void setRepeatWeeks(String repeatWeeks) { this.repeatWeeks = repeatWeeks; }
	public void setRepeatSunday(int repeat) { this.repeatSunday = repeat; }
	public void setRepeatMonday(int repeat) { this.repeatMonday = repeat; }
	public void setRepeatTuesday(int repeat) { this.repeatTuesday = repeat; }
	public void setRepeatWednesday(int repeat) { this.repeatWednesday = repeat; }
	public void setRepeatThursday(int repeat) { this.repeatThursday = repeat; }
	public void setRepeatFriday(int repeat) { this.repeatFriday = repeat; }
	public void setRepeatSaturday(int repeat) { this.repeatSaturday = repeat; }
}
