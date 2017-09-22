package cs2340.nycratsightings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mloginText;
    private TextView mregisterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mloginText = (TextView) findViewById(R.id.loginText);
        mregisterText = (TextView) findViewById(R.id.registerText);

        mloginText.setOnClickListener(this);
        mregisterText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.loginText:
                i = new Intent(this, LoginActivity.class);
                this.startActivity(i);

                break;
            case R.id.registerText:
                i = new Intent(this, RegisterActivity.class);
                this.startActivity(i);

                break;
            default:
                break;
        }

    }
}
