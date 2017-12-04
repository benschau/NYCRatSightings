package cs2340.nycratsightings.controller;

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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.model.User;

/** Represents a LoginActivity.
 * @author Benson
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button logIn = findViewById(R.id.login);
        final LoginButton fbLogIn = findViewById(R.id.fblogin);
        final Button cancelLogin = findViewById(R.id.cancelLogin);
        final TextView signUp = findViewById(R.id.signup);

        fbLogIn.setReadPermissions("email");

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        final AccessToken accessToken = loginResult.getAccessToken();
                        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                        final User fbUser = new User();

                        Log.d(TAG, "UID: " + accessToken.getUserId() + "\n" +
                                        "Auth Content: " + accessToken.getToken());

                        GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                fbUser.setEmail(object.optString("email"));

                                Log.d(TAG, "Email: " + fbUser.getEmail());

                                mRef.child("users").child(accessToken.getUserId()).setValue(fbUser);
                            }
                        }).executeAsync();

                        toMain();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                    }
                }
        );

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

        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * This method goes to the main screen.
     */
    private void toMain() {
        this.startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * Login to the Fire base system.
     * If the login fails, a toast will appear to indicate login failure.
     */
    private void login() {
        String email;
        String password;

        email = ((EditText) findViewById(R.id.email)).getText().toString();
        password = ((EditText) findViewById(R.id.password)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
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
