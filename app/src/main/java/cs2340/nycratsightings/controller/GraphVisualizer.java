package cs2340.nycratsightings.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.model.DateRange;
import cs2340.nycratsightings.model.GraphInfo;
import cs2340.nycratsightings.model.Sighting;

public class GraphVisualizer extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private final String TAG = "GraphVisualizer";

    private ArrayList<Sighting> mSightings;
    private GraphView mGraph;
    private DatePicker mDateFrom, mDateTo;
    private DateRange mDateRange;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_visualizer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGraph = findViewById(R.id.graph1);

        mSightings = SplashActivity.mSightingData.getRatData();
        mDateRange = new DateRange(mSightings.get(0).parseCreationDate(),
                mSightings.get(mSightings.size() - 1).parseCreationDate());

        updateGraph();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        getMenuInflater().inflate(R.menu.rat_map_overflow, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_logout:
                signOut();
                finish();
                break;
            case R.id.menu_about:
                break;
            case R.id.rat_range:
                createRangeDialog(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method creates and sets the date range from the user.
     * @param context the context
     */
    private void createRangeDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        Calendar today = Calendar.getInstance();

        dialog.setContentView(R.layout.daterange_dialog);
        dialog.setTitle("Date Range");
        dialog.setOnDismissListener(this);

        submit = dialog.findViewById(R.id.submit);
        mDateFrom = dialog.findViewById(R.id.dateFrom);
        mDateTo = dialog.findViewById(R.id.dateTo);

        mDateFrom.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                null
        );

        mDateTo.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                null
        );

        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.submit:
                        mDateRange = new DateRange(
                                mDateFrom.getYear(), mDateFrom.getMonth(), mDateFrom.getDayOfMonth(),
                                mDateTo.getYear(), mDateTo.getMonth(), mDateTo.getDayOfMonth()
                        );

                        Log.d(TAG, "Date Range: " + mDateRange);

                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        updateGraph();
    }

    /**
     * updateGraph
     *  Graph the current rat data.
     */
    private void updateGraph() {
        LineGraphSeries<DataPoint> graphData;
        mSightings = SplashActivity.mSightingData.getRatData();

        Calendar from = mDateRange.getFromDate();
        Calendar to = mDateRange.getToDate();

        Integer start[] = new Integer[] {
            Integer.valueOf(from.get(Calendar.MONTH)),
            Integer.valueOf(from.get(Calendar.YEAR))
        };

        Integer end[] = new Integer[] {
            Integer.valueOf(to.get(Calendar.MONTH)),
            Integer.valueOf(to.get(Calendar.YEAR))
        };

        GraphInfo graph = new GraphInfo(start, end, mSightings);
        graphData = graph.getGraphSeries();

        mGraph.addSeries(graphData);
    }

    /**
     * signOut
     *  Sign out of Firebase.
     */
    private void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signOut();
        finish();
    }

}
