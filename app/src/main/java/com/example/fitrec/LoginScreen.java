package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitrec.model.User;
import com.example.fitrec.network.RetrofitClient;
import com.example.fitrec.network.UserApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    private EditText enterEmail, enterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);

        enterEmail = findViewById(R.id.enterEmail);
        enterPassword = findViewById(R.id.enterPassword);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Go back to MainActivity (home screen)
                Intent i = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(i);
                finish(); // close login screen
                return true;
            } else if (id == R.id.nav_back) {
                // also go back to MainActivity
                Intent i = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            return false;
        });

    }
    public void launchForgotPassword(View v) {
        //launch a new screen
        Intent i3 = new Intent(this, ForgotPasswordScreen.class);
        startActivity(i3);
    }
    public void launchSignUp(View v) {
        //launch a new screen
        Intent i4 = new Intent(this, SignUpScreen.class);
        startActivity(i4);
    }

    public void handleLogin(View v) {
        // CONNECT input fields
        String emailText = enterEmail.getText().toString().trim();
        String passwordText = enterPassword.getText().toString().trim();

        // Empty email
        if (emailText.isEmpty()) {
            enterEmail.setError("Enter email");
            return;
        }

        // Invalid email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            enterEmail.setError("Invalid email");
            return;
        }

        // Empty password
        if (passwordText.isEmpty()) {
            enterPassword.setError("Enter password");
            return;
        }

        // Retrofit call to log in endpoint
        User user = new User();
        user.setEmail(emailText);
        user.setPassword(passwordText);

        UserApi api = RetrofitClient.getRetrofitInstance().create(UserApi.class);
        api.loginUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(LoginScreen.this, "Login successful!", Toast.LENGTH_SHORT).show();

                    // Get logged-in user from backend
                    User loggedInUser = response.body();

                    // Save userId (session)
                    getSharedPreferences("fitrec_prefs", MODE_PRIVATE).edit().putLong("userId", loggedInUser.getId()).apply();

                    // Go to CreateProfiles instead of MainActivity
                    Intent i = new Intent(LoginScreen.this, CreateProfiles.class);

                    // Pass ALL user data (INCLUDING ID)
                    i.putExtra("userId", loggedInUser.getId());
                    i.putExtra("email", loggedInUser.getEmail());
                    i.putExtra("password", loggedInUser.getPassword());
                    i.putExtra("name", loggedInUser.getName());
                    i.putExtra("gender", loggedInUser.getGender());
                    i.putExtra("age", loggedInUser.getAge());
                    i.putExtra("style", loggedInUser.getStylePreferences());
                    i.putExtra("bodyType", loggedInUser.getBodyType());
                    i.putExtra("occasion", loggedInUser.getOccasion());

                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginScreen.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginScreen.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}