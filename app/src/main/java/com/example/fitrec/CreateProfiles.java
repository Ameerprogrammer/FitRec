package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CreateProfiles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_profiles_screen);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Go back to MainActivity (home screen)
                Intent i = new Intent(CreateProfiles.this, MainActivity.class);
                startActivity(i);
                finish(); // close create profiles screen
                return true;
            } else if (id == R.id.nav_back) {
                // Block back button, show toast message instead
                Toast.makeText(CreateProfiles.this, "Fill out the fields please", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });

           }

    public void handleComplete(View v) {

        EditText age = findViewById(R.id.enterAge);
        EditText gender = findViewById(R.id.enterGender);
        EditText style = findViewById(R.id.enterStyle);
        EditText bodyType = findViewById(R.id.enterBodyType);
        EditText occasion = findViewById(R.id.enterOccasion);

        String ageText = age.getText().toString().trim();
        String genderText = gender.getText().toString().trim();
        String styleText = style.getText().toString().trim();
        String bodyTypeText = bodyType.getText().toString().trim();
        String occasionText = occasion.getText().toString().trim();

        // Check if ANY field is empty
        if (ageText.isEmpty() || genderText.isEmpty() || styleText.isEmpty()
                || bodyTypeText.isEmpty() || occasionText.isEmpty()) {

            Toast.makeText(this, "Fill out all fields please!", Toast.LENGTH_SHORT).show();
            return;
        }

        // All fields filled → go to next screen
        Intent i = new Intent(CreateProfiles.this, Profile_Summary_Screen.class);
        startActivity(i);
    }
}