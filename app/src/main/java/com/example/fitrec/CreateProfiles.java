package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

//IMPORTS FOR RETROFIT + USER MODEL
import com.example.fitrec.model.User;
import com.example.fitrec.network.ApiService;
import com.example.fitrec.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                // calls the function below
                saveProfileAndGoToSummary();
            }
        });
    }

    private void saveProfileAndGoToSummary() {

        // RECEIVE DATA FROM PREVIOUS SCREEN
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        // grab inputs from this screen
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

        // CONVERT AGE STRING → INT
        int ageInt = Integer.parseInt(age);

        // CREATE USER OBJECT
        User user = new User(
                email,      // username
                password,
                gender,
                ageInt,
                style,
                email,
                email       // using email as name placeholder for now
        );

        // CREATE API SERVICE
        ApiService apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        // SEND USER TO SPRING BOOT BACKEND
        apiService.createUser(user).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(CreateProfiles.this, "User created!", Toast.LENGTH_SHORT).show();

                    // go to summary screen after successful API call
                    Intent summaryIntent = new Intent(CreateProfiles.this, Profile_Summary_Screen.class);
                    summaryIntent.putExtra("gender", gender);
                    summaryIntent.putExtra("age", age);
                    summaryIntent.putExtra("bodyType", bodyType);
                    summaryIntent.putExtra("style", style);
                    summaryIntent.putExtra("occasion", occasion);

                    startActivity(summaryIntent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(CreateProfiles.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}