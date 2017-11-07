package cs2340.nycratsightings.model;

import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class designed to handle graphs.
 * @author Lucas Liu
 */
public class GraphInfo {

    private final String TAG = "GraphInfo";
    private int monthsToTraverse;
    private TreeMap<String, Integer> map;

    /**
     * Sole constructor for GraphInfo.
     * @param from a start month and year pair
     * @param to an end month and year pair
     * @param data rat data in the form of an arraylist.
     */
    public GraphInfo(Calendar from, Calendar to, ArrayList<Sighting> data){
        map = new TreeMap<>();

        DateRange daterange = new DateRange(from, to);

        Calendar tCreateDate;
        for (Sighting s : data) {
            tCreateDate = s.parseCreationDate();
            if(daterange.inRange(tCreateDate)) {
                Log.d("WTF", getKey(s));
                if(map.containsKey(getKey(s))) {
                    map.put(getKey(s), map.get(getKey(s)) + 1);
                } else {
                    map.put(getKey(s), 1);
                }
            }
        }

        monthsToTraverse = (to.get(Calendar.YEAR) * 12 + to.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));
        Log.d(TAG, "months traversed: " + monthsToTraverse);
    }

    public int getMonthRange() {
        return monthsToTraverse;
    }

    /**
     * getGraphSeries
     *  Creates a set of data points to draw on the graph from the generated HashTable.
     * @return a set of data points to draw on the graph
     */
    public LineGraphSeries<DataPoint> getGraphSeries() {
        LineGraphSeries<DataPoint> lineSeries;
        ArrayList<DataPoint> lineData = new ArrayList<>();
        DataPoint dataPoints[];

        // TODO: Return LineGraphSeries set of datapoints for use in graphing.
        Set<String> set = map.keySet();
        int i = 0;
        for (String e : set) {
            //e.set(Calendar.MONTH, e.get(Calendar.MONTH) - 1);
            Integer numSightings = map.get(e);

            Log.d("BUILD LINESERIES", "Entry: (" + e + ", " + numSightings + ")");

            DataPoint dp = new DataPoint(i, numSightings);

            lineData.add(dp);
            i++;
        }

        dataPoints = lineData.toArray(new DataPoint[lineData.size()]);
        Log.d("wtF", "" + dataPoints[1]);
        lineSeries = new LineGraphSeries<>(dataPoints);

        return lineSeries;
    }
    private static String getKey(Sighting data){
        String date = data.getCreationDate();
        String[] elements = date.split(" ");
        String[] dateElements = elements[0].split("/");
        String out = dateElements[0] + " " + dateElements[2];

        return out;
    }
}
