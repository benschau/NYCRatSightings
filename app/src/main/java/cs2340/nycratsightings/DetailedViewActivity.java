package cs2340.nycratsightings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Alex
 */

public class DetailedViewActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedview);

        final Button returnToList = findViewById(R.id.returnToList);

        returnToList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.returnToList:
                finish();
                break;
            default:
                break;
        }
    }

}
