package com.example.wandersync.model;

public class TravelPost {
    private String id; // Unique identifier
    private TravelLog travelLog;
    private AccommodationReservation accommodations;
    private DiningReservation diningReservations;
    private String transportation;

    public TravelPost() {
        // Default constructor required for firebase
    }

    public TravelPost(String id, TravelLog travelLog, AccommodationReservation accommodations,
                      DiningReservation diningReservations, String transportation) {
        this.id = id;
        this.travelLog = travelLog;
        this.accommodations = accommodations;
        this.diningReservations = diningReservations;
        this.transportation = transportation;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TravelLog getTravelLog() {
        return travelLog;
    }

    public void setTravelLog(TravelLog travelLog) {
        this.travelLog = travelLog;
    }

    public AccommodationReservation getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(AccommodationReservation accommodations) {
        this.accommodations = accommodations;
    }

    public DiningReservation getDiningReservations() {
        return diningReservations;
    }

    public void setDiningReservations(DiningReservation diningReservations) {
        this.diningReservations = diningReservations;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }
}
