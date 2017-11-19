package cs2340.nycratsightings;

import org.junit.Before;
import org.junit.Test;

import cs2340.nycratsightings.controller.AddSightingActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Gerardo Prada on 11/19/17.
 */

public class VerifyLatitudeLongitudeTest {
    private final AddSightingActivity addSightingActivity = new AddSightingActivity();
    private String invalidLatitude;
    private String invalidLongitude;

    @Before
    public void setUp() {
        invalidLatitude = "invalid";
        invalidLongitude = "invalid";
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyLatitudeIllegalArgumentExeption() {
        addSightingActivity.verifyUserLatitude(null);
    }

    @Test(expected = NumberFormatException.class)
    public void verifyLatitude() {
        addSightingActivity.verifyUserLatitude(invalidLatitude);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyLongitudeIllegalArgumentExeption() {
        addSightingActivity.verifyUserLongitude(null);
    }

    @Test(expected = NumberFormatException.class)
    public void verifyLongitude() {
        addSightingActivity.verifyUserLongitude(invalidLongitude);
    }
}
