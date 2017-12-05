package cs2340.nycratsightings.controller;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import cs2340.nycratsightings.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by misterhuac on 12/4/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig twitterAuthConfig = new TwitterAuthConfig(
                getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret)
        );
        Fabric.with(this, new Twitter(twitterAuthConfig), new Crashlytics());
    }

}
