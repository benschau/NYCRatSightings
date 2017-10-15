package cs2340.nycratsightings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

/**
 * Created by Alex
 */

public class DetailedViewActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView uniqueIDDetailed, creationDateDetailed, locationTypeDetailed, incidentZipDetailed,
    incidentAddressDetailed, cityDetailed, boroughDetailed, latitudeDetailed, longitudeDetailed;

    private Sighting currSighting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedview);

        final Button returnToList = findViewById(R.id.returnToList);

        returnToList.setOnClickListener(this);

        currSighting = this.getIntent().getParcelableExtra("CURRENT_SIGHTING");
        if (currSighting == null) {
            Log.d("DETAILED_VIEW", "No current sighting.");
            finish();
        }

        uniqueIDDetailed = findViewById(R.id.unique_id_detailed);
        creationDateDetailed = findViewById(R.id.creation_date_detailed);
        locationTypeDetailed = findViewById(R.id.location_type_detailed);
        incidentZipDetailed = findViewById(R.id.incident_zip_detailed);
        incidentAddressDetailed = findViewById(R.id.incident_address_detailed);
        cityDetailed = findViewById(R.id.city_detailed);
        boroughDetailed = findViewById(R.id.borough_detailed);
        latitudeDetailed = findViewById(R.id.latitude_detailed);
        longitudeDetailed = findViewById(R.id.longitude_detailed);

        uniqueIDDetailed.setText(currSighting.getUniqueKey());
        creationDateDetailed.setText(currSighting.getCreationDate());
        locationTypeDetailed.setText(currSighting.getLocationType());
        incidentZipDetailed.setText(currSighting.getIncidentZip());
        incidentAddressDetailed.setText(currSighting.getIncidentAddress());
        cityDetailed.setText(currSighting.getCity());
        boroughDetailed.setText(currSighting.getBorough());
        latitudeDetailed.setText(currSighting.getLatitude());
        longitudeDetailed.setText(currSighting.getLongitude());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.returnToList:
                finish();
                break;
            default:
                break;
        }
    }

}
