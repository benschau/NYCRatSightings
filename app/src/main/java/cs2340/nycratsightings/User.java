package cs2340.nycratsightings;

/**
 * Created by Benson Chau on 9/25/2017.
 *
 * Model for account information.
 */

public class User {
    private String mEmail;
    private String mPasswd;

    /**
     * Create a new user
     * @param email user's email
     * @param passwd user's passwd
     */
    public User (String email, String passwd) {
        mEmail = email;
        mPasswd = passwd;
    }

    /**
     * Check user's credentials against given parameters.
     * @param email email input
     * @param passwd password input
     * @return true if email and password match, false if otherwise.
     */
    public boolean validateLogin(String email, String passwd) {
        return (email.equals(mEmail)) && (passwd.equals(mPasswd));
    }
}
