package cs2340.nycratsightings;

/**
 * Created by Alex on 9/29/17.
 */

public class Admin extends User {

    /**
     * Constructor for admin user. Call super class constructor
     */
    public Admin(String email, String password) {
        super(email, password);
    }

    public void addUser(String email, String password) {
        User user = new User(email, password);
    }

    public void deleteUser(User user) {
        user = null;
    }

    public void unlockAccount(User user) {
        user.setmAccountState("unlocked");
    }
}
