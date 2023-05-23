package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    EditText editTextEmailLoginPage;
    EditText editTextPasswordLoginPage;
    TextView textViewForgotPassword;
    TextView textViewSignUp;
    Button btnLogin;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextEmailLoginPage = findViewById(R.id.editTextTextEmailAddress);
        editTextPasswordLoginPage = findViewById(R.id.editTextTextEmailAddress_loginpage);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        btnLogin = findViewById(R.id.btnLogIn);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LogInActivity.this,MainActivity.class));
                loginUser();
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,ForgotPasswordActivity.class));
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
            }
        });

    }

    private void loginUser() {

    }
}