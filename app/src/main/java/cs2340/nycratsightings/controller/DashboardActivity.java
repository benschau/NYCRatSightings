/*package cs2340.nycratsightings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Find the ListView resource.
        ListView list = findViewById( R.id.ratMainList);
        //LinearLayout linlay = findViewById(R.id.linearLayout);

        //read rat data
        try {
            List<Sighting> array_list = readRatData();

            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and the
            // array as a third parameter.
            ArrayAdapter<Sighting> arrayAdapter = new ArrayAdapter<Sighting>(
                    this, android.R.layout.simple_list_item_1, array_list );

            //attach our Adapter to the ListView. This will populate all of the rows.
            list.setAdapter(arrayAdapter);
        }
        catch(FileNotFoundException e) {
            System.out.print("File not found");
        }

        //need a click listener and on-click to switch to detail view
    }

    private List<Sighting> readRatData() throws java.io.FileNotFoundException {
            FileReader in = new FileReader("small.csv");
            SightingData data = new SightingData(in);
            List<Sighting> list = data.getBackingdata();
            return list;
    }

}*/
/*package cs2340.nycratsightings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class DashboardActivity extends AppCompatActivity {

    DashboardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_listview);

        ListView mList = (ListView) findViewById(R.id.csv_listview);

        mAdapter = new DashboardAdapter(this, -1);

        //attach our Adapter to the ListView. This will populate all of the rows.
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                Toast.makeText(v.getContext(), mAdapter.getItem(pos).getBorough(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}*/

package cs2340.nycratsightings.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import cs2340.nycratsightings.model.DashboardAdapter;
import cs2340.nycratsightings.R;
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

        // Make use of SightingData
//        InputStream csvFile = getResources().openRawResource(R.raw.xaa);
        InputStream csvFile = getResources().openRawResource(R.raw.test);
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile));

        SightingData sd = new SightingData(reader);

        sightings = sd.getBackingData();

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

    /*
    private ArrayList<Sighting> loadArrayFromFile() {
        ArrayList<Sighting> sightings = new ArrayList<>();
        try {
            InputStream csvFile = getResources().openRawResource(R.raw.small);
            BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile));
            String line;
            boolean isRealData = false;

            //Read each line
            while ((line = reader.readLine()) != null) {
                //Split to separate
                String[] RowData = line.split(",");

                Sighting sighting = new Sighting();
                sighting.setBorough(RowData[23]);
                sighting.setCity(RowData[1]);
                sighting.setCreationDate(RowData[2]);
                sighting.setIncidentAddress(RowData[3]);
                sighting.setIncidentZip(RowData[4]);
                sighting.setLatitude(RowData[5]);
                sighting.setLocationType(RowData[6]);
                sighting.setLongitude(RowData[7]);

                if (isRealData) {
                    sightings.add(sighting);
                }
                isRealData = true;
            }
            return sightings;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    */
}

