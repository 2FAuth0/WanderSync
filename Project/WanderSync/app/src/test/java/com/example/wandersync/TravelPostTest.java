package com.example.wandersync;

import com.example.wandersync.model.TravelLog;
import com.example.wandersync.model.AccommodationReservation;
import com.example.wandersync.model.DiningReservation;
import com.example.wandersync.model.TravelPost;

import org.junit.Test;
import static org.junit.Assert.*;

public class TravelPostTest {

    @Test
    public void testNotesField() {
        TravelLog travelLog = new TravelLog("logId", "Paris", "2024-01-01", "2024-01-10", "10 days");
        AccommodationReservation accommodation = new AccommodationReservation("accId", "Hotel Luxe", "2024-01-01", "2024-01-10", "2", "Suite");
        DiningReservation dining = new DiningReservation("dineId", "Cafe de Paris", "www.cafedeparis.com", 4.5);
        String notes = "This trip was fantastic! The Eiffel Tower was breathtaking";

        TravelPost travelPost = new TravelPost("postId", travelLog, accommodation, dining, "Train", notes);

        assertEquals("This trip was fantastic! The Eiffel Tower was breathtaking", travelPost.getNotes());
    }

    @Test
    public void testUpdateNotesField() {
        TravelLog travelLog = new TravelLog("logId", "Paris", "2024-01-01", "2024-01-10", "10 days");
        AccommodationReservation accommodation = new AccommodationReservation("accId", "Hotel Luxe", "2024-01-01", "2024-01-10", "2", "Suite");
        DiningReservation dining = new DiningReservation("dineId", "Cafe de Paris", "www.cafedeparis.com", 4.5);
        TravelPost travelPost = new TravelPost("postId", travelLog, accommodation, dining, "Train", "Initial notes.");

        travelPost.setNotes("Updated notes: Loved the food at Cafe de Paris!");

        assertEquals("Updated notes: Loved the food at Cafe de Paris!", travelPost.getNotes());
    }
}
