package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CreateProfiles extends AppCompatActivity {

    EditText genderEditText, ageEditText, bodyTypeEditText, styleEditText, occasionEditText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_profiles_screen);

        // link XML views
        genderEditText = findViewById(R.id.enterGender);
        ageEditText = findViewById(R.id.enterAge);
        bodyTypeEditText = findViewById(R.id.enterBodyType);
        styleEditText = findViewById(R.id.enterStyle);
        occasionEditText = findViewById(R.id.enterOccasion);
        saveButton = findViewById(R.id.button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileAndGoToSummary();
            }
        });
    }

    private void saveProfileAndGoToSummary() {
        // grab inputs
        String gender = genderEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String bodyType = bodyTypeEditText.getText().toString().trim();
        String style = styleEditText.getText().toString().trim();
        String occasion = occasionEditText.getText().toString().trim();

        // basic validation
        if (gender.isEmpty() || age.isEmpty() || bodyType.isEmpty() || style.isEmpty() || occasion.isEmpty()) {
            Toast.makeText(this, "Fill out all of the fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        // pass data to summary screen
        Intent summaryIntent = new Intent(CreateProfiles.this, Profile_Summary_Screen.class);
        summaryIntent.putExtra("gender", gender);
        summaryIntent.putExtra("age", age);
        summaryIntent.putExtra("bodyType", bodyType);
        summaryIntent.putExtra("style", style);
        summaryIntent.putExtra("occasion", occasion);
        startActivity(summaryIntent);
    }
}