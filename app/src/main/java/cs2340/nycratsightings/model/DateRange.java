package cs2340.nycratsightings.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/** Represents a DateRange.
 * @author Benson
 * @version 1.0
 */
public class DateRange {
    private final String TAG = "DateRange";
    private Calendar from, to;

    /**
     * DateRange constructor.
     * @param from the beginning date
     * @param to the end date
     */
    public DateRange(Calendar from, Calendar to) {
        this.from = from;
        this.to = to;
    }

    /**
     * More detailed DateRange Constructor.
     * @param yrFrom the beginning year
     * @param monFrom the beginning month
     * @param dayFrom the beginning day
     * @param yrTo the end year
     * @param monTo the end month
     * @param dayTo the end day
     */
    public DateRange(int yrFrom, int monFrom, int dayFrom,
                     int yrTo, int monTo, int dayTo) {

        // NOTE: Month is incremented by one here. For some reason, it looks as if the Gregorian calendar is always a
        // month behind?

        this.from = new GregorianCalendar(yrFrom, monFrom + 1, dayFrom);
        this.to = new GregorianCalendar(yrTo, monTo + 1, dayTo);
    }

    /**
     * Checks whether date is in range.
     * @param date the date
     * @return whether date is in range or not
     */
    public boolean inRange(Calendar date) {
        /**
        Log.d(TAG, "from: " + this.from);
        Log.d(TAG, "to: " + this.to);
        Log.d(TAG, "compared to: " + date);

        Log.d(TAG, "date.compareTo(from) == " + date.compareTo(this.from));
        Log.d(TAG, "date.compareTo(to) == " + date.compareTo(this.to));
        Log.d(TAG, "date between from and to == " + ((date.compareTo(this.from) >= 0) && (date.compareTo(this.to) <= 0)));
         */

        return (date.compareTo(from) >= 0) && (date.compareTo(to) <= 0);
    }

    @Override
    public String toString(){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String formattedFrom = format.format(from.getTime());
        String formattedTo = format.format(to.getTime());

        return formattedFrom + " - " + formattedTo;
    }
}