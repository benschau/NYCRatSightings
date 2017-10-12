package cs2340.nycratsightings;

/**
 * Created by Mariam on 10/11/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class DashboardAdapter extends ArrayAdapter<Sighting> {
    Context ctx;
    ArrayList<Sighting> values;
    private TextView dateCreated, borough, city, initialAddress, incidentZip, latitude, longitude;

    public DashboardAdapter(Context context, ArrayList<Sighting> values) {
        super(context, -1, values);
        this.ctx = context;
        this.values = values;
    }

    @Override
    public View getView(final int pos, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.list_item, parent, false);
        bindValues(mView);
        Sighting sighting = getItem(pos);
        setItemValues(sighting);
        //We could handle the row clicks from here.
        mView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), getItem(pos).getBorough(), Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }

    public void bindValues(View view) {
        //Set up item layout params
        dateCreated = view.findViewById(R.id.date_created);
        borough = view.findViewById(R.id.borough);
        city = view.findViewById(R.id.city);
        initialAddress = view.findViewById(R.id.initial_address);
        incidentZip = view.findViewById(R.id.incident_zip);
        latitude = view.findViewById(R.id.latitude);
        longitude = view.findViewById(R.id.longitude);
    }


    public void setItemValues(Sighting sighting) {
        dateCreated.setText(sighting.getCreationDate());
        borough.setText(sighting.getBorough());
        city.setText(sighting.getCity());
        initialAddress.setText(sighting.getIncidentAddress());
        incidentZip.setText(sighting.getIncidentZip());
        latitude.setText(sighting.getLatitude());
        longitude.setText(sighting.getLongitude());
    }

}
