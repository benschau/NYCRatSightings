package cs2340.nycratsightings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = findViewById(R.id.login);
        final Button cancelLogin = findViewById(R.id.cancelLogin);
        final TextView signup = findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // signed in
                    Log.d(TAG, "OnAuthStateChanged: Signed in.");
                    toMain();
                } else {
                    // signed out
                    Log.d(TAG, "OnAuthStateChanged: Signed out.");
                    // TODO: Implement what to do when signed out at the login screen.
                }
            }
        };

        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        cancelLogin.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelLogin:
                finish();
                break;
            case R.id.signup:
                this.startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:
                login();
                break;
            default:
                break;
        }
    }

    public void toMain() {
        this.startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * Login to the Firebase system.
     * If the login fails, a toast will appear to indicate login failure.
     */
    public void login() {
        String email, passwd;

        email = ((EditText) findViewById(R.id.email)).getText().toString();
        passwd = ((EditText) findViewById(R.id.password)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, R.string.login_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
