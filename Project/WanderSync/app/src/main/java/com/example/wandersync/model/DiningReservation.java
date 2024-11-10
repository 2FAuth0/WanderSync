package com.example.wandersync.model;

public class DiningReservation {
    private String id; // Unique identifier
    private String location;
    private String website;
    private Double reviews;

    public DiningReservation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public DiningReservation(
            String id, // Unique identifier
            String location,
            String website,
            Double reviews) {
        this.id = id;
        this.location = location;
        this.website = website;
        this.reviews = reviews;
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

    public void setReviews(Double reviews) {
        this.reviews = reviews;
    }

    public Double getReviews() {
        return reviews;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}