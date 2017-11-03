package cs2340.nycratsightings.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Model for account information.
 * @author Benson Chau (9/25/2017)
 * @version 1.0
 *
 */
@IgnoreExtraProperties
public class User {
    /**
     * Instance variables are required to be public in order for Firebase to serialize them
     * in the database.
     */
    public String mEmail;

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
}
