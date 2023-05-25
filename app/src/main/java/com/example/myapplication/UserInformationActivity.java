package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserInformationActivity extends AppCompatActivity {
    EditText userName;
    EditText biography;
    RadioButton mainCamp;
    RadioButton eastCamp;
    Button createUser;
    FirebaseAuth mAuth;
    FirebaseDatabase mFireBaseDataBase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        userName = findViewById(R.id.et_name);
        biography = findViewById(R.id.et_bio);
        mainCamp = findViewById(R.id.mainButton);
        eastCamp = findViewById(R.id.eastButton);
        createUser = findViewById(R.id.button_begin);

        mAuth = FirebaseAuth.getInstance();
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        myRef = mFireBaseDataBase.getReference();
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String bio = biography.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    userName.setError("Please enter your name.");
                    userName.requestFocus();
                }
                else if(TextUtils.isEmpty(bio)) {
                    biography.setError("Password cannot be empty.");
                    biography.requestFocus();
                }
                else {
                    String uid = mAuth.getUid();
                    User user = new User(name,bio,uid);
                    myRef.child("users").setValue(user);
                }
                startActivity(new Intent(UserInformationActivity.this,MainActivity.class));
            }
        });
    }
}