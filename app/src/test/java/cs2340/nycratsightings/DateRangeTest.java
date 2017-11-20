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
    private final DateRange mDateRange;

    /**
     * DateRangeTEst constructor
     */
    public DateRangeTest() {
        final int year1 = 1998;
        final int year2 = 1999;
        final int month1 = 8;
        final int month2 = 5;
        final int day1 = 10;
        final int day2 = 11;
        Calendar from = new GregorianCalendar(year1, month1, day1);
        Calendar to = new GregorianCalendar(year2, month2, day2);
        mDateRange = new DateRange(from, to);
    }

    /**
     * method to test if the dates in the DateRange object are valid.
     */
    @Test
    public void validDateRangeValidator() {
        final int year1 = 1998;
        final int year2 = 1999;
        final int month1 = 8;
        final int month2 = 5;
        final int day1 = 10;
        final int day2 = 11;
        Calendar from = new GregorianCalendar(year1, month1, day1);
        Calendar to = new GregorianCalendar(year2, month2, day2);

        // check if the date range is correct, given a valid date in either from or to
        from.set(MINUTE, 0);
        from.set(HOUR, 12);

        mDateRange.setFromDate(from);
        assertTrue(mDateRange.validateDateRange());

        mDateRange.setToDate(to);
        assertTrue(mDateRange.validateDateRange());

        // check if the date range is incorrect, given a invalid date in either from or to
        from.set(MINUTE, 50)
        from.set(HOUR, 12)

        mDateRange.setFromDate(from);
        assertFalse(mDateRange.validateDateRange());

        mDateRange.setToDate(to);
        assertFalse(mDateRange.validateDateRange());
    }

    /**
     * method to test if in range
     */
    @Test
    public void inRangeValidator() {
        final int year97 = 1997;
        final int year98 = 1998;
        final int year99 = 1999;
        final int year00 = 2000;
        final int month1 = 1;
        final int month5 = 5;
        final int month8 = 8;
        final int month9 = 9;
        final int day09 = 9;
        final int day10 = 10;
        final int day11 = 11;
        // test a calendar that's actually in range
        Calendar test = new GregorianCalendar(year98, month9, day10);
        assertTrue(mDateRange.inRange(test));

        // test a calendar that's before the 'from' date in the date range
        test = new GregorianCalendar(year97, month5, day09);
        assertFalse(mDateRange.inRange(test));

        // test a calendar that's after the 'to' date in the date range
        test = new GregorianCalendar(year00, month1, day10);
        assertFalse(mDateRange.inRange(test));

        // test calendars on the to and from dates
        test = new GregorianCalendar(year98, month8, day10);
        assertTrue(mDateRange.inRange(test));
        test = new GregorianCalendar(year99, month5, day11);
        assertTrue(mDateRange.inRange(test));
    }
}
