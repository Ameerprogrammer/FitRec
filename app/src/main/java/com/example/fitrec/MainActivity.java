package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
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
}