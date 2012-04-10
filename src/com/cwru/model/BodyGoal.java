package com.cwru.model;

/**
 * Model class for handling body goals.
 * 
 * @author lowell
 *
 */
public class BodyGoal {
	private int id;
	private String category;
	private String unit;
	private double started;
	private double current;
	private double goal;
	private boolean isCompleted;
	
	/**
	 * Constructor taking all values for Body Goal
	 * 
	 * @param id
	 * @param category
	 * @param unit
	 * @param started
	 * @param current
	 * @param goal
	 * @param isCompleted
	 */
	public BodyGoal(int id, String category, String unit, double started, double current, double goal, boolean isCompleted) {
		this.id = id;
		this.category = category;
		this.unit = unit;
		this.started = started;
		this.current = current;
		this.goal = goal;
		this.isCompleted = isCompleted;
	}
	
	/**
	 * Constructor taking all values for Body Goal except id
	 * 
	 * @param category
	 * @param unit
	 * @param started
	 * @param current
	 * @param goal
	 * @param isCompleted
	 */
	public BodyGoal(String category, String unit, double started, double current, double goal, boolean isCompleted) {
		this.category = category;
		this.unit = unit;
		this.started = started;
		this.current = current;
		this.goal = goal;
		this.isCompleted = isCompleted;
	}
	
	/**
	 * Empty Constructor
	 */
	public BodyGoal() {}

	/**
	 * returns the id
	 * 
	 * @return int id
	 */
	public int getId() {
		return id;
	}

	/**
	 * set the int id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * returns the category
	 * 
	 * @return String category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * set the String category
	 * 
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * get the unit
	 * 
	 * @return String unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * set the String unit
	 * 
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * get the started value
	 * 
	 * @return double started
	 */
	public double getStarted() {
		return started;
	}

	/**
	 * set the double started value
	 * 
	 * @param started
	 */
	public void setStarted(double started) {
		this.started = started;
	}

	/**
	 * get the current value
	 * 
	 * @return double current
	 */
	public double getCurrent() {
		return current;
	}

	/**
	 * set the double current value
	 * 
	 * @param current
	 */
	public void setCurrent(double current) {
		this.current = current;
	}

	/**
	 * get the goal value
	 * 
	 * @return double goal
	 */
	public double getGoal() {
		return goal;
	}

	/**
	 * set the double goal value
	 * 
	 * @param goal
	 */
	public void setGoal(double goal) {
		this.goal = goal;
	}

	/**
	 * get the isCompleted value
	 * 
	 * @return boolean isCompleted
	 */
	public boolean isCompleted() {
		return isCompleted;
	}

	/**
	 * set the boolean isCompleted value
	 * 
	 * @param isCompleted
	 */
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	
}