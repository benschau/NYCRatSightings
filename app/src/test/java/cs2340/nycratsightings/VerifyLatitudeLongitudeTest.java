package cs2340.nycratsightings;

import org.junit.Before;
import org.junit.Test;

import cs2340.nycratsightings.controller.AddSightingActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This represents a testing object.
 * @author Gerardo Prada on 11/19/17.
 */

public class VerifyLatitudeLongitudeTest {
    private final AddSightingActivity addSightingActivity = new AddSightingActivity();
    private String invalidLatitude;
    private String invalidLongitude;

    /**
     * This is a setup method.
     */
    @Before
    public void setUp() {
        invalidLatitude = "invalid";
        invalidLongitude = "invalid";
    }

    /**
     * Verifies latitude with illegal arguments.
     */
    @Test(expected = IllegalArgumentException.class)
    public void verifyLatitudeIllegalArgumentExeption() {
        addSightingActivity.verifyUserLatitude(null);
    }

    /**
     * Verifies latitude with legal arguments.
     */
    @Test(expected = NumberFormatException.class)
    public void verifyLatitude() {
        addSightingActivity.verifyUserLatitude(invalidLatitude);
    }

    /**
     * Verifies longitude with illegal arguments.
     */
    @Test(expected = IllegalArgumentException.class)
    public void verifyLongitudeIllegalArgumentExeption() {
        addSightingActivity.verifyUserLongitude(null);
    }

    /**
     * Verifies longitude with legal arguments.
     */
    @Test(expected = NumberFormatException.class)
    public void verifyLongitude() {
        addSightingActivity.verifyUserLongitude(invalidLongitude);
    }
}
