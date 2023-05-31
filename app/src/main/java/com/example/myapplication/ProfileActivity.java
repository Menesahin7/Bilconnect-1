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
import java.text.DecimalFormat;
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
    FloatingActionButton backButton, floatingActionButtonUsers;
    Button lastAttended,logoutButon;
    TextView nameS,rating, editProfilebtn;
    EditText biography;

    public static FirebaseUser currentUser;
    FirebaseDatabase db;
    DatabaseReference myRef;
    User usr;
    private DatabaseReference mDatabase;

    TextView textViewNameAndSurname,campusText;

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
        logoutButon = findViewById(R.id.button2);
        campusText = findViewById(R.id.campusText);
        logoutButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,LogInActivity.class));
            }
        });
        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    if(snapshot.getValue(User.class).mail.equals(currentUser.getEmail()))
                    {
                        usr = snapshot.getValue(User.class);
                        campusText.setText(usr.getCampus());
                        nameS.setText(usr.getName());
                        biography.setText(usr.getBio());
                        DecimalFormat df = new DecimalFormat("##.##");
                        double getrtg = usr.getRating();
                        rating.setText(df.format(getrtg));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while retrieving user data
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });

        floatingActionButtonUsers = findViewById(R.id.floatingActionButtonUsers);
        floatingActionButtonUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UserInfoPage.class));
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