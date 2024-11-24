package com.example.wandersync.model;

import java.util.List;

public class TravelPost {
    private String id; // Unique identifier
    private List<TravelLog> travelLogs;
    private List<AccommodationReservation> accommodations;
    private List<DiningReservation> diningReservations;
    private List<String> transportation;
    private String notes;

    public TravelPost() {
        // Default constructor required for firebase
    }

    public TravelPost(String id, List<TravelLog> travelLog,
                      List<AccommodationReservation> accommodations,
                      List<DiningReservation> diningReservations,
                      List<String> transportation, String notes) {
        this.id = id;
        this.travelLogs = travelLog;
        this.accommodations = accommodations;
        this.diningReservations = diningReservations;
        this.transportation = transportation;
        this.notes = notes;

    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TravelLog> getTravelLogs() {
        return travelLogs;
    }

    public void setTravelLogs(List<TravelLog> travelLogs) {
        this.travelLogs = travelLogs;
    }

    public List<AccommodationReservation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(List<AccommodationReservation> accommodations) {
        this.accommodations = accommodations;
    }

    public List<DiningReservation> getDiningReservations() {
        return diningReservations;
    }

    public void setDiningReservations(List<DiningReservation> diningReservations) {
        this.diningReservations = diningReservations;
    }

    public List<String> getTransportation() {
        return transportation;
    }

    public void setTransportation(List<String> transportation) {
        this.transportation = transportation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
