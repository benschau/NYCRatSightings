package cs2340.nycratsightings;

/**
 * Created by Mariam on 10/11/17.
 */

        import android.content.Context;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;

public class DashboardAdapter extends ArrayAdapter<Sighting> {
    Context ctx;

    public DashboardAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

        this.ctx = context;
        loadArrayFromFile();
    }

    @Override
    public View getView(final int pos, View convertView, final ViewGroup parent) {

        TextView mView = (TextView) convertView;

        if (null == mView) {
            mView = new TextView(parent.getContext());
            mView.setTextSize(28);
        }

        //Set the state name as the text.
        mView.setText(getItem(pos).getBorough());
        mView.setText(getItem(pos).getCity());
        mView.setText(getItem(pos).getCreationDate());
        mView.setText(getItem(pos).getIncidentAddress());
        mView.setText(getItem(pos).getIncidentZip());
        mView.setText(getItem(pos).getUniqueKey());
        Float latitude = getItem(pos).getLatitude();
        mView.setText(String.valueOf(latitude));
        Float longitude = getItem(pos).getLongitude();
        mView.setText(String.valueOf(longitude));

        //We could handle the row clicks from here.
        mView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(parent.getContext(), getItem(pos).getBorough(), Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }

    private void loadArrayFromFile() {
        try {
            // Get input stream and Buffered Reader for our data file.
            InputStream csvFile = ctx.getResources().openRawResource(R.raw.small); //change to rat_sightings (entire file)
            BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile));
            String line;

            //Read each line
            while ((line = reader.readLine()) != null) {
                //Split to separate
                String[] RowData = line.split(",");

                Sighting sighting = new Sighting(RowData); //rowdata?
                sighting.setBorough(RowData[0]);
                sighting.setCity(RowData[1]);
                sighting.setCreationDate(RowData[2]);
                sighting.setIncidentAddress(RowData[3]);
                sighting.setIncidentZip(RowData[4]);
                sighting.setLatitude(Float.valueOf(RowData[5]));
                sighting.setLocationType(RowData[6]);
                sighting.setLongitude(Float.valueOf(RowData[7]));

                this.add(sighting);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
