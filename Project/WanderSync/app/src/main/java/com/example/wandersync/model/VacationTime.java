package com.example.wandersync.model;

public class VacationTime {
    private String id; // Unique identifier
    private String startDate;
    private String endDate;
    private int duration;

    public VacationTime() {

    }

    public VacationTime(String id, String startDate, String endDate, int duration) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public void setId(String id) {
        this.id = id;
    }

}
