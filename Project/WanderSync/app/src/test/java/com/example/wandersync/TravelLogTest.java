package com.example.wandersync;

import com.example.wandersync.model.TravelLog;

import org.junit.Test;
import static org.junit.Assert.*;

public class TravelLogTest {
    @Test
    public void testSetLocation() {
        TravelLog log = new TravelLog();
        log.setLocation("Paris");
        assertEquals("Paris", log.getLocation());
    }
}
