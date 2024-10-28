package com.example.wandersync;

import com.example.wandersync.model.User;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUserConstructor() {
        User user = new User("test@example.com", "password123");
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

}
