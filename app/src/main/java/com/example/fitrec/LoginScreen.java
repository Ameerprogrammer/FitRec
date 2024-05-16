package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);

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

    public void launchCreateProfiles(View v) {
        //launch a new screen
        Intent i7 = new Intent(this, CreateProfiles.class);
        startActivity(i7);
    }
}