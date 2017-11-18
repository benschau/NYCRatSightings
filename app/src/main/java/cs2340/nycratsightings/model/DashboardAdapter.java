package cs2340.nycratsightings.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cs2340.nycratsightings.R;

/** Represents a DashboardAdapter.
 * @author Mariam & co. (10/11/17)
 * @version 1.0
 */
public class DashboardAdapter extends ArrayAdapter<Sighting>{
    Context ctx;
    ArrayList<Sighting> values;
    private TextView uniqueID;
    private TextView dateCreated;
    private TextView locationType;

    /**
     * Constructor for DashboardAdapter.
     * @param context the context
     * @param values the list of values
     */
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

        return mView;
    }

    /**
     * Gets sighting values.
     * @param view the view
     */
    public void bindValues(View view) {
        //Set up item layout params
        uniqueID = view.findViewById(R.id.unique_id);
        dateCreated = view.findViewById(R.id.date_created);
        locationType = view.findViewById(R.id.location_type);

    }

    /**
     * Sets the sighting values.
     * @param sighting the Sighting object
     */
    public void setItemValues(Sighting sighting) {

        uniqueID.setText(sighting.getUniqueKey());
        dateCreated.setText(sighting.getCreationDate());
        locationType.setText(sighting.getLocationType());

    }

}
