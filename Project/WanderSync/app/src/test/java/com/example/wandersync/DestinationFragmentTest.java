package com.example.wandersync;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;

import static org.junit.Assert.*;

import android.widget.Toast;

import com.example.wandersync.view.DestinationFragment;

@RunWith(RobolectricTestRunner.class)
public class DestinationFragmentTest {
    private DestinationFragment fragment;
    @Before
    public void setup() {
        fragment = new DestinationFragment();
    }

    @Test
    public void testValidDates() {
        boolean result = fragment.areDatesValid("2024-10-01", "2024-10-31");
        assertTrue("Expected true for valid dates", result);
    }

    @Test
    public void testCalculateDuration() {
        int result = fragment.calculateDuration("2020-10-10","2020-10-20");
        assertEquals(10,result);
    }

    @Test
    public void testCalculateDurationMonth() {
        int result = fragment.calculateDuration("2020-10-10","2020-11-10");
        assertEquals(31,result);
    }

    @Test
    public void testEndDateBeforeStartDate() {
        boolean result = fragment.areDatesValid("2024-10-31", "2024-10-01");
        assertFalse("Expected false when end date is before start date", result);

        // Verify the Toast message
        String toastMessage = Shadows.shadowOf(Toast.makeText(fragment.getContext(), "", Toast.LENGTH_SHORT)).getTextOfLatestToast();
        assertEquals("End date must be after start date", toastMessage);
    }

    @Test
    public void testInvalidDateFormat() {
        boolean result = fragment.areDatesValid("invalid-date", "2024-10-01");
        assertFalse("Expected false for invalid date format", result);

        // Verify the Toast message
        String toastMessage = Shadows.shadowOf(Toast.makeText(fragment.getContext(), "", Toast.LENGTH_SHORT)).getTextOfLatestToast();
        assertEquals("Invalid date format. Use YYYY-MM-DD", toastMessage);
    }
}