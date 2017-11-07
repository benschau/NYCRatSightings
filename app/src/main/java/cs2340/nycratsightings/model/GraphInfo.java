package cs2340.nycratsightings.model;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class designed to handle graphs.
 * @author Lucas Liu
 */
public class GraphInfo {

    private int monthsToTraverse;
    private TreeMap<String, Integer> map;
    private Hashtable<String[], Integer> ratData;

    /* public static void main(String[] args){
        String[] data = {"dank","1/2/2015 11:58","thre","thre","thre","thre","thre","thre","thre"};
        String[] data2 = {"dank","10/8/2015 11:58","thre","thre","thre","thre","thre","thre","thre"};
        String[] dat3 = {"dank","10/8/2015 11:58","thre","thre","thre","thre","thre","thre","thre"};
        Sighting tester = new Sighting(data);
        Sighting tester2 = new Sighting(data2);
        Sighting tester3 = new Sighting(dat3);
        Calendar dank = tester.parseCreationDate();
        Calendar dank2 = tester2.parseCreationDate();
        ArrayList<Sighting> testSightings = new ArrayList<>();
        testSightings.add(tester);
        testSightings.add(tester2);
        testSightings.add(tester3);
        Integer[] start = {10,2015};
        Integer[] end = {1,2015};
        TreeMap<Integer[],Integer> test = new TreeMap<>();

        Grapher thedank = new Grapher(start,end,testSightings);
        System.out.println(thedank.map.entrySet());
    } */

    /**
     * Sole constructor for GraphInfo.
     * @param start a start month and year pair in the form of an array of Integers.
     * @param end an end month and year pair in the form of an array of Integers.
     * @param data rat data in the form of an arraylist.
     */
    public GraphInfo(Integer[] start, Integer[] end, ArrayList<Sighting> data){
        map = new TreeMap<>();
        ratData = new Hashtable<>();

        DateRange daterange = new DateRange(end[1], end[0] - 1, 1, start[1], start[0], 1);
        boolean sent = true;
        int i = data.size() - 1;

        while(sent){
            Sighting traverse = data.get(i);
            if(daterange.inRange(traverse.parseCreationDate())){
                if(map.containsKey(getKey(traverse))){
                    map.put(getKey(traverse), map.get(getKey(traverse)) + 1);
                } else {
                    map.put(getKey(traverse),1);
                }
            }
            if(i == 0){
                sent = false;
            }
            i--;
        }
    }

    /**
     * getGraphSeries
     *  Creates a set of data points to draw on the graph from the generated HashTable.
     * @return a set of data points to draw on the graph
     */
    public LineGraphSeries<DataPoint> getGraphSeries() {
        LineGraphSeries<DataPoint> lineData = new LineGraphSeries<>();

        // TODO: Return LineGraphSeries set of datapoints for use in graphing.

        return lineData;
    }

    /**
     * getKey
     *  Get the key that will be used in a treemap from a sighting.
     * @param data the sighting from which we get the key from
     * @return the key used in the hashtable.
     */
    private static String getKey(Sighting data){
        String date = data.getCreationDate();
        String[] elements = date.split(" ");
        String[] dateElements = elements[0].split("/");
        String out = dateElements[0] + " " + dateElements[2];

        return out;
    }
}
