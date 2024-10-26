package com.example.wandersync.model;

public class TravelLog {
    private String id; // Unique identifier
    private String location;
    private String startDate;
    private String endDate;
    private String duration;

    public TravelLog() {
        // Default constructor required for calls to DataSnapshot.getValue(TravelLog.class)
    }

    public TravelLog(String id, String location, String startDate, String endDate, String duration) {
        this.id = id;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}