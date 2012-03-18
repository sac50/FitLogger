package com.cwru.model;

/**
 * Class to represent the Interval object as represented by the database
 * @author scrilley
 *
 */
public class IntervalSet {
	private int id;
	private int intervalId;
	private String name;
	private double length;
	private String type;
	private String units;
	
	/**
	 * Constructor taking in all parameters
	 * @param id
	 * @param intervalSetId
	 * @param name
	 * @param length
	 * @param type
	 * @param units
	 */
	public IntervalSet(int id, int intervalId, String name, double length, String type, String units) {
		this.id = id;
		this.intervalId = intervalId;
		this.name = name;
		this.length = length;
		this.type = type;
		this.units = units;
	}
	
	/**
	 * Constructor taking in all parameters except the id of the interval assigned by the database
	 * @param intervalId
	 * @param name
	 * @param length
	 * @param type
	 * @param units
	 */
	public IntervalSet(int intervalId, String name, double length, String type, String units) {
		this.intervalId = intervalId;
		this.name = name;
		this.length = length;
		this.type = type;
		this.units = units;
	}
	
	/**
	 * Constructor taking in all parameters except the id of the interval assigned by the database
	 * @param name
	 * @param length
	 * @param type
	 * @param units
	 */
	public IntervalSet( String name, double length, String type, String units) {
		this.name = name;
		this.length = length;
		this.type = type;
		this.units = units;
	}
	
	/**
	 * Empty constructor
	 */
	public IntervalSet() { };
	
	/**
	 * Getter for the id assigned to the interval assigned by the database
	 * @return
	 */
	public int getId() { return id; }
	/**
	 * Getter for the interval id that the interval relates to
	 * @return
	 */
	public int getIntervalId() { return intervalId; }
	/**
	 * Getter for the name of the interval that is provided by the user
	 * @return
	 */
	public String getName() { return name; }
	/**
	 * Getter for the length of the interval.  This length can either be a length in terms of distance or time
	 * @return
	 */
	public double getLength() { return length; }
	/**
	 * Getter for the type of interval that is associated with the interval.  This is either distance or time.
	 * @return
	 */
	public String getType() { return type; }
	/**
	 * Getter for the units that the length correspond to
	 * @return
	 */
	public String getUnits() { return units; }
	
	/**
	 * Setter for the id that is provided by the database for the interval
	 * @param id
	 */
	public void setId(int id) { this.id = id; }
	/**
	 * Setter for the interval id id that the interval relates to
	 * @param intervalId
	 */
	public void setIntervalId(int intervalId) { this.intervalId = intervalId; }
	/**
	 * Setter for the name provided by the user for the interval
	 * @param name
	 */
	public void setName(String name) { this.name = name; }
	/**
	 * Setter for the length of the interval.  This length can either be a length in terms of distance or time
	 * @param length
	 */
	public void setLength(double length) { this.length = length; }
	/**
	 * Setter for the type of interval.  This can be either time or distance
	 * @param type
	 */
	public void setType(String type) { this.type = type; }
	/**
	 * Setter for the units of the interval.  These units correspond to the type of interval and the length.  
	 * @param units
	 */
	public void setUnits(String units) { this.units = units; }
}
