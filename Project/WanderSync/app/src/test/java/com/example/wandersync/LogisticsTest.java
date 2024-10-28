package com.example.wandersync;

import static org.junit.Assert.assertEquals;

import android.view.View;

import com.example.wandersync.view.TestFragment;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LogisticsTest {
    @Test
    public void testPieChartVisibilityToggle() {
        TestFragment fragment = new TestFragment();
        fragment.togglePieChartVisibility();

        assertEquals("PieChart should be visible", View.VISIBLE, fragment.getPieChartVisibility());

        fragment.togglePieChartVisibility();
        assertEquals("PieChart should be invisible", View.GONE, fragment.getPieChartVisibility());
    }
    @Test
    public void testOnlyOneFormVisibleAtATime() {
        TestFragment fragment = new TestFragment();

        fragment.toggleForm1();
        assertEquals("Form1 should be visible", View.VISIBLE, fragment.getForm1Visibility());
        assertEquals("Form2 should be hidden", View.GONE, fragment.getForm2Visibility());

        fragment.toggleForm2();
        assertEquals("Form2 should be visible", View.VISIBLE, fragment.getForm2Visibility());
        assertEquals("Form1 should be hidden", View.GONE, fragment.getForm1Visibility());

        fragment.toggleForm1();
        assertEquals("Form1 should be visible", View.VISIBLE, fragment.getForm1Visibility());
        assertEquals("Form2 should be hidden", View.GONE, fragment.getForm2Visibility());
    }
}