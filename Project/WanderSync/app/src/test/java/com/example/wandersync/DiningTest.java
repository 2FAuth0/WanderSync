package com.example.wandersync;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import android.view.View;

import com.example.wandersync.view.TestFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DiningTest {
    private List<Date> dateList;
    private List<Date> pastDates;
    private List<Date> futureDates;
    private Date currentDate;

    @Before
    public void setUp() {
        dateList = new ArrayList<>();
        pastDates = new ArrayList<>();
        futureDates = new ArrayList<>();
        currentDate = new Date();

        // Add dates to dateList (mix of past and future dates)
        long currentTimeMillis = currentDate.getTime();
        dateList.add(new Date(currentTimeMillis - (10L * 24 * 60 * 60 * 1000))); // 10 days ago
        dateList.add(new Date(currentTimeMillis - (5L * 24 * 60 * 60 * 1000))); // 5 days ago
        dateList.add(new Date(currentTimeMillis + (3L * 24 * 60 * 60 * 1000))); // 3 days in the future
        dateList.add(new Date(currentTimeMillis + (7L * 24 * 60 * 60 * 1000))); // 7 days in the future
    }

    @Test
    public void testSplitDatesIntoPastAndFuture() {
        // Split dateList into pastDates and futureDates
        for (Date date : dateList) {
            if (date.before(currentDate)) {
                pastDates.add(date);
            } else {
                futureDates.add(date);
            }
        }
        System.out.println(dateList.size());

        // Assertions
        for (Date date : pastDates) {
            assertTrue(date.before(currentDate), "Date in pastDates is not before the current date");
        }
        System.out.println(pastDates.size());

        for (Date date : futureDates) {
            assertTrue(date.after(currentDate), "Date in futureDates is not after the current date");
        }
        System.out.println(futureDates.size());

        // Check that the combined size matches the original list, excluding today's date
        int totalDatesCount = pastDates.size() + futureDates.size();
        assertEquals("The split lists' size does not match the original dateList size", dateList.size(), totalDatesCount);
    }

    @Test
    public void testReservationToggle() {
        TestFragment fragment = new TestFragment();
        fragment.toggleReservationFormVisibility();

        assertEquals("Reservation form should be visible", View.VISIBLE, fragment.getReservationVisibility());

        fragment.toggleReservationFormVisibility();
        assertEquals("Reservation form should be invisible", View.GONE, fragment.getReservationVisibility());
    }
}