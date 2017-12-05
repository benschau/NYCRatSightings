package cs2340.nycratsightings.controller;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.model.SightingData;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/** Represents a SplashActivity.
 * @author Benson Chau
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity {

    static SightingData mSightingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, WelcomeActivity.class);
        mSightingData = new SightingData();

        startActivity(intent);
    }
}
