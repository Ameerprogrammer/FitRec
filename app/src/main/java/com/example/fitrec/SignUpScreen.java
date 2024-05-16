package com.example.fitrec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);
    }
    public void launchCreateProfiles(View v) {
        //launch a new screen
        Intent i6 = new Intent(this, CreateProfiles.class);
        startActivity(i6);
    }
}