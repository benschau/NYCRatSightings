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
import cs2340.nycratsightings.model.SightingData;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {

    ArrayList<Sighting> sightings;
    DashboardAdapter mAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_listview);

        final Button mSignOut = (Button) findViewById(R.id.dash_sign_out);
        final Button mAddSighting = findViewById(R.id.dash_add_sighting);
        ListView mList = (ListView) findViewById(R.id.csv_listview);

        mSignOut.setOnClickListener(this);
        mAddSighting.setOnClickListener(this);

        /**
         * Read the csv file with sighting data and add it to the SightingData array list.
         */
        InputStream csvFile = getResources().openRawResource(R.raw.xaa);
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile));
        SightingData sightingData = new SightingData();
        sightingData.readCSV(reader);

        /**
         * Check if "new-sighting-data.txt" exists. If it does, then the user has added new
         * sightings to the app. These are then read from the file and added to the SightingData
         * array list.
         */
        try {
            File file = new File(this.getFilesDir(), "new-sighting-data.txt");
            BufferedReader readerInternalStorage = new BufferedReader(new FileReader(file));
            sightingData.readInternalStorage(readerInternalStorage);
        } catch (FileNotFoundException e) {
            Log.e("Dashboard Activity", "new-sighting-data.txt does not exist");
        }

        sightings = sightingData.getBackingData();

        mAdapter = new DashboardAdapter(this, sightings);

        //attach our Adapter to the ListView. This will populate all of the rows.
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
        Intent i;
        Bundle b;

        Sighting currSighting = (Sighting) parent.getItemAtPosition(pos);

        Log.d("ITEMCLICK: ", currSighting.toString());

        b = new Bundle();
        b.putParcelable("CURRENT_SIGHTING", currSighting);
        i = new Intent(this, DetailedViewActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    //sign out button functionality
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
}

