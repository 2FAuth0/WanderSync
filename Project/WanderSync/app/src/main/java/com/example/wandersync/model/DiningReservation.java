package com.example.wandersync.model;

public class DiningReservation {
    private String id; // Unique identifier
    private String location;
    private String timing;
    private String website;

    public DiningReservation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public DiningReservation(
            String id, // Unique identifier
            String location,
            String timing,
            String website) {
        this.id = id;
        this.timing = timing;
        this.location = location;
        this.website = website;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}