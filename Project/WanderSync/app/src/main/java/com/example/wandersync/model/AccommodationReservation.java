package com.example.wandersync.model;

public class AccommodationReservation {
    private String id;
    private String location;
    private String checkIn;
    private String checkOut;
    private String numRooms;
    private String roomType;
    private boolean isPast;

    public AccommodationReservation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public AccommodationReservation(
            String id,
            String location,
            String checkIn,
            String checkOut,
            String numRooms,
            String roomType
    ) {
        this.id = id;
        this.location = location;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numRooms = numRooms;
        this.roomType = roomType;
    }

    public String getId() {
        return id;
    }
    public String getLocation() {
        return location;
    }
    public String getCheckIn() {
        return checkIn;
    }
    public String getCheckOut() {
        return checkOut;
    }
    public String getNumRooms() {
        return numRooms;
    }
    public String getRoomType() {
        return roomType;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
    public void setNumRooms(String numRooms) {
        this.numRooms = numRooms;
    }
    public boolean isPast() {
        return isPast;
    }
    public void setPast(boolean past) {
        isPast = past;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
