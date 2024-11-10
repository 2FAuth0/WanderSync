package com.example.wandersync;

import com.example.wandersync.view.AccomodationFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class AccommodationFragmentTest {

    private AccomodationFragment fragment;

    @Before
    public void setUp() {
        fragment = new AccomodationFragment();
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
    public void areDatesValid_withInvalidDateFormat_returnsFalse() {
        String startDate = "2023/12/01";
        String endDate = "2023/12/10";
        assertFalse(fragment.areDatesValid(startDate, endDate));
    }
}