package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitrec.model.User;
import com.example.fitrec.network.RetrofitClient;
import com.example.fitrec.network.UserApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // prevents auto-routing loop when coming back from other screens
        boolean skipAutoRoute = getIntent().getBooleanExtra("skipAutoRoute", false);

        Long userId = getSharedPreferences("fitrec_prefs", MODE_PRIVATE)
                .getLong("userId", -1);

        // Call API on launch to test connection
        fetchUsers();

        // auto-login / routing logic (core session flow)
        if (userId != -1 && !skipAutoRoute) {

            UserApi api = RetrofitClient.getRetrofitInstance().create(UserApi.class);

            api.getUserById(userId).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    // handle failure/null safely
                    if (!response.isSuccessful() || response.body() == null) {

                        getSharedPreferences("fitrec_prefs", MODE_PRIVATE)
                                .edit()
                                .remove("userId")
                                .apply();

                        Toast.makeText(MainActivity.this, "Session expired", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User user = response.body();

                    if (user.getName() != null && !user.getName().isEmpty()) {

                        // profile complete → go summary screen
                        Intent i = new Intent(MainActivity.this, Profile_Summary_Screen.class);
                        i.putExtra("user", user);
                        startActivity(i);

                    } else {

                        // profile incomplete → go create profile
                        Intent i = new Intent(MainActivity.this, CreateProfiles.class);

                        i.putExtra("userId", user.getId());
                        i.putExtra("email", user.getEmail());
                        i.putExtra("password", user.getPassword());

                        startActivity(i);
                    }

                    finish(); // stops back navigation loop
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            });

            return; // prevents rest of UI logic from running when logged in
        }

        //navigation bar ----------------------------
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Show a toast instead of recreating the activity
                Toast.makeText(MainActivity.this, "Bestie, you are already at the home screen", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_back) {
                finish(); // go back
                return true;
            }

            return false;
        });
        //nav bar ---------------------------------

    }

    // logout logic (clears session + resets app state)
    public void handleLogout() {

        getSharedPreferences("fitrec_prefs", MODE_PRIVATE)
                .edit()
                .remove("userId")
                .apply();

        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void launchSignUp(View v) {
        //launch a new screen
        Intent i = new Intent(this, SignUpScreen.class);
        startActivity(i);
    }

    public void launchLogin(View v) {
        //launch a new screen
        Intent i2 = new Intent(this, LoginScreen.class);
        startActivity(i2);
    }

    private void fetchUsers() {
        UserApi userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);
        Call<List<User>> call = userApi.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    for (User user : users) {
                        Log.d(TAG, "User: " + user.getName()); // assuming User has getName()
                    }
                } else {
                    Log.e(TAG, "API Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }
}