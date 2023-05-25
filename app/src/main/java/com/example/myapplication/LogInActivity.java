package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        editTextEmailLoginPage = findViewById(R.id.editTextTextEmailAddress_loginpage);
        editTextPasswordLoginPage = findViewById(R.id.editTextTextPassword);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        btnLogin = findViewById(R.id.btnLogIn);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void loginUser(){
        String loginEmail = editTextEmailLoginPage.getText().toString();
        String loginPassword = editTextPasswordLoginPage.getText().toString();

        if(TextUtils.isEmpty(loginEmail)) {
            editTextEmailLoginPage.setError("Email cannot be empty.");
            editTextEmailLoginPage.requestFocus();
        }
        else if(TextUtils.isEmpty(loginPassword)) {
            editTextPasswordLoginPage.setError("Password cannot be empty.");
            editTextPasswordLoginPage.requestFocus();
        }
        else {
            mAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this,MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(LogInActivity.this,"Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}