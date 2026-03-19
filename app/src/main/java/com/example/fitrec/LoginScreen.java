package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Go back to MainActivity (home screen)
                Intent i = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(i);
                finish(); // close login screen
                return true;
            } else if (id == R.id.nav_back) {
                // also go back to MainActivity
                Intent i = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            return false;
        });

    }
    public void launchForgotPassword(View v) {
        //launch a new screen
        Intent i3 = new Intent(this, ForgotPasswordScreen.class);
        startActivity(i3);
    }
    public void launchSignUp(View v) {
        //launch a new screen
        Intent i4 = new Intent(this, SignUpScreen.class);
        startActivity(i4);
    }

    public void launchCreateProfiles(View v) {

        android.widget.EditText email = findViewById(R.id.enterEmail);
        android.widget.EditText password = findViewById(R.id.enterPassword);

        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        // Empty email
        if (emailText.isEmpty()) {
            email.setError("Enter email");
            return;
        }

        // Invalid email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Invalid email");
            return;
        }

        // Empty password
        if (passwordText.isEmpty()) {
            password.setError("Enter password");
            return;
        }

        // If everything valid → go forward
        Intent i7 = new Intent(this, CreateProfiles.class);
        startActivity(i7);
    }
}