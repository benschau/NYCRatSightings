package cs2340.nycratsightings;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private Typeface mTypeFace;
    private Button logoutButton;
    private TextView welcomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeView = (TextView) findViewById(R.id.welcome_text);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        mTypeFace = Typeface.createFromAsset(getAssets(), "font/Trocchi-Regular.ttf");

        welcomeView.setTypeface(mTypeFace);
        logoutButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.logoutButton:
                signOut();
                finish();
                break;
            default:
                break;
        }
    }

    public void signOut() {
        mAuth.signOut();
    }
}
