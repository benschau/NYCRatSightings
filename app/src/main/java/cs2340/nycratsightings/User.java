package cs2340.nycratsightings;

/**
 * Created by Benson Chau on 9/25/2017.
 *
 * Model for account information.
 */

public class User {
    private String mEmail;
    private String mPassword;
    private String mAccountState;

    /**
     * Default constructor
     */
    public User () {

    }
    /**
     * Create a new user
     * @param email user's email
     * @param passwd user's passwd
     */
    public User (String email, String passwd) {
        mEmail = email;
        mPassword = passwd;
        mAccountState = "unlocked";
    }

    public void setmEmail(String email) {
        mEmail = email;
    }
    public void setmPassword(String password) {
        mPassword = password;
    }
    public void setmAccountState(String accountState) {
        mAccountState = accountState;
    }

    public String getmEmail() {
        return mEmail;
    }
    public String getmPassword() {
        return mPassword;
    }
    public String getmAccountState() {
        return mAccountState;
    }

    /**
     * Check user's credentials against given parameters.
     * @param email email input
     * @param passwd password input
     * @return true if email and password match, false if otherwise.
     */
    public boolean validateLogin(String email, String password) {
        return (email.equals(mEmail)) && (password.equals(mPassword));
    }
}
