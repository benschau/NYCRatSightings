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

public class RatMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        RatMarkerAdapter ratInfo = new RatMarkerAdapter();
        googleMap.setInfoWindowAdapter(ratInfo);

        // TODO: Read LAT/LONG and other info from reader

        LatLng rat = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(rat).title("A rat in sydney."));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(rat));

        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        return true;
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {

    }
}
