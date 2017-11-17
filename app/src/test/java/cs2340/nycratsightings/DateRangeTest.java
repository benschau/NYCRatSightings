package cs2340.nycratsightings;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cs2340.nycratsightings.model.DateRange;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Represents a DateRangeTest Object
 */
public class DateRangeTest {
    DateRange mDateRange;

    /**
     * DateRangeTEst constructor
     */
    public DateRangeTest() {
        Calendar from = new GregorianCalendar(1998, 8, 10);
        Calendar to = new GregorianCalendar(1999, 5, 11);
        mDateRange = new DateRange(from, to);
    }

    /**
     * method to test if in range
     */
    @Test
    public void inRangeValidator() {
        // test a calendar that's actuallly in range
        Calendar test = new GregorianCalendar(1998, 9, 10);
        assertTrue(mDateRange.inRange(test));

        // test a calendar that's before the 'from' date in the daterange
        test = new GregorianCalendar(1997, 5, 9);
        assertFalse(mDateRange.inRange(test));

        // test a calendar that's after the 'to' date in the daterange
        test = new GregorianCalendar(2000, 1, 10);
        assertFalse(mDateRange.inRange(test));

        // test calendars on the to and from dates
        test = new GregorianCalendar(1998, 8, 10);
        assertTrue(mDateRange.inRange(test));
        test = new GregorianCalendar(1999, 5, 11);
        assertTrue(mDateRange.inRange(test));
    }
}
