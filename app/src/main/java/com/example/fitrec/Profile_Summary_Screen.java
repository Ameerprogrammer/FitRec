package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile_Summary_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_summary_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Grab the data from the intent
        Intent intent = getIntent();
        String gender = intent.getStringExtra("gender");
        int age = intent.getIntExtra("age", 0); // default 0
        String body = intent.getStringExtra("body");
        String style = intent.getStringExtra("style");
        String occasion = intent.getStringExtra("occasion");

        TextView genderView = findViewById(R.id.textView12);
        TextView ageView = findViewById(R.id.textView10);
        TextView bodyView = findViewById(R.id.textView6);
        TextView styleView = findViewById(R.id.textView11);
        TextView occasionView = findViewById(R.id.textView13);

        genderView.setText("Gender: " + gender);
        ageView.setText("Age: " + age);
        bodyView.setText("Body Type: " + body);
        styleView.setText("Style: " + style);
        occasionView.setText("Occasion: " + occasion);


        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Go back to MainActivity (home screen)
                Intent i = new Intent(Profile_Summary_Screen.this, MainActivity.class);
                startActivity(i);
                finish(); // close summary screen
                return true;
            } else if (id == R.id.nav_back) {
                // also go back to create profiles if user wants to edit their stuff
                Intent i = new Intent(Profile_Summary_Screen.this, CreateProfiles.class);
                startActivity(i);
                finish();
                return true;
            }

            return false;
        });

    }
}