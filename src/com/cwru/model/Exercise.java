package com.cwru.model;

import android.os.Parcel;
import android.os.Parcelable;



public class Exercise implements Parcelable{
	private Long id;
	private String name;
	private String type;
	private int sets;
	private Long time = 0L;
	private String timeType;
	private Boolean isCountdown = true;
	private Double distance = 0.0;
	private String distanceType;
	private int intervals;
	private int intervalSets;
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
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeString(this.type);
		dest.writeLong(this.time);
		dest.writeString(this.timeType);
		dest.writeByte((byte) (this.isCountdown ? 1 : 0));
		dest.writeDouble(this.distance);
		dest.writeString(this.distanceType);
		dest.writeInt(this.intervals);
		dest.writeString(this.comment);
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
	public int getIntervals() {
		return intervals;
	}
	public void setIntervals(int intervals) {
		this.intervals = intervals;
	}
	public int getIntervalSets() {
		return intervalSets;
	}
	public void setIntervalSets(int intervalSets) {
		this.intervalSets = intervalSets;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}