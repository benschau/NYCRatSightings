package cs2340.nycratsightings.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import cs2340.nycratsightings.model.SightingData;

/** Represents a DashboardActivity.
 * @author Mariam Marzouk
 * @version 1.0
 */

public class DashboardActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    //private final String TAG = "DashboardActivity";


    //private ArrayList<Sighting> mSightings;
    private ListView mList;
    //private DashboardAdapter mAdapter;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mList = findViewById(R.id.csv_listview);

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
        String TAG = "DashboardActivity";

        Sighting currSighting = (Sighting) parent.getItemAtPosition(pos);

        Log.d(TAG, "onItemClick: " + currSighting.toString());

        b = new Bundle();
        b.putParcelable("CURRENT_SIGHTING", currSighting);
        i = new Intent(this, DetailedViewActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        getMenuInflater().inflate(R.menu.rat_dash_overflow, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_logout:
                mAuth.signOut();
                finish();
                break;
            case R.id.menu_about:
                break;
            case R.id.add_rat:
                i = new Intent(this, AddSightingActivity.class);
                this.startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Methods updates sightings array list with SightingData's backing array.
     * Called after SightingData's backing array has been updated.
     */
    private void updateSightings() {
        ArrayList<Sighting> mSightings;
        DashboardAdapter mAdapter;
        mSightings = SplashActivity.mSightingData.getRatData();

        mAdapter = new DashboardAdapter(this, mSightings);

        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
    }
}

