package cs2340.nycratsightings;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cs2340.nycratsightings.model.GraphInfo;
import cs2340.nycratsightings.model.Sighting;

import static org.junit.Assert.assertEquals;

/**
 * This represents a graphInfoUnitTests object.
 * @author Lucas Liu on 11/12/2017.
 */

public class graphInfoUnitTests {

    Calendar from;
    Calendar to;
    ArrayList<Sighting> nullSightingData;
    ArrayList<Sighting> emptySightingData;
    ArrayList<Sighting> filledSightingData;
    TreeMap<Calendar, Integer> map;
    Map<Calendar, Integer> expected;
    private Calendar nullfrom;
    private Calendar nullto;


    /**
     * Sets up variables for testing.
     */
    @Before
    public void setUp() {
        from = new GregorianCalendar(2017, 4 - 1, 30);
        to = new GregorianCalendar(2014, 2 - 1, 10);

        String[] data1 = {"33", "03/2/1990 f", "irrelevant", "irrelevant", "irrelevant"
                , "irrelevant", "irrelevant", "0", "0"};
        Sighting sighting1 = new Sighting(data1);
        String[] data2 = {"33", "02/30/2014 f", "irrelevant", "irrelevant", "irrelevant"
                , "irrelevant", "irrelevant", "0", "0"};
        Sighting sighting2 = new Sighting(data2);
        String[] data3 = {"33", "03/10/2015 f", "irrelevant", "irrelevant", "irrelevant"
                , "irrelevant", "irrelevant ", "0", "0"};
        Sighting sighting3 = new Sighting(data3);
        String[] data4 = {"33", "03/10/2015 f", "irrelevant", "irrelevant", "irrelevant"
                , "irrelevant", "irrelevant", "0", "0"};
        Sighting sighting4 = new Sighting(data4);
        String[] data5 = {"33", "03/2/2017 f", "irrelevant", "irrelevant", "irrelevant"
                , "irrelevant", "irrelevant", "0", "0"};
        Sighting sighting5 = new Sighting(data5);
        String[] data6 = {"33", "03/2/2018 f", "irrelevant", "irrelevant", "irrelevant"
                , "irrelevant", "irrelevant", "0", "0"};
        Sighting sighting6 = new Sighting(data6);
        emptySightingData = new ArrayList<Sighting>();
        filledSightingData = new ArrayList<Sighting>();
        filledSightingData.add(sighting1);
        filledSightingData.add(sighting2);
        filledSightingData.add(sighting3);
        filledSightingData.add(sighting4);
        filledSightingData.add(sighting5);
        filledSightingData.add(sighting6);


        GraphInfo test = new GraphInfo(to, from, filledSightingData);
        map = test.getMap();

        expected = new TreeMap<>();
        expected.put(new GregorianCalendar(2014, 1, 30), 1);
        expected.put(new GregorianCalendar(2015, 2, 10), 2);
        expected.put(new GregorianCalendar(2017, 2, 2), 1);
    }

    /**
     * Tests that passing in null variables result in a nullpointer
     * The Activity Class should be the one handling invalid data.
     */
    @Test(expected = NullPointerException.class)
    public void testNullFrom() {
        GraphInfo test = new GraphInfo(nullfrom, to, filledSightingData);
    }
    /**
     * Tests that passing in null variables result in a nullpointer
     * The Activity Class should be the one handling invalid data.
     */
    @Test(expected = NullPointerException.class)
    public void testNullTo() {
        GraphInfo test = new GraphInfo(from, nullto, filledSightingData);
    }
    /**
     * Tests that passing in null variables result in a nullpointer
     * The Activity Class should be the one handling invalid data.
     */
    @Test(expected = NullPointerException.class)
    public void testNullSightingData() {
        GraphInfo test = new GraphInfo(from, to, nullSightingData);
    }

    /**
     * The logic for bad dates is unique to the GraphInfo class,
     * if from is before to, then these two dates are invalid
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBadDates() {
        GraphInfo test = new GraphInfo(to, from, filledSightingData);
    }

    /**
     * The GraphInfo should not run in the event that
     * there are no rats currently recorded on the database.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptySightingData() {
        GraphInfo test = new GraphInfo(to, from, emptySightingData);
    }

    /**
     * tests that the graphinfo's backing map is equal
     * to the expected map.
     */
    @Test
    public void testMapEquality() {
        assertEquals(map, expected);
    }
}
