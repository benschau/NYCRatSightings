package cs2340.nycratsightings.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.model.DashboardAdapter;
import cs2340.nycratsightings.model.Sighting;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {
    private final String TAG = "DashboardActivity";


    private ArrayList<Sighting> mSightings;
    private ListView mList;
    DashboardAdapter mAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_listview);

        final Button mSignOut = findViewById(R.id.dash_sign_out);
        final Button mAddSighting = findViewById(R.id.dash_add_sighting);
        mList = findViewById(R.id.csv_listview);

        mSignOut.setOnClickListener(this);
        mAddSighting.setOnClickListener(this);

        updateSightings();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SplashActivity.mSightingData.syncRatData();

        updateSightings();
    }

    public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
        Intent i;
        Bundle b;

        Sighting currSighting = (Sighting) parent.getItemAtPosition(pos);

        Log.d(TAG, "onItemClick: " + currSighting.toString());

        b = new Bundle();
        b.putParcelable("CURRENT_SIGHTING", currSighting);
        i = new Intent(this, DetailedViewActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.dash_sign_out:
                i = new Intent(this, WelcomeActivity.class);
                this.startActivity(i);
                signOut();
                break;
            case R.id.dash_add_sighting:
                i = new Intent(this, AddSightingActivity.class);
                this.startActivity(i);
                break;
            default:
                break;
        }
    }

    public void signOut() {
        mAuth.signOut();
    }

    /**
     * Methods updates sightings array list with SightingData's backing array.
     * Called after SightingData's backing array has been updated.
     */
    private void updateSightings() {
        mSightings = SplashActivity.mSightingData.getRatData();

        mAdapter = new DashboardAdapter(this, mSightings);

        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
    }
}

