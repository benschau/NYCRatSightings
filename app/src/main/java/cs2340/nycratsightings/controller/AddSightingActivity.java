package cs2340.nycratsightings.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.model.Sighting;

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
                try {
                    saveNewSighting();
                    goToDashboard();
                } catch (NumberFormatException e) {
                    Toast.makeText(AddSightingActivity.this, R.string.invalid_lat_long,
                            Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }

    /**
     * Save new sighting to internal storage.
     */
    private void saveNewSighting() {
        String id = generateSightingId();
        String sightingDate = generateSightingDate();
        String locationType = ((EditText) findViewById(R.id.location_type)).getText().toString();
        String zipCode = ((EditText) findViewById(R.id.zip)).getText().toString();
        String address = ((EditText) findViewById(R.id.address)).getText().toString();
        String city = ((EditText) findViewById(R.id.city)).getText().toString();
        String borough = ((EditText) findViewById(R.id.borough)).getText().toString();
        String latitude = ((EditText) findViewById(R.id.latitutde)).getText().toString();
        String longitude = ((EditText) findViewById(R.id.longitude)).getText().toString();

        // Verify user input.
        // If this fails, a NumberFormatException is thrown, caught in the switch case
        // that called the saveNewSighting() method, and a toast is displayed
        Double.parseDouble(latitude);
        Double.parseDouble(longitude);

        String[] sightingArray = {id, sightingDate, locationType, zipCode, address, city, borough,
                latitude, longitude};
        Sighting newSighting = new Sighting(sightingArray);

        File file = new File(this.getFilesDir(), "new-sighting-data.txt");
        try {
            PrintWriter pw = new PrintWriter(file);
            newSighting.saveToFile(pw);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("AddSightingActivity", "Error opening text file to add data");
        }
    }

    public void goToDashboard() {
        Intent i = new Intent(this, DashboardActivity.class);
        this.startActivity(i);
    }

    /**
     * Generate random number in range [1, 30000000] for unique id
     *
     * @return sighting id
     */
    private String generateSightingId() {
        Random random = new Random();
        int r = random.nextInt(30000000) + 1;
        return Integer.toString(r);
    }

    /**
     * Generate sighting date based on creation date
     *
     * @return sighting date
     */
    private String generateSightingDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
