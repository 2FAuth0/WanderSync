package com.example.wandersync;

import com.example.wandersync.model.Trip;
import org.junit.Test;
import static org.junit.Assert.*;

public class TripTest {

    @Test
    public void testTripConstructor() {
        Trip trip = new Trip("1234","test@example.com");
        assertEquals("test@example.com", trip.getUsers().get(0));
        assertEquals("1234", trip.getId());
    }

    // New test for adding a dining reservation
    @Test
    public void testAddDiningReservation() {
        Trip trip = new Trip();
        String diningId = "dining123";
        trip.addDining(diningId);

        // Verify that the dining reservation ID was added to the list
        assertTrue(trip.getDiningReservations().contains(diningId));
        assertEquals(1, trip.getDiningReservations().size());
    }

    // New test for adding an accommodation reservation
    @Test
    public void testAddAccommodationReservation() {
        Trip trip = new Trip();
        String accommodationId = "accommodation123";
        trip.addReservation(accommodationId);

        // Verify that the accommodation reservation ID was added to the list
        assertTrue(trip.getAccommodationReservations().contains(accommodationId));
        assertEquals(1, trip.getAccommodationReservations().size());
    }

    @Test
    public void testAddTravelLogs() {
        Trip trip = new Trip();
        String travelLogId = "travelLog123";
        trip.addTravelLog(travelLogId);

        // Verify that the accommodation reservation ID was added to the list
        assertTrue(trip.getTravelLogs().contains(travelLogId));
        assertEquals(1, trip.getTravelLogs().size());
    }

    @Test
    public void testMerge() {
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();
        for (int i = 0; i < 10; i++) {
            trip1.addTravelLog(String.valueOf(i));
            trip2.addTravelLog(String.valueOf(i+10));
            trip1.addReservation(String.valueOf(i));
            trip2.addReservation(String.valueOf(i+10));
            trip1.addDining(String.valueOf(i));
            trip2.addDining(String.valueOf(i+10));
        }
        trip1.merge(trip2);
        assertEquals(trip1.getTravelLogs().size(), 20);
        assertEquals(trip1.getDiningReservations().size(), 20);
        assertEquals(trip1.getAccommodationReservations().size(), 20);



    }
}
