package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {
    User user;
    FloatingActionButton backButton;
    Button lastAttended;
    TextView nameS,rating;
    EditText biography;

    FirebaseUser currentUser;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_profile);

        String name = currentUser.getUid();
        nameS = findViewById(R.id.textVievNameSurname);
        nameS.setText(name);
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