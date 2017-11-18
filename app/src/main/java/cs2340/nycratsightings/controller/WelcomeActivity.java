package cs2340.nycratsightings.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.controller.LoginActivity;
import cs2340.nycratsightings.controller.RegisterActivity;

/** Represents a WelcomeActivity.
 * @author --
 * @version 1.0
 */
public class WelcomeActivity extends Activity implements View.OnClickListener{

    private final String TAG = "WelcomeActivity";
    /*private TextView mTitle;
    private TextView mLoginText;
    private TextView mRegisterText;
    private Typeface mTypeFace;*/
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView mTitle;
        TextView mLoginText;
        TextView mRegisterText;
        Typeface mTypeFace;

        mTypeFace = Typeface.createFromAsset(getAssets(), "font/Trocchi-Regular.ttf");

        mTitle = findViewById(R.id.title);
        mLoginText = findViewById(R.id.loginText);
        mRegisterText = findViewById(R.id.registerText);

        mTitle.setTypeface(mTypeFace);
        mLoginText.setOnClickListener(this);
        mRegisterText.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // signed in
                    Log.d(TAG, "OnAuthStateChanged: Signed in.");
                    toLogin();
                } else {
                    // signed out
                    Log.d(TAG, "OnAuthStateChanged: Signed out.");
                    // TODO: Implement what to do when signed out at the login screen.
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.loginText:
                toLogin();
                break;
            case R.id.registerText:
                i = new Intent(this, RegisterActivity.class);
                this.startActivity(i);

                break;
            default:
                break;
        }
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

    /**
     * This method goes to the login screen.
     */
    public void toLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        this.startActivity(i);
    }

}
