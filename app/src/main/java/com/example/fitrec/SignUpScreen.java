package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignUpScreen extends AppCompatActivity {

    // Add these fields to access your XML inputs
    private EditText enterEmail, enterPassword, reenterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);

        // Link XML views to Java variables
        enterEmail = findViewById(R.id.enterEmail);
        enterPassword = findViewById(R.id.enterPassword);
        reenterPassword = findViewById(R.id.ReenterPassword);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Go back to MainActivity (home screen)
                Intent i = new Intent(SignUpScreen.this, MainActivity.class);
                startActivity(i);
                finish(); // close sign up screen
                return true;
            } else if (id == R.id.nav_back) {
                // also go back to MainActivity
                Intent i = new Intent(SignUpScreen.this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            return false;
        });
    }

    // Add validation here
    public void launchCreateProfiles(View v) {
        // Get user inputs
        String email = enterEmail.getText().toString().trim();
        String password = enterPassword.getText().toString().trim();
        String rePassword = reenterPassword.getText().toString().trim();

        // Password match check
        if (!password.equals(rePassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Bestie, you have to fill in all of the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // If validation passes, launch the CreateProfiles screen
        Intent i6 = new Intent(this, CreateProfiles.class);

        // Pass email and password to next screen
        i6.putExtra("email", email);
        i6.putExtra("password", password);

        startActivity(i6);
    }
}