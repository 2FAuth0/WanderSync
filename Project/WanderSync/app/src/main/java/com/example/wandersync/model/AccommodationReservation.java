package com.example.wandersync.model;

public class AccommodationReservation {
    private String id;
    private String check_in;
    private String check_out;
    private String num_Rooms;
    private String room_Type;

    public AccommodationReservation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public AccommodationReservation(
            String id,
            String check_in,
            String check_out,
            String num_Rooms,
            String room_Type
    ) {
        this.id = id;
        this.check_in = check_in;
        this.check_out = check_out;
        this.num_Rooms = num_Rooms;
        this.room_Type = room_Type;
    }

    public String getId() {
        return id;
    }
    public String getCheck_in() {
        return check_in;
    }
    public String getCheck_out() {
        return check_out;
    }
    public String getNum_Rooms() {
        return num_Rooms;
    }
    public String getRoom_Type() {
        return room_Type;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }
    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }
    public void setNum_Rooms(String num_Rooms) {
        this.num_Rooms = num_Rooms;
    }
    public void setRoom_Type(String room_Type) {
        this.room_Type = room_Type;
    }
}
