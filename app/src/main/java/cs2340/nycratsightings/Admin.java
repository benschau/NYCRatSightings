package cs2340.nycratsightings;

/**
 * Created by Alex on 9/29/17.
 */

public class Admin extends User {

    /**
     * Constructor
     */
    public Admin (String email, String passwd) {
        super(email, passwd);
    }


    void addUser(String email, String passwd) {
        User user = new User(email, passwd);
    }

    void deleteUser(User user) {
        user = null;
    }

    void unlockAccount(User user) {
        user.setmPasswd(null);
    }



}
