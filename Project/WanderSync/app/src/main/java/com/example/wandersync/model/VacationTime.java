package com.example.wandersync.model;

public class VacationTime {
    private String id; // Unique identifier
    private String startDate;
    private String endDate;
    private int duration;

    public VacationTime() {
        // Default constructor required for calls to DataSnapshot.getValue(TravelLog.class)
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

    public void setId(String id) {
        this.id = id;
    }

}
