package cs2340.nycratsightings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerButton = (Button) findViewById(R.id.register);
        final RadioGroup userType = (RadioGroup) findViewById(R.id.userType);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email, password;

                email = ((EditText) findViewById(R.id.email)).getText().toString();
                password = ((EditText) findViewById(R.id.password)).getText().toString();

                // Get selected user type from registration screen and create corresponding user.
                int selectedUserTypeId = userType.getCheckedRadioButtonId();
                RadioButton selectedRB = (RadioButton) findViewById(selectedUserTypeId);
                String selectedUserType = selectedRB.getText().toString();
                
                if (selectedUserType.equals("Administrator")) {
                    Admin newAdminUser = new Admin(email, password);
                    returnToWelcome(v);
                } else {
                    User newUser = new User(email, password);
                    returnToWelcome(v);
                }
            }
        });
    }

    public void returnToWelcome(View view) {
        startActivity(new Intent(view.getContext(),WelcomeActivity.class));
    }
}
