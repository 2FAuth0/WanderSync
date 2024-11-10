package com.example.wandersync;

import com.example.wandersync.model.User;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    private User user;
    @Before
    public void setUp() {
        user = new User("test@example.com", "user123");
    }

    @Test
    public void testUserConstructor() {
        User user = new User("test@example.com", "password123");
        assertEquals("test@example.com", user.getEmail());
        //assertEquals("password123", user.getPassword());
    }

    // New test for adding a dining reservation
    @Test
    public void testAddDiningReservation() {
        String diningId = "dining123";
        user.addDiningReservation(diningId);

        // Verify that the dining reservation ID was added to the list
        assertTrue(user.getDiningReservationIDs().contains(diningId));
        assertEquals(1, user.getDiningReservationIDs().size());
    }

    // New test for adding an accommodation reservation
    @Test
    public void testAddAccommodationReservation() {
        String accommodationId = "accommodation123";
        user.addAccommodationReservation(accommodationId);

        // Verify that the accommodation reservation ID was added to the list
        assertTrue(user.getAccommodationReservationIDs().contains(accommodationId));
        assertEquals(1, user.getAccommodationReservationIDs().size());
    }
}
