package cs2340.nycratsightings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = (Button) findViewById(R.id.login);
        final Button cancelLogin = (Button) findViewById(R.id.cancelLogin);
        final TextView registerHere = (TextView) findViewById(R.id.signup);
        //Creating dummy user here
        final User dummy = new User("user","pass");

        registerHere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                goToRegister(v);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context;
                String email, passwd;

                email = ((EditText) findViewById(R.id.email)).getText().toString();
                passwd = ((EditText) findViewById(R.id.password)).getText().toString();

                if (dummy.validateLogin(email, passwd)) {
                    goToMain(v);
                } else {
                    context = getApplicationContext();
                    CharSequence text = "Incorrect email and password combination!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast.makeText(context, text, duration).show();
                }
            }
        });

        cancelLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                returnToWelcome(v);
            }
        });
    }

    public void returnToWelcome(View view) {
        startActivity(new Intent(view.getContext(),WelcomeActivity.class));
    }

    public void goToMain(View view){
        startActivity(new Intent(view.getContext(),MainActivity.class));
    }

    public void goToRegister(View view){
        startActivity(new Intent(view.getContext(),RegisterActivity.class));
    }

}
