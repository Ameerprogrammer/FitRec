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

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 7)
            return false;

        // check if it has at least 1 number
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }

    // Add validation here
    public void launchCreateProfiles(View v) {
        // Get user inputs
        String email = enterEmail.getText().toString().trim();
        String password = enterPassword.getText().toString().trim();
        String rePassword = reenterPassword.getText().toString().trim();

        // Clear previous errors
        enterEmail.setError(null);
        enterPassword.setError(null);
        reenterPassword.setError(null);

        boolean hasError = false;

        // Email check
        if (email.isEmpty()) {
            enterEmail.setError("Email is required");
            hasError = true;
        } else if (!isValidEmail(email)) {
            enterEmail.setError("Enter a valid email");
            hasError = true;
        }

        // Password check
        if (password.isEmpty()) {
            enterPassword.setError("Password is required");
            hasError = true;
        } else if (!isValidPassword(password)) {
            enterPassword.setError("Must be 7+ chars & include a number");
            hasError = true;
        }

        // Re-enter password check
        if (rePassword.isEmpty()) {
            reenterPassword.setError("Re-enter your password");
            hasError = true;
        } else if (!password.equals(rePassword)) {
            reenterPassword.setError("Passwords do not match");
            hasError = true;
        }

        // STOP if anything failed
        if (hasError) return;

        // If validation passes, launch the CreateProfiles screen
        Intent i6 = new Intent(this, CreateProfiles.class);

        // Pass email and password to next screen
        i6.putExtra("email", email);
        i6.putExtra("password", password);

        startActivity(i6);
    }
}