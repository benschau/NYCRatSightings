package cs2340.nycratsightings.model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.regex.*;

/**
 * Model for account information.
 * @author Benson Chau (9/25/2017)
 * @version 1.0
 *
 */
@IgnoreExtraProperties
public class User {
    /**
     * Instance variables are required to be public in order for Fire base to serialize them
     * in the database.
     */
    private String mEmail;

    /**
     * Default constructor. Needed for DataSnapshot.getValue(User.class)
     */
     User() {

    }

    /**
     * Create a new user.
     * @param email user's email
     */
    public User (String email) {
        mEmail = email;
    }

    /**
     * Tests the validity of the user's email address
     * @return whether user email matches regular expression
     */
    public boolean hasValidEmail() {
        Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        Matcher regexMatcher = regex.matcher(mEmail);
        return regexMatcher.matches();

    }
}
