package com.example.wandersync.model;

import java.util.List;

public class TravelPost {
    private String id; // Unique post id
    private TravelLog travelLog;
    private List<AccommodationReservation> accommodations;
    private List<DiningReservation> diningReservations;

    public TravelPost() {
        // Default constructor for firebase
    }

    public TravelPost(String id, TravelLog travelLog, List<AccommodationReservation> accommodations, List<DiningReservation> diningReservations) {
        this.id = id;
        this.travelLog = travelLog;
        this.accommodations = accommodations;
        this.diningReservations = diningReservations;
    }

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
}

