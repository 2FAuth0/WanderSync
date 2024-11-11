package com.example.wandersync.model;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String id;
    private List<String> users = new ArrayList<>();
    private List<String> travelLogs = new ArrayList<>();
    private List<String> diningReservations = new ArrayList<>();
    private List<String> accommodationReservations = new ArrayList<>();
    private List<String> notes = new ArrayList<>();

    public Trip() {   }

    public Trip(String id, String email) {
        this.id = id;
        users.add(email);
    }

    public void merge(Trip trip) {
        users.addAll(trip.users);
        travelLogs.addAll(trip.travelLogs);
        diningReservations.addAll(trip.diningReservations);
        accommodationReservations.addAll(trip.accommodationReservations);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getTravelLogs() {
        return travelLogs;
    }

    public void setTravelLogs(List<String> travelLogs) {
        this.travelLogs = travelLogs;
    }

    public List<String> getDiningReservations() {
        return diningReservations;
    }

    public void setDiningReservations(List<String> diningReservations) {
        this.diningReservations = diningReservations;
    }

    public List<String> getAccommodationReservations() {
        return accommodationReservations;
    }

    public void setAccommodationReservations(List<String> accommodationReservations) {
        this.accommodationReservations = accommodationReservations;
    }

    public void addTravelLog(String id) {
        travelLogs.add(id);
    }

    public void addUser(String email) {
        users.add(email);
    }

    public void addNote(String note) {
        notes.add(note);
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public void addReservation(String reservationId) {
        accommodationReservations.add(reservationId);
    }

    public void addDining(String reservationId) {
        diningReservations.add(reservationId);
    }
}
