package com.cwru.model;

/**
* ExerciseGoal Class.  Represents an exercise goal object associated with the application.
* @author lkissling
*
*/
public class ExerciseGoal {
	/** Constants for the type of exercise */
	public static final int RUN = 0;
	public static final int SWIM = 1;
	public static final int BIKE = 2;
	public static final int SKI = 3;
	public static final int PADDLE = 4;
	public static final int SPECIFIC_CARDIO_EXERCISE = 5;
	public static final int SPECIFIC_STRENGTH_EXERCISE = 6;
	
	/** Constant for the type of exercise goal */
	public static final int TIME = 0;
	public static final int DISTANCE = 1;
	public static final int SET = 2;
	
	private int id;
	private String name;
	private boolean isCumulative;
	private boolean isCompleted;
	private int mode;
	private int type;
	private int exerciseId;
	private double goalOne;
	private double goalTwo;
	private double currentBestOne;
	private double currentBestTwo;
	private String unit;
	
	/**
	 * Constructor taking all values for ExerciseGoal
	 * 
	 * @param id
	 * @param name
	 * @param isCumulative
	 * @param isCompleted
	 * @param mode
	 * @param type
	 * @param exerciseId
	 * @param goalOne
	 * @param goalTwo
	 * @param currentBestOne
	 * @param currentBestTwo
	 * @param unit
	 */
	public ExerciseGoal(int id, String name, boolean isCumulative, boolean isCompleted, int mode, int type, int exerciseId, double goalOne, double goalTwo, double currentBestOne, double currentBestTwo, String unit) {
		this.id = id;
		this.name = name;
		this.isCumulative = isCumulative;
		this.isCompleted = isCompleted;
		this.mode = mode;
		this.type = type;
		this.exerciseId = exerciseId;
		this.goalOne = goalOne;
		this.goalTwo = goalTwo;
		this.currentBestOne = currentBestOne;
		this.currentBestTwo = currentBestTwo;
		this.unit = unit;
	}
	
	/**
	 * Constructor taking all values except id for ExerciseGoal
	 * 
	 * @param name
	 * @param isCumulative
	 * @param isCompleted
	 * @param mode
	 * @param type
	 * @param exerciseId
	 * @param goalOne
	 * @param goalTwo
	 * @param currentBestOne
	 * @param currentBestTwo
	 * @param unit
	 */
	public ExerciseGoal(String name, boolean isCumulative, boolean isCompleted, int mode, int type, int exerciseId, double goalOne, double goalTwo, double currentBestOne, double currentBestTwo, String unit) {
		this.name = name;
		this.isCumulative = isCumulative;
		this.isCompleted = isCompleted;
		this.type = type;
		this.exerciseId = exerciseId;
		this.goalOne = goalOne;
		this.goalTwo = goalTwo;
		this.currentBestOne = currentBestOne;
		this.currentBestTwo = currentBestTwo;
		this.unit = unit;
	}
	
	/**
	 * Empty constructor for ExerciseGoal
	 */
	public ExerciseGoal() { }

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return boolean isCumulative
	 */
	public boolean getIsCumulative() {
		return isCumulative;
	}
	
	/**
	 * 
	 * @param boolean isCumulative
	 */
	public void setIsCumulative(boolean isCumulative) {
		this.isCumulative = isCumulative;
	}
	
	/**
	 * 
	 * @return boolean isCompleted
	 */
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	/**
	 * 
	 * @param boolean isCompleted
	 */
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * @return the exerciseId
	 */
	public int getExerciseId() {
		return exerciseId;
	}

	/**
	 * @param exerciseId the exerciseId to set
	 */
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	/**
	 * @return the goalOne
	 */
	public double getGoalOne() {
		return goalOne;
	}

	/**
	 * @param goalOne the goalOne to set
	 */
	public void setGoalOne(double goalOne) {
		this.goalOne = goalOne;
	}

	/**
	 * @return the goalTwo
	 */
	public double getGoalTwo() {
		return goalTwo;
	}

	/**
	 * @param goalTwo the goalTwo to set
	 */
	public void setGoalTwo(double goalTwo) {
		this.goalTwo = goalTwo;
	}
	
	/**
	 * @return the currentBestOne
	 */
	public double getCurrentBestOne() {
		return currentBestOne;
	}

	/**
	 * @param currentBestOne the currentBestOne to set
	 */
	public void setCurrentBestOne(double currentBestOne) {
		this.currentBestOne = currentBestOne;
	}
	
	/**
	 * @return the currentBestTwo
	 */
	public double getCurrentBestTwo() {
		return currentBestTwo;
	}

	/**
	 * @param currentBestTwo the currentBestTwo to set
	 */
	public void setCurrentBestTwo(double currentBestTwo) {
		this.currentBestTwo = currentBestTwo;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
