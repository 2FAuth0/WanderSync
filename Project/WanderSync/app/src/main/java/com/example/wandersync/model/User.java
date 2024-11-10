package com.example.wandersync.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String id;
    private List<VacationTime> allottedVacation;
    private List<String> travelLogIDs;
    private List<String> diningReservationIDs;
    private List<String> accommodationReservationIDs;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String id) {
        this.email = email;
        this.id = id;
        this.travelLogIDs = new ArrayList<>();
        travelLogIDs.add(id);

    }

    public void addVacationTime(VacationTime v) {
        if (allottedVacation == null) {
            this.allottedVacation = new ArrayList<>();
        }
        allottedVacation.add(v);
    }

    public void addTravelLog(String id) {
        if (travelLogIDs == null) {
            this.travelLogIDs = new ArrayList<>();
        }
        travelLogIDs.add(id);
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


    public String getEmail() {
        return this.email;
    }

    public List<VacationTime> getAllottedVacation() {
        return this.allottedVacation;
    }

    public List<String> getTravelLogIDs() {
        return this.travelLogIDs;
    }

    public List<String> getDiningReservationIDs() {
        return this.diningReservationIDs;
    }

    public List<String> getAccommodationReservationIDs() {
        return this.accommodationReservationIDs;
    }
}

