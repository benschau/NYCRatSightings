package cs2340.nycratsightings;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RatMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private ArrayList<Sighting> sightings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: Use this to add rats to database
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        // TODO: Change this so that we don't have to load the csv for every activity that needs this
        InputStream csvFile = getResources().openRawResource(R.raw.xaa);
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile));

        SightingData sd = new SightingData(reader);

        sightings = sd.getBackingData();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        RatMarkerAdapter ratInfo = new RatMarkerAdapter(getLayoutInflater());

        LatLng rat = null;
        Double lat, lng;
        String desc;
        for (Sighting sighting : sightings) {
            lat = Double.parseDouble(sighting.getLatitude());
            lng = Double.parseDouble(sighting.getLongitude());

            desc = createDesc(sighting.getBorough(),
                    sighting.getCity(),
                    sighting.getIncidentAddress() + " " + sighting.getIncidentZip(),
                    sighting.getCreationDate());

            rat = new LatLng(lat, lng);
            googleMap.addMarker(
                    new MarkerOptions().position(rat).title(sighting.getUniqueKey()).snippet(desc)
            );
        }

        if (sightings != null)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(rat));

        googleMap.setInfoWindowAdapter(ratInfo);
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        marker.showInfoWindow();

        return true;
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        // TODO: Go to rat info, show options to edit
    }

    private void addMarker(GoogleMap googleMap, Double lat, Double lon,
                           String title, String snippet){
        googleMap.addMarker(
                new MarkerOptions().position(new LatLng(lat, lon))
                                   .title(title)
                                   .snippet(snippet)
        );
    }

    public String createDesc(String borough, String city, String addr, String date) {
        return String.format("Borough: %s\nCity: %s\nAddress: %s\nDate: %s", borough, city, addr, date);
    }
}
