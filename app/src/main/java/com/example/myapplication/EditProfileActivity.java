package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {

    private FloatingActionButton backButton;
    private EditText nameSurname;
    private EditText biography;
    private Button saveButton;
    private Button changePassword;
    private FirebaseUser currentUser;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private User usr;
    private ArrayList<User> userss = new ArrayList<User>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameSurname = findViewById(R.id.editText4);
        biography = findViewById(R.id.editTextBiography1111);
        saveButton = findViewById(R.id.buttonSave);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = db.getReference();

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    usr = (User) snapshot.getValue(User.class);
                    try {
                        userss.add(usr);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }

                try {
                    for(int i = 0; i<userss.size(); i++) {
                        if(userss.get(i).getId().equals(FirebaseAuth.getInstance().getUid())) {
                            usr = userss.get(i);
                            biography.setText(usr.getBio());
                            biography.requestFocus();
                            nameSurname.setText(usr.getName());
                            nameSurname.requestFocus();
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while retrieving user data
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });



        backButton = findViewById(R.id.backButtonProfile1111);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this,ProfileActivity.class));
            }
        });

        changePassword = findViewById(R.id.ChangePasswordButton);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(EditProfileActivity.this, "Email sent, please check your email.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(EditProfileActivity.this, "Failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameSurname.getText().toString();
                String bio = biography.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    nameSurname.setError("Please enter your name.");
                    nameSurname.requestFocus();
                }
                else if(TextUtils.isEmpty(bio)) {
                    biography.setError("Password cannot be empty.");
                    biography.requestFocus();
                }
                else {
                    for(int i = 0; i<userss.size(); i++) {
                        if(userss.get(i).getId().equals(FirebaseAuth.getInstance().getUid())) {
                            usr = userss.get(i);
                            biography.setText(usr.getBio());
                            nameSurname.setText(usr.getName());
                        }
                    }

                    usr.setBio(bio);
                    usr.setName(name);

                    myRef.child("users").child(currentUser.getUid()).setValue(usr);
                    startActivity(new Intent(EditProfileActivity.this,ProfileActivity.class));
                }
            }
        });
    }



}