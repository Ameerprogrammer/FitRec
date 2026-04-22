package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitrec.model.User;
import com.example.fitrec.network.RetrofitClient;
import com.example.fitrec.network.UserApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProfiles extends AppCompatActivity {

    private EditText enterName;

    // moved spinners to class level
    private Spinner age, gender, style, bodyType, occasion;

    // email + password from previous screen
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_profiles_screen);

        enterName = findViewById(R.id.enterName);

        // bind spinners once here
        age = findViewById(R.id.spinnerAge);
        gender = findViewById(R.id.spinnerGender);
        style = findViewById(R.id.spinnerStyle);
        bodyType = findViewById(R.id.spinnerBodyType);
        occasion = findViewById(R.id.spinnerOccasion);

        // GET DATA FROM SIGNUP SCREEN
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        //null safety check
        if (email == null || password == null) {
            Toast.makeText(this, "Something went wrong. Please sign up again.", Toast.LENGTH_SHORT).show();
            finish();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                Intent i = new Intent(CreateProfiles.this, MainActivity.class);

                User existingUser = (User) getIntent().getSerializableExtra("user");

                if (existingUser != null) {
                    // restore email/password so it doesn't crash
                    email = existingUser.getEmail();
                    password = existingUser.getPassword();

                    // (optional later: prefill UI fields)
                }

                startActivity(i);
                finish();
                return true;
            } else if (id == R.id.nav_back) {
                Toast.makeText(CreateProfiles.this, "Fill out the fields please", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });

        // ----------------- GENDER -----------------
        String[] genders = {"Gender?", "Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, genders);
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        gender.setAdapter(genderAdapter);
        gender.setSelection(0);

        // ----------------- BODY TYPE -----------------
        String[] bodies = {"Body Type?", "Slim", "Plus Size"};
        ArrayAdapter<String> bodyAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, bodies);
        bodyAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        bodyType.setAdapter(bodyAdapter);
        bodyType.setSelection(0);

        // ----------------- STYLE -----------------
        String[] styles = {"Style?", "Goth", "Kawaii"};
        ArrayAdapter<String> styleAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, styles);
        styleAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        style.setAdapter(styleAdapter);
        style.setSelection(0);

        // ----------------- OCCASION -----------------
        String[] occasions = {"Occasion?", "Dance", "Party"};
        ArrayAdapter<String> occasionAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, occasions);
        occasionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        occasion.setAdapter(occasionAdapter);
        occasion.setSelection(0);

        // ----------------- AGE -----------------
        List<String> ageList = new ArrayList<>();
        ageList.add("Age?");

        for (int i = 14; i <= 50; i++) {
            ageList.add(String.valueOf(i));
        }

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ageList
        ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };

        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        age.setAdapter(ageAdapter);
        age.setSelection(0);
    }

    public void handleComplete(View v) {

        // prevent spam clicks
        v.setEnabled(false);

        String nameValue = enterName.getText().toString().trim();

        String genderValue = gender.getSelectedItem().toString().trim();
        String bodyValue = bodyType.getSelectedItem().toString().trim();
        String styleValue = style.getSelectedItem().toString().trim();
        String occasionValue = occasion.getSelectedItem().toString().trim();
        String ageValueStr = age.getSelectedItem().toString().trim();

        if (nameValue.isEmpty()) {
            enterName.setError("Enter your name");
            v.setEnabled(true); // re-enable
            return;
        }

        if (genderValue.equals("Gender?")) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            v.setEnabled(true);
            return;
        }
        if (bodyValue.equals("Body Type?")) {
            Toast.makeText(this, "Please select a body type", Toast.LENGTH_SHORT).show();
            v.setEnabled(true);
            return;
        }
        if (styleValue.equals("Style?")) {
            Toast.makeText(this, "Please select a style", Toast.LENGTH_SHORT).show();
            v.setEnabled(true);
            return;
        }
        if (occasionValue.equals("Occasion?")) {
            Toast.makeText(this, "Please select an occasion", Toast.LENGTH_SHORT).show();
            v.setEnabled(true);
            return;
        }
        if (ageValueStr.equals("Age?")) {
            Toast.makeText(this, "Please select your age", Toast.LENGTH_SHORT).show();
            v.setEnabled(true);
            return;
        }

        int ageValue = Integer.parseInt(ageValueStr);

        if (ageValue < 14 || ageValue > 50) {
            Toast.makeText(this, "Age must be between 14 and 50", Toast.LENGTH_SHORT).show();
            v.setEnabled(true);
            return;
        }

        User user = new User(
                email,
                password,
                nameValue,
                genderValue,
                ageValue,
                styleValue,
                bodyValue,
                occasionValue
        );

        UserApi api = RetrofitClient.getRetrofitInstance().create(UserApi.class);

        api.createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                v.setEnabled(true); // re-enable button

                if (response.isSuccessful() && response.body() != null) {

                    User createdUser = response.body();

                    Intent i = new Intent(CreateProfiles.this, Profile_Summary_Screen.class);

                    i.putExtra("user", createdUser);

                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(CreateProfiles.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                v.setEnabled(true); // re-enable button
                Toast.makeText(CreateProfiles.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}