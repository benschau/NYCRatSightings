package cs2340.nycratsightings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends Activity implements View.OnClickListener{

    private TextView mTitle;
    private TextView mLoginText;
    private TextView mRegisterText;
    private Typeface mTypeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mTypeFace = Typeface.createFromAsset(getAssets(), "font/Trocchi-Regular.ttf");

        mTitle = (TextView) findViewById(R.id.title);
        mLoginText = (TextView) findViewById(R.id.loginText);
        mRegisterText = (TextView) findViewById(R.id.registerText);

        mTitle.setTypeface(mTypeFace);
        mLoginText.setOnClickListener(this);
        mRegisterText.setOnClickListener(this);
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
