package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ForgotPasswordScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password_screen);

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
}