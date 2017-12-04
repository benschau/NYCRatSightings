package cs2340.nycratsightings.model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
     public User() {

    }

    /**
     * Create a new user.
     * @param email user's email
     */
    public User (String email) {
        mEmail = email;
    }

    /**
     * Get the user's email.
     * @return email as a String
     */
    public String getEmail() { return mEmail; }

    /**
     * Set the user's email.
     * @param email to set as current email
     */
    public void setEmail(String email) { mEmail = email; }

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
