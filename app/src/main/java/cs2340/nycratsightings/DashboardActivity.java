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
package cs2340.nycratsightings;

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
}
