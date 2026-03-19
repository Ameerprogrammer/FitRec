package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

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

        Spinner genderSpinner = findViewById(R.id.spinnerGender);
        Spinner bodySpinner = findViewById(R.id.spinnerBodyType);
        Spinner styleSpinner = findViewById(R.id.spinnerStyle);
        Spinner occasionSpinner = findViewById(R.id.spinnerOccasion);
        Spinner ageSpinner = findViewById(R.id.spinnerAge);

        // ----------------- GENDER -----------------
        String[] genders = {"Gender?", "Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, genders);
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setSelection(0); // show hint initially

        // ----------------- BODY TYPE -----------------
        String[] bodies = {"Body Type?", "Slim", "Plus Size"};
        ArrayAdapter<String> bodyAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, bodies);
        bodyAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        bodySpinner.setAdapter(bodyAdapter);
        bodySpinner.setSelection(0); // show hint initially

        // ----------------- STYLE -----------------
        String[] styles = {"Style?", "Goth", "Kawaii"};
        ArrayAdapter<String> styleAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, styles);
        styleAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);
        styleSpinner.setSelection(0); // show hint initially

        // ----------------- OCCASION -----------------
        String[] occasions = {"Occasion?", "Dance", "Party"};
        ArrayAdapter<String> occasionAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, occasions);
        occasionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        occasionSpinner.setAdapter(occasionAdapter);
        occasionSpinner.setSelection(0); // show hint initially
        occasionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // ----------------- AGE -----------------
        List<String> ageList = new ArrayList<>();
        ageList.add("Age?"); // hint

        for (int i = 14; i <= 50; i++) {
            ageList.add(String.valueOf(i));
        }

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ageList
        ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // disables "Age?"
            }
        };
        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setSelection(0);

           }

    public void handleComplete(View v) {

        Spinner age = findViewById(R.id.spinnerAge);
        Spinner gender = findViewById(R.id.spinnerGender);
        Spinner style = findViewById(R.id.spinnerStyle);
        Spinner bodyType = findViewById(R.id.spinnerBodyType);
        Spinner occasion = findViewById(R.id.spinnerOccasion);

        // Get selected values from spinners
        String genderValue = gender.getSelectedItem().toString().trim();
        String bodyValue = bodyType.getSelectedItem().toString().trim();
        String styleValue = style.getSelectedItem().toString().trim();
        String occasionValue = occasion.getSelectedItem().toString().trim();
        String ageValueStr = age.getSelectedItem().toString().trim();

        // HINT VALIDATION
        if (genderValue.equals("Gender?")) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bodyValue.equals("Body Type?")) {
            Toast.makeText(this, "Please select a body type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (styleValue.equals("Style?")) {
            Toast.makeText(this, "Please select a style", Toast.LENGTH_SHORT).show();
            return;
        }
        if (occasionValue.equals("Occasion?")) {
            Toast.makeText(this, "Please select an occasion", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ageValueStr.equals("Age?")) {
            Toast.makeText(this, "Please select your age", Toast.LENGTH_SHORT).show();
            return;
        }

        int ageValue = Integer.parseInt(ageValueStr);

        // ----- AGE RANGE CHECK -----
        if (ageValue < 14 || ageValue > 50) {
            Toast.makeText(this, "Age must be between 14 and 50", Toast.LENGTH_SHORT).show();
            return;
        }

        // Move forward
        Intent i = new Intent(CreateProfiles.this, Profile_Summary_Screen.class);

        i.putExtra("age", ageValue);
        i.putExtra("gender", genderValue);
        i.putExtra("style", styleValue);
        i.putExtra("body", bodyValue);
        i.putExtra("occasion", occasionValue);

        startActivity(i);
    }
}