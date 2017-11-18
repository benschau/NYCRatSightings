package cs2340.nycratsightings.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.model.RatMarkerAdapter;
import cs2340.nycratsightings.model.Sighting;
import cs2340.nycratsightings.model.SightingData;
import cs2340.nycratsightings.model.DateRange;

/** Represents a RatMapActivity.
 * @author Benson?
 * @version 1.0
 */
public class RatMapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, DialogInterface.OnDismissListener {
    private final String TAG = "RatMapActivity";

    private GoogleMap mGMap;
    private ArrayList<Sighting> mSightings;

    private DatePicker mDateFrom;
    private DatePicker mDateTo;
    //private Button submit;

    private DateRange mDateRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RatMapActivity.this, AddSightingActivity.class));

                // TODO: Use this to add rats to database


                refreshMap();
            }
        });

        SplashActivity.mSightingData.syncRatData();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mSightings = SplashActivity.mSightingData.getRatData();
        // The default date range should contain all elements in the array list.
        mDateRange = new DateRange(mSightings.get(0).parseCreationDate(),
                 mSightings.get(mSightings.size() - 1).parseCreationDate());

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGMap = googleMap;

        refreshMap();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        marker.showInfoWindow();

        return true;
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        Intent i;
        Bundle b;
        Sighting currSighting = null;
        String sightingKey;
        String uniqueKey = marker.getTitle();

        for (Sighting s : mSightings) {
            sightingKey = s.getUniqueKey();
            if (sightingKey.equals(uniqueKey)) {
                currSighting = s;
                break;
            }
        }

        if (currSighting != null) {
            Log.d(TAG, "OnInfoWindowClick: " + currSighting.toString());

            b = new Bundle();
            b.putParcelable("CURRENT_SIGHTING", currSighting);
            i = new Intent(this, DetailedViewActivity.class);
            i.putExtras(b);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        getMenuInflater().inflate(R.menu.rat_map_overflow, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_logout:
                signOut();
                finish();
                break;
            case R.id.menu_about:
                break;
            case R.id.rat_range:
                createRangeDialog(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        refreshMap();
    }

    /**
     * sign out method
     */
    private void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signOut();
        finish();
    }

    /**
     * This method gets the date range from the user.
     * @param context the context
     */
    private void createRangeDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        Calendar today = Calendar.getInstance();

        Button submit;

        dialog.setContentView(R.layout.daterange_dialog);
        dialog.setTitle("Date Range");
        dialog.setOnDismissListener(this);

        submit = dialog.findViewById(R.id.submit);
        mDateFrom = dialog.findViewById(R.id.dateFrom);
        mDateTo = dialog.findViewById(R.id.dateTo);

        mDateFrom.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                null
        );

        mDateTo.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                null
        );

        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.submit:
                        mDateRange = new DateRange(
                                mDateFrom.getYear(), mDateFrom.getMonth(), mDateFrom.getDayOfMonth(),
                                mDateTo.getYear(), mDateTo.getMonth(), mDateTo.getDayOfMonth()
                        );

                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * Creates a description of the sighting.
     * @param borough the borough
     * @param city the city
     * @param address the address
     * @param date the date
     * @return the formatted String
     */
    private String createDesc(String borough, String city, String address, String date) {
        return String.format("Borough: %s\nCity: %s\nAddress: %s\nDate: %s", borough, city, address, date);
    }

    /**
     * Refreshes the map.
     */
    private void refreshMap() {
        SplashActivity.mSightingData.syncRatData();
        mGMap.clear();

        RatMarkerAdapter ratInfo = new RatMarkerAdapter(getLayoutInflater());

        LatLng rat = null;
        Double lat;
        Double lng;
        String desc;
        for (Sighting sighting : mSightings) {
            Log.d(TAG, "Comparing date1: " + sighting.getCreationDate() + " to date range: " + mDateRange);
            Log.d(TAG, "Date range comparison: " + mDateRange.inRange(sighting.parseCreationDate()));
            if (mDateRange.inRange(sighting.parseCreationDate())) {
                lat = Double.parseDouble(sighting.getLatitude());
                lng = Double.parseDouble(sighting.getLongitude());

                desc = createDesc(sighting.getBorough(),
                        sighting.getCity(),
                        sighting.getIncidentAddress() + " " + sighting.getIncidentZip(),
                        sighting.getCreationDate());

                rat = new LatLng(lat, lng);
                mGMap.addMarker(
                        new MarkerOptions().position(rat).title(sighting.getUniqueKey()).snippet(desc)
                );
            }
        }

        if (mSightings != null && rat != null) {
            mGMap.moveCamera(CameraUpdateFactory.newLatLng(rat));
        }

        mGMap.setInfoWindowAdapter(ratInfo);
        mGMap.setOnInfoWindowClickListener(this);
    }
}
