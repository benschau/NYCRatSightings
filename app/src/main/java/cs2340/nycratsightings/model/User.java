package cs2340.nycratsightings.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Benson Chau on 9/25/2017.
 *
 * Model for account information.
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
     * Create a new user
     * @param email user's email
     */
    public User (String email) {
        mEmail = email;
    }
}
