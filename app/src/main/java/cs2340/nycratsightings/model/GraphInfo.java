package cs2340.nycratsightings.model;

import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class designed to handle graphs.
 *
 * @author Lucas Liu
 */
public class GraphInfo {

    private final String TAG = "GraphInfo";
    private int monthsToTraverse;
    private TreeMap<Calendar, Integer> map;

    /**
     * Sole constructor for GraphInfo.
     *
     * @param from a start month and year pair
     * @param to   an end month and year pair
     * @param data rat data in the form of an arraylist.
     */
    public GraphInfo(Calendar from, Calendar to, ArrayList<Sighting> data) {
        map = new TreeMap<>();

        DateRange daterange = new DateRange(from, to);

        Calendar tCreateDate;
        for (Sighting s : data) {
            tCreateDate = s.parseCreationDate();
            if (daterange.inRange(tCreateDate)) {
                //Log.d("WTF", getKey(s));
                if (map.containsKey(getKeyDateDays(s))) {
                    map.put(getKeyDateDays(s), map.get(getKeyDateDays(s)) + 1);
                } else {
                    map.put(getKeyDateDays(s), 1);
                }
            }
        }

        monthsToTraverse = (to.get(Calendar.YEAR) * 12 + to.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));
        //Log.d(TAG, "months traversed: " + monthsToTraverse);
    }

    /**
     * Generates a map key given a sighting, this one
     * generates a string and ignores the day
     * The graph will display months
     * no longer in use
     * @param data sighting whose key should be found
     * @return a String key
     */
    private static String getKey(Sighting data) {
        String date = data.getCreationDate();
        String[] elements = date.split(" ");
        String[] dateElements = elements[0].split("/");
        String out = dateElements[0] + " " + dateElements[2];

        return out;
    }

    /**
     * generates a map key given a sighting, this one
     * generates a calender ignoring day of month
     * The graph will display dates in the x axis as months
     * no longer in use
     * @param data
     * @return
     */
    private static Calendar getKeyDate(Sighting data) {
        String date = data.getCreationDate();
        String[] elements = date.split(" ");
        String[] dateElements = elements[0].split("/");
        String out = dateElements[0] + " " + dateElements[2];
        Calendar outvar = new GregorianCalendar(Integer.parseInt(dateElements[2]), Integer.parseInt(dateElements[0]) - 1, 1);
        return outvar;
    }

    /**
     * generates a map key given a sighting, this one
     * generates a calendar including day of month
     * the graph will display dates as differention for days
     *
     * @param data
     * @return
     */
    private static Calendar getKeyDateDays(Sighting data) {
        String date = data.getCreationDate();
        String[] elements = date.split(" ");
        String[] dateElements = elements[0].split("/");
        String out = dateElements[0] + " " + dateElements[2];
        Calendar outvar = new GregorianCalendar(Integer.parseInt(dateElements[2]), Integer.parseInt(dateElements[0]) - 1, Integer.parseInt(dateElements[1]));
        return outvar;
    }


    /**
     * getGraphSeries
     * Creates a set of data points to draw on the graph from the generated HashTable.
     *
     * @return a set of data points to draw on the graph
     */
    public LineGraphSeries<DataPoint> getGraphSeries(int[] dummy) {
        LineGraphSeries<DataPoint> lineSeries;
        ArrayList<DataPoint> lineData = new ArrayList<>();
        DataPoint dataPoints[];

        // TODO: Return LineGraphSeries set of datapoints for use in graphing.
        Set<Calendar> set = map.keySet();
        int i = 0;
        for (Calendar e : set) {
            //e.set(Calendar.MONTH, e.get(Calendar.MONTH) - 1);
            Integer numSightings = map.get(e);

            //Log.d("BUILD LINESERIES", "Entry: (" + e + ", " + numSightings + ")");

            DataPoint dp = new DataPoint(e.getTime(), numSightings);

            lineData.add(dp);
            i++;
        }

        dataPoints = lineData.toArray(new DataPoint[lineData.size()]);
        //Log.d("wtF", "" + dataPoints[1]);
        lineSeries = new LineGraphSeries<>(dataPoints);
        dummy[0] = dataPoints.length;
        return lineSeries;
    }
    public TreeMap getMap(){
        return map;
    }
}
