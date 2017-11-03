package cs2340.nycratsightings.model;

/** Represents an Admin.
 * @author --
 * @version 1.0
 */
public class Admin extends User {

    /**
     * Default empty constructor for Admin
     */
    public Admin () {
        super();
    }

    /**
     * Constructor for Admin that takes in an email.
     * @param email admin user's email
     */
    public Admin (String email) {
        super(email);
    }
}
