package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ForgotPasswordScreen extends AppCompatActivity {

    private EditText enterEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password_screen);

        enterEmail = findViewById(R.id.enterEmail);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Go back to MainActivity (home screen)
                Intent i = new Intent(ForgotPasswordScreen.this, MainActivity.class);
                startActivity(i);
                finish(); // close forgot password screen
                return true;
            } else if (id == R.id.nav_back) {
                // go back to login screen
                Intent i = new Intent(ForgotPasswordScreen.this, LoginScreen.class);
                startActivity(i);
                finish();
                return true;
            }

            return false;
        });
    }

    public void launchResetPassword(View v) {
        String emailText = enterEmail.getText().toString().trim();

        // tests
        if (emailText.isEmpty()) {
            enterEmail.setError("Enter your email");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            enterEmail.setError("Invalid email");
            return;
        }

        // remove any whitespace
        emailText = emailText.replaceAll("\\s+", "");

        // If input is valid → show confirmation toast (or navigate)
        Toast.makeText(this, "Reset link sent to: " + emailText, Toast.LENGTH_SHORT).show();

        Intent i2 = new Intent(this, LoginScreen.class);
        startActivity(i2);
    }

}