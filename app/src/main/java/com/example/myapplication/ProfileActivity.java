package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {
    User user;
    FloatingActionButton backButton;
    Button lastAttended;
    EditText biography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        backButton = findViewById(R.id.backButtonProfile);
        lastAttended = findViewById(R.id.buttonLastAttendedEvent);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
        lastAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // buraya last attended eventleri listelediğimiz sayfaya yönlendircez
            }
        });

    }
}