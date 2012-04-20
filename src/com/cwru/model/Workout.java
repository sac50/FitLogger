package com.cwru.model;

/**
 * 
 * @author sacrilley
 *
 */
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
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @param exerciseSequence
	 */
	public Workout (String name, String type, String exerciseSequence) {
		this.name = name;
		this.type = type;
		this.exerciseSequence = exerciseSequence;
	}
	
	/**
	 * 
	 * @param name
	 * @param type
	 */
	public Workout (String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 */
	public Workout (int id, String name) { 
		this.id = id; 
		this.name = name;
	}
	
	/**
	 * 
	 * @param workoutName
	 * @param workoutType
	 * @param workoutRepeatWeeks
	 * @param repeatSunday
	 * @param repeatMonday
	 * @param repeatTuesday
	 * @param repeatWednesday
	 * @param repeatThursday
	 * @param repeatFriday
	 * @param repeatSaturday
	 */
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

	/**
	 * 
	 * @param workoutName
	 * @param workoutType
	 * @param exerciseSequence
	 * @param workoutRepeatWeeks
	 * @param repeatSunday
	 * @param repeatMonday
	 * @param repeatTuesday
	 * @param repeatWednesday
	 * @param repeatThursday
	 * @param repeatFriday
	 * @param repeatSaturday
	 */
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
	
	/**
	 * 
	 * @param workoutId
	 * @param workoutName
	 * @param workoutType
	 * @param exerciseSequence
	 * @param workoutRepeatWeeks
	 * @param repeatSunday
	 * @param repeatMonday
	 * @param repeatTuesday
	 * @param repeatWednesday
	 * @param repeatThursday
	 * @param repeatFriday
	 * @param repeatSaturday
	 */
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

	/**
	 * 
	 * @return
	 */
	public int getId() { return id;}
	/**
	 * 
	 * @return
	 */
	public String getName() { return name; }
	/**
	 * 
	 * @return
	 */
	public String getType() { return type; }
	/**
	 * 
	 * @return
	 */
	public String getExerciseSequence() { return exerciseSequence; }
	/**
	 * 
	 * @return
	 */
	public String getComment() { return comment; }
	/**
	 * 
	 * @return
	 */
	public String getRepeatWeeks() { return repeatWeeks; }
	/**
	 * 
	 * @return
	 */
	public int getRepeatSunday() { return repeatSunday; }
	/**
	 * 
	 * @return
	 */
	public int getRepeatMonday() { return repeatMonday; }
	/**
	 * 
	 * @return
	 */
	public int getRepeatTuesday() { return repeatTuesday; }
	/**
	 * 
	 * @return
	 */
	public int getRepeatWednesday() { return repeatWednesday; }
	/**
	 * 
	 * @return
	 */
	public int getRepeatThursday() { return repeatThursday; }
	/**
	 * 
	 * @return
	 */
	public int getRepeatFriday() { return repeatFriday; }
	/**
	 * 
	 * @return
	 */
	public int getRepeatSaturday() { return repeatSaturday; }
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) { this.name = name; }
	/**
	 * 
	 * @param type
	 */
	public void setType(String type) { this.type = type; }
	/**
	 * 
	 * @param exerciseSequence
	 */
	public void setExerciseSequence(String exerciseSequence) { this.exerciseSequence = exerciseSequence; }
	/**
	 * 
	 * @param comment
	 */
	public void setComment(String comment) { this.comment = comment; }
	/**
	 * 
	 * @param repeatWeeks
	 */
	public void setRepeatWeeks(String repeatWeeks) { this.repeatWeeks = repeatWeeks; }
	/**
	 * 
	 * @param repeat
	 */
	public void setRepeatSunday(int repeat) { this.repeatSunday = repeat; }
	/**
	 * 
	 * @param repeat
	 */
	public void setRepeatMonday(int repeat) { this.repeatMonday = repeat; }
	/**
	 * 
	 * @param repeat
	 */
	public void setRepeatTuesday(int repeat) { this.repeatTuesday = repeat; }
	/**
	 * 
	 * @param repeat
	 */
	public void setRepeatWednesday(int repeat) { this.repeatWednesday = repeat; }
	/**
	 * 
	 * @param repeat
	 */
	public void setRepeatThursday(int repeat) { this.repeatThursday = repeat; }
	/**
	 * 
	 * @param repeat
	 */
	public void setRepeatFriday(int repeat) { this.repeatFriday = repeat; }
	/**
	 * 
	 * @param repeat
	 */
	public void setRepeatSaturday(int repeat) { this.repeatSaturday = repeat; }
}
