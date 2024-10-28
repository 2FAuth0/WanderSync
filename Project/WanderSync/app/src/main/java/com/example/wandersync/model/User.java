package com.example.wandersync.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String id;
    private List<VacationTime> allottedVacation;
    private List<String> travelLogIDs;

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
}
