package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitrec.model.User;
import com.example.fitrec.network.RetrofitClient;
import com.example.fitrec.network.UserApi;

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

        // Call API on launch to test connection
        fetchUsers();
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