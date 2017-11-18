package cs2340.nycratsightings.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import cs2340.nycratsightings.R;

/** Represents a MainActivity.
 * @author --
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    //private Button mAbout, mRatMap, mDashboard,mGraphVisualizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mAbout;
        Button mRatMap;
        Button mDashboard;
        Button mGraphVisualizer;

        FirebaseAuth.AuthStateListener mAuthListener;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAbout = findViewById(R.id.about);
        mRatMap = findViewById(R.id.goToRatMap);
        mDashboard = findViewById(R.id.goToDash);
        mGraphVisualizer = findViewById(R.id.goToGraph);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Log.d(TAG, "OnAuthStateChanged: Signed out.");
                }
            }
        };

        mAbout.setOnClickListener(this);
        mRatMap.setOnClickListener(this);
        mDashboard.setOnClickListener(this);
        mGraphVisualizer.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_logout:
                signOut();
                break;
            case R.id.menu_about:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToDash:
                this.startActivity(new Intent(this, DashboardActivity.class));
                break;
            case R.id.goToRatMap:
                this.startActivity(new Intent(this, RatMapActivity.class));
                break;
            case R.id.about:
                break;
            case R.id.goToGraph:
                this.startActivity(new Intent(this,GraphVisualizer.class));
                break;
        }
    }

    /**
     * This method signs out of firebase.
     */
    public void signOut() {
        mAuth.signOut();
        finish();
    }
}
