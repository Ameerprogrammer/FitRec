package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitrec.model.User;
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

        Intent intent = getIntent();

        // single object flow
        User user = (User) intent.getSerializableExtra("user");

        if (user == null) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // extract values
        String gender = user.getGender();
        int age = user.getAge();
        String body = user.getBodyType();
        String style = user.getStylePreferences();
        String occasion = user.getOccasion();

        // safety guard (prevents "null" showing in UI)
        if (gender == null) gender = "N/A";
        if (body == null) body = "N/A";
        if (style == null) style = "N/A";
        if (occasion == null) occasion = "N/A";

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

                // pass user back
                i.putExtra("user", user);

                startActivity(i);
                finish();
                return true;
            }

            return false;
        });

        Button logoutBtn = findViewById(R.id.logoutBtn);

        Long userId = getSharedPreferences("fitrec_prefs", MODE_PRIVATE)
                .getLong("userId", -1);

        // extra safety (prevents crash if button ever missing in layout)
        if (logoutBtn != null) {
            logoutBtn.setVisibility(userId != -1 ? View.VISIBLE : View.GONE);
        }
    }

    public void handleLogout(View v) {

        getSharedPreferences("fitrec_prefs", MODE_PRIVATE)
                .edit()
                .remove("userId")
                .apply();

        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();

        // clears activity stack so user can’t go back into session
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}