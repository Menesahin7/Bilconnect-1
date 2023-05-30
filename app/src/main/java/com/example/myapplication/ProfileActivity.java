package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class ProfileActivity extends AppCompatActivity {
    User user;
    FloatingActionButton backButton;
    Button lastAttended;
    TextView nameS,rating, editProfilebtn;
    EditText biography;

    public static FirebaseUser currentUser;
    FirebaseDatabase db;
    DatabaseReference myRef;
    User usr;
    private DatabaseReference mDatabase;





    TextView textViewNameAndSurname;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = db.getReference();

        nameS = findViewById(R.id.textVievNameSurname);
        biography = findViewById(R.id.editTextBiography);
        rating = findViewById(R.id.textViewRating);

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    if(snapshot.getValue(User.class).mail.equals(currentUser.getEmail()))
                    {
                        usr = snapshot.getValue(User.class);
                        nameS.setText(usr.getName());
                        biography.setText(usr.getBio());
                        rating.setText(""+usr.getRating());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while retrieving user data
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });

        backButton = findViewById(R.id.backButtonProfile);
        lastAttended = findViewById(R.id.buttonLastAttendedEvent);
        textViewNameAndSurname = findViewById(R.id.textVievNameSurname);
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
                startActivity(new Intent(ProfileActivity.this,AttendedEventsActivity.class));
            }
        });

        editProfilebtn = findViewById(R.id.textViewEditProfile);
        editProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
            }
        });

    }
}