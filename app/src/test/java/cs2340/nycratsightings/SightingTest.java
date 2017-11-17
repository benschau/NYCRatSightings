package cs2340.nycratsightings;

import org.junit.Before;
import org.junit.Test;
import cs2340.nycratsightings.model.Sighting;
import static org.junit.Assert.*;

/**
 * Created by Mariam Marzouk on 11/17/17.
 */

public class SightingTest {
    private Sighting tSighting;
    private String[] tEntries;

    @Before
    public void init() {
        tEntries = new String[9];
    }

    @Test
    public void testLegalConstructionLetters() {
        tEntries = new String[]{"a","b","c","d","e","f","g","h", "i"};
        tSighting = new Sighting(tEntries);
        assertEquals(tSighting.uniqueKey, tEntries[0]);
        assertEquals(tSighting.creationDate, tEntries[1]);
        assertEquals(tSighting.locationType, tEntries[2]);
        assertEquals(tSighting.incidentZip, tEntries[3]);
        assertEquals(tSighting.incidentAddress, tEntries[4]);
        assertEquals(tSighting.city, tEntries[5]);
        assertEquals(tSighting.borough, tEntries[6]);
        assertEquals(tSighting.latitude, tEntries[7]);
        assertEquals(tSighting.longitude, tEntries[8]);

        assertFalse(tSighting.nullEntries);
    }

    @Test
    public void testLegalConstructionNums() {
        tEntries = new String[]{"78", "89", "57", "43", "21", "90", "89", "87", "93"};
        tSighting = new Sighting(tEntries);
        assertEquals(tSighting.uniqueKey, tEntries[0]);
        assertEquals(tSighting.creationDate, tEntries[1]);
        assertEquals(tSighting.locationType, tEntries[2]);
        assertEquals(tSighting.incidentZip, tEntries[3]);
        assertEquals(tSighting.incidentAddress, tEntries[4]);
        assertEquals(tSighting.city, tEntries[5]);
        assertEquals(tSighting.borough, tEntries[6]);
        assertEquals(tSighting.latitude, tEntries[7]);
        assertEquals(tSighting.longitude, tEntries[8]);

        assertFalse(tSighting.nullEntries);
    }

    @Test
    public void testNullEntries() {
        tEntries = new String[]{"a","b", null,"dry","e","fry","g","h", "i"};
        tSighting = new Sighting(tEntries);
        assertEquals(tSighting.uniqueKey, tEntries[0]);
        assertEquals(tSighting.creationDate, tEntries[1]);

        assertTrue(tSighting.nullEntries);
        //making sure execution continued as normal after exiting loop
        assertEquals(tSighting.locationType, null);
        assertEquals(tSighting.incidentZip, "dry");
    }

    @Test
    public void testNullEntriesAgain() {
        tEntries = new String[]{"why", "is", "geerja", "tek", "this", "way", null, null, "?"};
        tSighting = new Sighting(tEntries);
        assertEquals(tSighting.uniqueKey, tEntries[0]);
        assertEquals(tSighting.creationDate, tEntries[1]);
        assertEquals(tSighting.locationType, tEntries[2]);
        assertEquals(tSighting.incidentZip, tEntries[3]);
        assertEquals(tSighting.incidentAddress, tEntries[4]);
        assertEquals(tSighting.city, tEntries[5]);

        assertTrue(tSighting.nullEntries);
        //making sure execution continued as normal after exiting loop
        assertEquals(tSighting.borough, null);
        assertEquals(tSighting.latitude, null);
        assertEquals(tSighting.longitude, "?");
    }

}



