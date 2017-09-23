package cs2340.nycratsightings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mlogoutText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlogoutText = (TextView) findViewById(R.id.logoutText);
        mlogoutText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.logoutText:
                i = new Intent(this, WelcomeActivity.class);
                this.startActivity(i);
                break;
            default:
                break;
        }

    }
}
