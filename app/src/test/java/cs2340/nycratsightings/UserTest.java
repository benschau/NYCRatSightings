package cs2340.nycratsightings;
import org.junit.Test;

import cs2340.nycratsightings.model.User;
import static org.junit.Assert.*;

/**
 * Represents a UserTest Object
 * @author Alex on 11/17/17.
 */
public class UserTest {
    private final User mUser1;
    private final User mUser2;
    private final User mUser3;
    private final User mUser4;
    private final User mUser5;

    /**
     * UserTest Constructor
     */
    public UserTest() {
        mUser1 = new User("");
        mUser2 = new User("abc123");
        mUser3 = new User("a@a.com");
        mUser4 = new User("alexander.zhang@alumni.emory.edu");
        mUser5 = new User("1234567897@abcdefg.htmlz");

    }

    /**
     * Test method for User class's hasValidEmail method
     */
    @Test
    public void testHasValidEmail() {
        assertFalse(mUser1.hasValidEmail());
        assertFalse(mUser2.hasValidEmail());
        assertTrue(mUser3.hasValidEmail());
        assertTrue(mUser4.hasValidEmail());
        assertFalse(mUser5.hasValidEmail());

    }

}
