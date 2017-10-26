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

package cs2340.nycratsightings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {

    ArrayList<Sighting> sightings;
    DashboardAdapter mAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_listview);

        final Button mSignOut = (Button) findViewById(R.id.dash_sign_out);
        mSignOut.setOnClickListener(this);

        ListView mList = (ListView) findViewById(R.id.csv_listview);

        // Make use of SightingData
        InputStream csvFile = getResources().openRawResource(R.raw.xaa);
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
            default:
                break;
        }
    }

    public void signOut() {
        mAuth.signOut();
    }
}

