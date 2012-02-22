package com.cwru.model;



public class Exercise {
	private Long id;
	private String name;
	private String type;
	private int sets;
	private Long time = 0L;
	private String timeType;
	private Boolean isCountdown = true;
	private Double distance = 0.0;
	private String distanceType;
	private int intervalNum;
	private String comment;
	
	public Exercise() {
		// Default Constructor
	}
	
	public Exercise (Long id, String name) {
		this.name = name;
		this.id = id;
	}
	
	@Override 
	public boolean equals(Object that) {
	    //check for self-comparison
	    if ( this == that ) return true;
	    if ( !(that instanceof Exercise) ) return false;
	    // Cast object
	    Exercise exercise = (Exercise) that;
	    if (this.name.equals(exercise.name)) {
	    	return true;
	    }
	    return false;	    
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSets() {
		return sets;
	}
	public void setSets(int sets) {
		this.sets = sets;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public Boolean getIsCountdown() {
		return isCountdown;
	}
	public void setIsCountdown(Boolean isCountdown) {
		this.isCountdown = isCountdown;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getDistanceType() {
		return distanceType;
	}
	public void setDistanceType(String distanceType) {
		this.distanceType = distanceType;
	}
	public int getIntervalNum() {
		return intervalNum;
	}
	public void setIntevalNum(int intervalNum) {
		this.intervalNum = intervalNum;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}