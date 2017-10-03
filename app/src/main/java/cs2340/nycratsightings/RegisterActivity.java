package cs2340.nycratsightings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                Context context;
                String email, password;

                email = ((EditText) findViewById(R.id.email)).getText().toString();
                password = ((EditText) findViewById(R.id.password)).getText().toString();

                // Checks if all fields have been completed.
                int selectedUserTypeId = userType.getCheckedRadioButtonId();
                if (!email.isEmpty() && !password.isEmpty() && selectedUserTypeId != -1) {
                    RadioButton selectedRB = (RadioButton) findViewById(selectedUserTypeId);
                    String selectedUserType = selectedRB.getText().toString();
                    if (selectedUserType.equals("Administrator")) {
                        Admin newAdminUser = new Admin(email, password);
                        returnToWelcome(v);
                    } else {
                        User newUser = new User(email, password);
                        returnToWelcome(v);
                    }
                } else {
                    context = getApplicationContext();
                    CharSequence text = "Fill out all the fields to complete registration";
                    int duration = Toast.LENGTH_SHORT;

                    Toast.makeText(context, text, duration).show();
                }
            }
        });
    }

    public void returnToWelcome(View view) {
        startActivity(new Intent(view.getContext(),WelcomeActivity.class));
    }
}
