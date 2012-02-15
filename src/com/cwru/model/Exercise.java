package com.cwru.model;



public class Exercise {
	private Long id;
	private String name;
	private String type;
	private int sets;
	private Long time = 0L;
	private Boolean isCountdown = true;
	private Double distance = 0.0;
	private String distanceType;
	private String comment;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}