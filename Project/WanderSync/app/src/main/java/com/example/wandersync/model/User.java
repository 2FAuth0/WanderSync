package com.example.wandersync.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String id;
    private List<VacationTime> allottedVacation;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String id) {
        this.email = email;
        this.id = id;
        this.allottedVacation = new ArrayList<>();
    }

    public void addVacationTime(VacationTime v) {
        allottedVacation.add(v);
    }

    public String getId() {
        return id;
    }


}
