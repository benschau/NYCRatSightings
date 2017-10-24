package cs2340.nycratsightings.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cs2340.nycratsightings.R;

/**
 * Created by Gerardo Prada on 10/23/17.
 */

public class AddSightingActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sighting);

        final Button cancelAddSighting = findViewById(R.id.cancel_add_sighting);
        final Button addSighting = findViewById(R.id.add_sighting);
        // Set on click action for buttons. The onClick(View v) method is called and a switch
        // statement is used to determine the next method calls based on the clicked button id.
        cancelAddSighting.setOnClickListener(this);
        addSighting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_add_sighting:
                finish();
                break;
            case R.id.add_sighting:
                addSightingToCSV();
                goToDash();
            default:
                break;
        }
    }

    // TODO: IMPLEMENT
    private void addSightingToCSV() {
        // TODO: SANITIZE INPUTS
        // TODO: AUTOGENERATE ID
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String sightingDate = dateFormat.format(date);
        String locationType = ((EditText) findViewById(R.id.location_type)).getText().toString();
        String zipCode = ((EditText) findViewById(R.id.zip)).getText().toString();
        String address = ((EditText) findViewById(R.id.address)).getText().toString();
        String city = ((EditText) findViewById(R.id.city)).getText().toString();
        String borough = ((EditText) findViewById(R.id.borough)).getText().toString();
        String latitude = ((EditText) findViewById(R.id.latitutde)).getText().toString();
        String longitude = ((EditText) findViewById(R.id.longitude)).getText().toString();
    }

    public void goToDash() {
        Intent i = new Intent(this, DashboardActivity.class);
        this.startActivity(i);
    }
}
