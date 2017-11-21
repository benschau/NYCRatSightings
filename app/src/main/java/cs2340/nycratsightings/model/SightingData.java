package cs2340.nycratsightings.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

/** This class manages the list of sightings in the model
 * @author Lucas & co.
 * @version 1.0
 */
public class SightingData {
    //private final String TAG = "SightingData";
    //private final String CSV_PATH = "res/raw/mimd.csv";

    private ArrayList<Sighting> mSightings;
    private final FirebaseDatabase mDB;
    private DatabaseReference mRef;

    /**
     * Constructor
     */
    public SightingData() {
        mSightings = new ArrayList<>();
        mDB = FirebaseDatabase.getInstance();

        initDB();
    }

    private void initDB() {
        mRef = mDB.getReference();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("ratdb").exists()) {
                    mSightings = readCSV();
                    Iterator<Sighting> iter = mSightings.iterator();

                    /*while (iter.hasNext()) {
                        Sighting s = iter.next();

                        addRat(s);
                    }*/
                    for (Sighting s : mSightings) {
                        s = iter.next();
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
        final String TAG = "SightingData";
        final String CSV_PATH = "res/raw/mimd.csv";

        InputStream csvFile = this.getClass().getClassLoader().getResourceAsStream(CSV_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile));

        try {
            String line;
            reader.readLine(); //skip over header
            while ((line = reader.readLine()) != null) {
                final int zero = 0;
                final int one = 1;
                final int seven = 7;
                final int eight = 8;
                final int nine = 9;
                final int sixteen = 16;
                final int twenty3 = 23;
                final int forty9 = 49;
                final int fifty = 50;

                String[] tokens = line.split(",");
                String[] relevantData = {tokens[zero],tokens[one],tokens[seven],tokens[eight], tokens[nine],
                        tokens[sixteen], tokens[twenty3], tokens[forty9], tokens[fifty]};
                Sighting sighting = new Sighting(relevantData);
                csvData.add(sighting);
            }
            reader.close();
        } catch (Exception e) {
            Log.d("EXCEPTION", "There was a problem: " + e);
        }

        return csvData;
    }

    /**
     * add rat method
     * @param sighting the sighting
     */
    public void addRat(Sighting sighting) {

        String TAG = "SightingData";
        mRef.child("ratdb").child(sighting.getUniqueKey()).setValue(sighting);

        Log.d(TAG, "addRat: " + sighting.toString());
        //mSightings.add(sighting);

        syncRatData();
    }

    /**
     * gets the rat data
     * @return the array list of sighitngs
     */
    public ArrayList<Sighting> getRatData() {
       return mSightings;
    }

    /**
     * syncs rat data with fire base
     */
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

    /**
     * Convert input strings representing 'from' and 'to' dates into Date objects. The method then
     * loops through the array list of sightings in the database, retrieves the date for each
     * sighting, and compares it to the 'from' and 'to' boundaries set by the user. If the sighting
     * is within these dates, then the data point is added to the hashmap if it is not there or else
     * the value associated with that date in the hashmap is incremented by one.
     *
     * @param from from date for sightings to display
     * @param to to date for sightings to display
     * @return HashMap containing the number of rat sightings for each date
     * @throws ParseException
     */
    public LinkedHashMap<String, Integer> getLineChartRatData(String from, String to) throws ParseException {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date fromDate = sourceFormat.parse(from);
        Date toDate = sourceFormat.parse(to);
        /**
         * LinkedHashMap is used to keep the order in which dates are added to the HashMap. This is
         * important when looping through the HashMap and adding entries to the graph to ensure that
         * the dates are in the correct order.
         */
        LinkedHashMap<String, Integer> lineChartData = new LinkedHashMap<>();
        for (Sighting s : mSightings) {
            String cd = s.getCreationDate();
            Date cdDate = sourceFormat.parse(cd);
            if ((cdDate.equals(fromDate) || cdDate.after(fromDate)) && (cdDate.equals(toDate) || cdDate.before(toDate))) {
                // Retrive the mm/dd/yyyy from the date and ignores the hh:mm:ss
                String cdNoTime = cd.substring(0, 10);
                if (lineChartData.containsKey(cdNoTime)) {
                    lineChartData.put(cdNoTime, lineChartData.get(cdNoTime) + 1);
                } else {
                    lineChartData.put(cdNoTime, 1);
                }
            }
        }
        return lineChartData;
    }
}


