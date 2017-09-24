package cs2340.nycratsightings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Creating dummy user here
        final  user dummy = new user("lmao","dank");

        final Button login = (Button) findViewById(R.id.login);
        final Button welcomeReturnButton = (Button) findViewById(R.id.returnToWelcomeButton);
        final TextView registerHere = (TextView) findViewById(R.id.signup);
        registerHere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                goToRegister(v);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emaildata = ((EditText)findViewById(R.id.email)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                if(dummy.validateLogin(emaildata,password)){
                    goToMain(v);
                }
            }
        });
        welcomeReturnButton.setOnClickListener(new Button.OnClickListener() {
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
    //User object
    class user{
        private String username;
        private String password;
        public user(String userNameIn, String passwordIn){
            username = userNameIn;
            password = passwordIn;
        }
        public boolean validateLogin(String nameTest, String passwordTest){
            if(username.equals(nameTest) && password.equals(passwordTest)){
                return true;
            } else{
                return false;
            }
        }
    }
}
