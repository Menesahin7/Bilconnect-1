package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserInformationActivity extends AppCompatActivity {
    EditText userName;
    EditText biography;
    RadioButton mainCamp;
    RadioButton eastCamp;
    RadioGroup campusGroup;
    Button createUser;
    FirebaseAuth mAuth;
    FirebaseDatabase mFireBaseDataBase;
    DatabaseReference myRef;
    String email;
    String password;
    String campus;
    User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        userName = findViewById(R.id.et_name);
        biography = findViewById(R.id.et_bio);
        campusGroup = findViewById(R.id.radiogroup_campus);
        mainCamp = findViewById(R.id.mainButton);
        eastCamp = findViewById(R.id.eastButton);
        createUser = findViewById(R.id.button_begin);

        mAuth = FirebaseAuth.getInstance();
        mFireBaseDataBase = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = mFireBaseDataBase.getReference();


        email = getIntent().getStringExtra("signup_email");
        password = getIntent().getStringExtra("signup_password");
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String bio = biography.getText().toString();

                if(mainCamp.isChecked()) {
                    campus = "main";
                }
                else if(eastCamp.isChecked()) {
                    campus = "east";
                }


                if(TextUtils.isEmpty(name)) {
                    userName.setError("Please enter your name.");
                    userName.requestFocus();
                }
                else if(TextUtils.isEmpty(bio)) {
                    biography.setError("Password cannot be empty.");
                    biography.requestFocus();
                }
                else if(!mainCamp.isChecked() && !eastCamp.isChecked()) {
                    Toast.makeText(UserInformationActivity.this, "Please select your campus.", Toast.LENGTH_SHORT).show();
                    mainCamp.requestFocus();
                    eastCamp.requestFocus();
                    campusGroup.requestFocus();
                }
                else {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                if(mAuth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(UserInformationActivity.this, "Signed up successfully.", Toast.LENGTH_SHORT).show();
                                    String uid = mAuth.getUid();
                                    User user = new User(name, email, bio, uid, campus);
                                    myRef.child("users").child(uid).setValue(user);
                                    startActivity(new Intent(UserInformationActivity.this,MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(UserInformationActivity.this,"Sign up error: Please verify your email first.", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(UserInformationActivity.this,"Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}