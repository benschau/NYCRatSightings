package cs2340.nycratsightings.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
//import android.content.res.Resources;
//import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
//import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Comparator;
import java.util.GregorianCalendar;

import cs2340.nycratsightings.R;
import cs2340.nycratsightings.model.DateRange;
import cs2340.nycratsightings.model.GraphInfo;
import cs2340.nycratsightings.model.Sighting;

/**
 * Represents a graphVisualizer object
 */
public class GraphVisualizer extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private final String TAG = "GraphVisualizer";

    private ArrayList<Sighting> mSightings;
    private GraphView mGraph;
    private DatePicker mDateFrom;
    private DatePicker mDateTo;
    private DateRange mDateRange;
    //private Button submit;

    private EditText fromDate;
    private EditText toDate;
    //private Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button update;

        setContentView(R.layout.activity_graph_visualizer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fromDate = findViewById(R.id.editTextFrom);
        toDate = findViewById(R.id.editTextTo);
        update = findViewById(R.id.update);
        mGraph = findViewById(R.id.graph1);

        SplashActivity.mSightingData.syncRatData();

        mSightings = SplashActivity.mSightingData.getRatData();
        Log.d(TAG, "mSightings: " + mSightings);
        mDateRange = new DateRange(mSightings.get(0).parseCreationDate(),
                mSightings.get(mSightings.size() - 1).parseCreationDate());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromString = fromDate.getText().toString();
                String toString = toDate.getText().toString();
                Log.d("dank",fromString);
                String[] fromArray = fromString.split("/");
                String[] toArray = toString.split("/");
                Calendar fromCalendar = new GregorianCalendar(Integer.parseInt(fromArray[2]),
                        Integer.parseInt(fromArray[0]) - 1,Integer.parseInt(fromArray[1]));
                Calendar toCalendar = new GregorianCalendar(Integer.parseInt(toArray[2]),
                        Integer.parseInt(toArray[0]) - 1,Integer.parseInt(toArray[1]));
                updateGraph(fromCalendar,toCalendar);
            }
        });
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

        Button submit;

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
        final int year15 = 2015;
        final int seven = 7;
        final int ten = 10;
        final int three = 3;
        Calendar fromDefault = new GregorianCalendar(year15,seven,three);
        Calendar toDefault = new GregorianCalendar(year15,ten,three);
        LineGraphSeries<DataPoint> graphData;
        mSightings = SplashActivity.mSightingData.getRatData();
        int[] dummy = {0};
        GraphInfo graph = new GraphInfo(fromDefault, toDefault, mSightings);
        graphData = graph.getGraphSeries(dummy);

        mGraph.addSeries(graphData);

        mGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        //mGraph.getGridLabelRenderer().setNumHorizontalLabels(10);

        mGraph.getViewport().setMinX(fromDefault.getTime().getTime());
        mGraph.getViewport().setMaxX(toDefault.getTime().getTime());
        mGraph.getViewport().setXAxisBoundsManual(true);

        mGraph.getGridLabelRenderer().setHumanRounding(false);
    }
    private void updateGraph(Calendar to, Calendar from){
        LineGraphSeries<DataPoint> graphData;
        mSightings = SplashActivity.mSightingData.getRatData();

        int[] dummy = {0};
        GraphInfo graph = new GraphInfo(from, to, mSightings);

        graphData = graph.getGraphSeries(dummy);
        mGraph.addSeries(graphData);

        mGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        //mGraph.getGridLabelRenderer().setNumHorizontalLabels(10);

        mGraph.getViewport().setMinX(from.getTime().getTime());
        mGraph.getViewport().setMaxX(to.getTime().getTime());
        mGraph.getViewport().setXAxisBoundsManual(true);

        mGraph.getGridLabelRenderer().setHumanRounding(false);
    }

    /**
     * signOut
     *  Sign out of Fire base.
     */
    private void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signOut();
        finish();
    }

}
