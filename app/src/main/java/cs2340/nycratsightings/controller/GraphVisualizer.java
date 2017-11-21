package cs2340.nycratsightings.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cs2340.nycratsightings.R;


/**
 * Represents a graphVisualizer object
 */
public class GraphVisualizer extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "GraphVisualizer";

    private LinkedHashMap<String, Integer> mGraphData;
    private EditText mDateFrom;
    private EditText mDateTo;
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mDateFrom = findViewById(R.id.date_from);
        mDateTo = findViewById(R.id.date_to);
        final Button updateGraph = findViewById(R.id.update_graph);
        updateGraph.setOnClickListener(this);

        // Retrieve sighting data for use in graph.
        SplashActivity.mSightingData.syncRatData();

        // Initialize line chart from xml
        mChart = (LineChart) findViewById(R.id.rat_line_chart);
        mChart.setNoDataText("Select 'From' and 'To' dates to view chart");
        mChart.invalidate();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.update_graph:
                String fromDateString = mDateFrom.getText().toString();
                String toDateString = mDateTo.getText().toString();
                if (fromDateString.isEmpty() || toDateString.isEmpty()) {
                    Toast.makeText(GraphVisualizer.this, R.string.empty_from_to, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        mGraphData = SplashActivity.mSightingData.getLineChartRatData(fromDateString, toDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    /**
                     * Loop through hash map and create an Entry object. The variable 'i' is used to set
                     * the x-axis values for each entry so that each entry is plotted at regular
                     * intervals.
                     * A separate list is created that contains the HashMaps' keys which are used as
                     * labels for the x-axis.
                     * The entries are then added to an entries list which is used to populate the
                     * chart.
                     */
                    List<Entry> entries = new ArrayList<Entry>();
                    final List<String> xAxisLabels = new ArrayList<String>();
                    int i = 0;
                    for (String key : mGraphData.keySet()) {
                        xAxisLabels.add(key);
                        // TODO: DEBUG STATEMENT
                        Log.e("key and value", key + " " + mGraphData.get(key));
                        entries.add(new Entry(i, mGraphData.get(key)));
                        i++;
                    }

                    /**
                     * Create a formatter to set the labels for each data point on the x-axis to its
                     * corresponding date.
                     */
                    IAxisValueFormatter formatter = new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return xAxisLabels.get((int) value);
                        }
                    };
                    XAxis xAxis = mChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f); // Set minimum axis-step interval to 1
                    xAxis.setValueFormatter(formatter);

                    /**
                     * Create data set, add to chart, and style.
                     */
                    LineDataSet dataSet = new LineDataSet(entries, "Rat Frequency");
                    LineData lineData = new LineData(dataSet);
                    dataSet.setDrawFilled(true);
                    mChart.setData(lineData);
                    mChart.animateY(3000);
                    mChart.invalidate();
                    break;
                }
            default:
                break;
        }
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
