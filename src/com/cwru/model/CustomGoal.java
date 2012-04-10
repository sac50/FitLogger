package com.cwru.model;

/**
 * Model class for handling Custom Goals.
 * 
 * @author lowell
 *
 */
public class CustomGoal {
	private int id;
	private String name;
	private String description;
	private boolean isCompleted;
	
	/**
	 * Constructor that takes values for id, name, description and isCompleted.
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param isCompleted
	 */
	public CustomGoal(int id, String name, String description, boolean isCompleted) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.isCompleted = isCompleted;
	}
	
	/**
	 * Constructor that takes values for name, description and isCompleted
	 * @param name
	 * @param description
	 * @param isCompleted
	 */
	public CustomGoal(String name, String description, boolean isCompleted) {
		this.name = name;
		this.description = description;
		this.isCompleted = isCompleted;
	}
	
	/**
	 * Empty constructor
	 */
	public CustomGoal() {}
	
	/**
	 * getter for id
	 * 
	 * @return int id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * setter for int id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * getter for name
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * setter for String name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter for description
	 * 
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * setter for String description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * getter for isCompleted
	 * 
	 * @return boolean isCompleted
	 */
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	/**
	 * setter for boolean isCompleted
	 * 
	 * @param isCompleted
	 */
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
}