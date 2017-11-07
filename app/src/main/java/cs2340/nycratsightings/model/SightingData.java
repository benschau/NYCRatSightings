package cs2340.nycratsightings.model;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/** This class manages the list of sightings in the model
 * @author Lucas & co.
 * @version 1.0
 */
public class SightingData {
    private final String TAG = "SightingData";
    private final String CSV_PATH = "raw/xaa.csv";

    private ArrayList<Sighting> mSightings;
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;
    private DateRange totalDateRange;

    public SightingData() {
        mSightings = new ArrayList<>();
        mDB = FirebaseDatabase.getInstance();

        initDB();
    }

    private void initDB() {
        mRef = mDB.getReference();
        //mSightings = readCSV();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("ratdb").exists()) {
                    mSightings = readCSV();
                    for (Sighting s : mSightings) {
                        addRat(s);
                    }
                }

                syncRatData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Read sighting data from csv file and add it to backing array
     */
    private ArrayList<Sighting> readCSV() {
        ArrayList<Sighting> csvData = new ArrayList<>();

        InputStream csvFile = this.getClass().getClassLoader().getResourceAsStream(CSV_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile));

        try {
            String line;
            reader.readLine(); //skip over header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String[] relevantData = {tokens[0],tokens[1],tokens[7],tokens[8], tokens[9],
                        tokens[16], tokens[23], tokens[49], tokens[50]};
                Sighting sighting = new Sighting(relevantData);
                csvData.add(sighting);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return csvData;
    }

    public void addRat(Sighting sighting) {
        mRef.child("ratdb").child(sighting.getUniqueKey()).setValue(sighting);

        Log.d(TAG, "addRat: " + sighting.toString());
        mSightings.add(sighting);

        syncRatData();
    }

    public ArrayList<Sighting> getRatData() {
       return mSightings;
    }

    public void syncRatData() {
        final ArrayList<Sighting> sightings = new ArrayList<>();

        Query query = mRef.child("ratdb").orderByChild("uniqueKey");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Sighting s = snapShot.getValue(Sighting.class);

                    sightings.add(s);
                    mSightings = sightings;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


