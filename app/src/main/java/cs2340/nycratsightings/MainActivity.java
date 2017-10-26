package cs2340.nycratsightings;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //welcomeView = (TextView) findViewById(R.id.welcome_text);
        //mTypeFace = Typeface.createFromAsset(getAssets(), "font/Trocchi-Regular.ttf");
        //welcomeView.setTypeface(mTypeFace);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.dashboard:
                break;
        }
    }

    public void signOut() {
        mAuth.signOut();
    }
}
