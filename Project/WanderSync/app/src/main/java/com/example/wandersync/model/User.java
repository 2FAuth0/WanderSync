package com.example.wandersync.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String id;
    private List<VacationTime> allottedVacation;
    private String tripID;
    private List<String> diningReservationIDs;
    private List<String> accommodationReservationIDs;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String id, String tripID) {
        this.email = email;
        this.id = id;
        this.tripID = tripID;
    }

    public void addVacationTime(VacationTime v) {
        if (allottedVacation == null) {
            this.allottedVacation = new ArrayList<>();
        }
        allottedVacation.add(v);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addDiningReservation(String id) {
        if (diningReservationIDs == null) {
            this.diningReservationIDs = new ArrayList<>();
        }
        diningReservationIDs.add(id);
    }

    // Method to add an accommodation reservation ID
    public void addAccommodationReservation(String id) {
        if (accommodationReservationIDs == null) {
            this.accommodationReservationIDs = new ArrayList<>();
        }
        accommodationReservationIDs.add(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<VacationTime> getAllottedVacation() {
        return allottedVacation;
    }

    public void setAllottedVacation(List<VacationTime> allottedVacation) {
        this.allottedVacation = allottedVacation;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public List<String> getDiningReservationIDs() {
        return this.diningReservationIDs;
    }

    public List<String> getAccommodationReservationIDs() {
        return this.accommodationReservationIDs;
    }
}

