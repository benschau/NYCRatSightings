package cs2340.nycratsightings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent intent = new Intent(this, WelcomeActivity.class);
        //for testing purposes
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

        //finish();
    }
}
