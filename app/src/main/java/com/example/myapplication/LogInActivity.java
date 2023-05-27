package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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

    boolean passwordVisible;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
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

        //toggle button on login password
        editTextPasswordLoginPage.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if(event.getAction()== MotionEvent.ACTION_UP){
                if(event.getRawX()>=editTextPasswordLoginPage.getRight()-editTextPasswordLoginPage.getCompoundDrawables()[Right].getBounds().width()){
                    int selection= editTextPasswordLoginPage.getSelectionEnd();
                    if(passwordVisible){
                        editTextPasswordLoginPage.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                        editTextPasswordLoginPage.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordVisible=false;

                    }else{
                        editTextPasswordLoginPage.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_remove_red_eye_24,0);
                        editTextPasswordLoginPage.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordVisible=true;

                    }

                    editTextPasswordLoginPage.setSelection(selection);
                    return true;
                }
            }

            return false;
        });

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
                        if(mAuth.getCurrentUser().isEmailVerified()) {
                            Toast.makeText(LogInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LogInActivity.this,MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(LogInActivity.this, "You have not verified your mail yet. Please verify your Bilkent mail.", Toast.LENGTH_SHORT).show();
                            mAuth.getCurrentUser().sendEmailVerification();
                        }

                    }
                    else {
                        Toast.makeText(LogInActivity.this,"Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}