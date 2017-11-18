package cs2340.nycratsightings.controller;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.model.User;
import cs2340.nycratsightings.model.Admin;

/** Represents a RegisterActivity.
 * @author --
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    //private Button mRegister;
    private CheckBox mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button mRegister;

        mRegister = findViewById(R.id.register);
        mUserType = findViewById(R.id.userType);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();

        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.register:
                register();
                break;
            default:
                break;
        }
    }

    /**
     * This method saves the new user according to their type (admin or reg user).
     * @param uid the user id
     * @param email the user's email
     */
    private void writeNewUser(String uid, String email) {
        User user;
        Admin admin;
        String tag;

        // TODO: Refactor- really shit
        // ^ looks fine to me doe :/
        DatabaseReference ref = mDB.getReference();
        if (mUserType.isChecked()) {
            admin = new Admin(email);
            tag = "admins";
            ref.child(tag).child(uid).setValue(admin);
        } else {
            user = new User(email);
            tag = "users";
            ref.child(tag).child(uid).setValue(user);
        }
    }

    /**
     * Registers the new user.
     */
    public void register() {
        String email, passwd;

        email = ((EditText) findViewById(R.id.email)).getText().toString();
        passwd = ((EditText) findViewById(R.id.password)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, R.string.register_failed,
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            FirebaseUser user = task.getResult().getUser();

                            writeNewUser(user.getUid(), user.getEmail());

                            finish();
                        }
                    }
                });

    }
}
