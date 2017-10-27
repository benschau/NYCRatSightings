package cs2340.nycratsightings.model;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import cs2340.nycratsightings.R;

public class RatMarkerAdapter implements GoogleMap.InfoWindowAdapter {

    private View mWindow;
    private LayoutInflater mInflater;

    RatMarkerAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        if (mWindow == null) {
            mWindow = mInflater.inflate(R.layout.rat_info_window, null);
        }

        TextView title = mWindow.findViewById(R.id.title);

        title.setText(marker.getTitle());

        title = mWindow.findViewById(R.id.snippet);
        title.setText(marker.getSnippet());

        return mWindow;
    }
}
