package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignUpScreen extends AppCompatActivity {

    private EditText enterEmail, enterPassword, reenterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);

        enterEmail = findViewById(R.id.enterEmail);
        enterPassword = findViewById(R.id.enterPassword);
        reenterPassword = findViewById(R.id.ReenterPassword);

        //nav bar
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home || id == R.id.nav_back) {
                startActivity(new Intent(SignUpScreen.this, MainActivity.class));
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
        if (password.length() < 7) return false;
        for (char c : password.toCharArray()) if (Character.isDigit(c)) return true;
        return false;
    }

    public void launchCreateProfiles(android.view.View v) {

        String email = enterEmail.getText().toString().trim();
        String password = enterPassword.getText().toString().trim();
        String rePassword = reenterPassword.getText().toString().trim();

        // Clear errors
        enterEmail.setError(null);
        enterPassword.setError(null);
        reenterPassword.setError(null);

        boolean hasError = false;

        // Validation
        if (email.isEmpty()) {
            enterEmail.setError("Email required");
            hasError = true;
        } else if (!isValidEmail(email)) {
            enterEmail.setError("Enter a valid email");
            hasError = true;
        }

        if (password.isEmpty()) {
            enterPassword.setError("Password required");
            hasError = true;
        } else if (!isValidPassword(password)) {
            enterPassword.setError("7+ chars & include a number");
            hasError = true;
        }

        if (rePassword.isEmpty()) {
            reenterPassword.setError("Re-enter your password");
            hasError = true;
        } else if (!password.equals(rePassword)) {
            reenterPassword.setError("Passwords do not match");
            hasError = true;
        }

        if (hasError) return;

        // PASS DATA TO NEXT SCREEN
        Intent intent = new Intent(SignUpScreen.this, CreateProfiles.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}