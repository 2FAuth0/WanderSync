package com.example.wandersync;

import com.example.wandersync.view.CommunityFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class CommunityFragmentTest {

    private CommunityFragment fragment;

    @Before
    public void setUp() {
        fragment = new CommunityFragment();
    }

    @Test
    public void areDatesValid_withValidDates_returnsTrue() {
        String startDate = "2023-12-01";
        String endDate = "2023-12-10";
        assertTrue(fragment.areDatesValid(startDate, endDate));
    }

    @Test
    public void areDatesValid_withInvalidDates_returnsFalse() {
        String startDate = "2023-12-10";
        String endDate = "2023-12-01";
        assertFalse(fragment.areDatesValid(startDate, endDate));
    }

    @Test
    public void calculateDuration_withValidDates_returnsCorrectDuration() {
        String startDate = "2023-12-01";
        String endDate = "2023-12-10";
        assertEquals(9, fragment.calculateDuration(startDate, endDate));
    }

    @Test
    public void calculateDuration_withInvalidDates_returnsZero() {
        String startDate = "2023-12-10";
        String endDate = "2023-12-01";
        assertEquals(0, fragment.calculateDuration(startDate, endDate));
    }
}