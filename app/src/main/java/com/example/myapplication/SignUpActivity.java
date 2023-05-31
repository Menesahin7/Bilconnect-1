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

public class SignUpActivity extends AppCompatActivity {
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    EditText editTextTextConfPassword;

    Button btnSignUp;
    TextView btnTextViewLogIn;
    FirebaseAuth mAuth;

    EditText password;
    boolean passwordVisible;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextTextConfPassword = findViewById(R.id.editTextTextConfPassword);
        btnTextViewLogIn = findViewById(R.id.textViewLogInbtn);
        btnSignUp = findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();

        //toggle button on password and password confirmation
        password=findViewById(R.id.editTextTextPassword);

        password.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if(event.getAction()==MotionEvent.ACTION_UP){
                if(event.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                    int selection= password.getSelectionEnd();
                    if(passwordVisible){
                        password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordVisible=false;
                    }else{
                        password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_remove_red_eye_24,0);
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordVisible=true;
                    }
                    password.setSelection(selection);
                    return true;
                }
            }

            return false;
        });
        editTextTextConfPassword.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if(event.getAction()==MotionEvent.ACTION_UP){
                if(event.getRawX()>=editTextTextConfPassword.getRight()-editTextTextConfPassword.getCompoundDrawables()[Right].getBounds().width()){
                    int selection= editTextTextConfPassword.getSelectionEnd();
                    if(passwordVisible){
                        editTextTextConfPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                        editTextTextConfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordVisible=false;
                    }else{
                        editTextTextConfPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_remove_red_eye_24,0);
                        editTextTextConfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordVisible=true;
                    }
                    editTextTextConfPassword.setSelection(selection);
                    return true;
                }
            }

            return false;
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SignUpActivity.this,"Signed up",Toast.LENGTH_SHORT).show();
                createUser();
            }
        });

        btnTextViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LogInActivity.class));

            }
        });

    }
    //TODO:ADD NAME TO DATABASE
    private void createUser(){

        String email = editTextTextEmailAddress.getText().toString();
        String password = editTextTextPassword.getText().toString();
        String confPassword = editTextTextConfPassword.getText().toString();


        if(TextUtils.isEmpty(email)) {
            editTextTextEmailAddress.setError("Email cannot be empty.");
            editTextTextEmailAddress.requestFocus();
        } else if (!email.contains("bilkent.edu.tr")) {
            editTextTextEmailAddress.setError("You can only SignUp with a Bilkent mail.");
            editTextTextEmailAddress.requestFocus();
        }
        else if(TextUtils.isEmpty(password)) {
            editTextTextPassword.setError("Password cannot be empty.");
            editTextTextPassword.requestFocus();
        }

        else if(TextUtils.isEmpty(confPassword)) {
            editTextTextConfPassword.setError("Please confirm your password.");
            editTextTextConfPassword.requestFocus();
        }
        else if(!password.equals(confPassword)) {
            editTextTextConfPassword.setError("Confirmed password and the password you entered do not match.");
            editTextTextConfPassword.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this,"Verification email sent. Please verify your email.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this,UserInformationActivity.class);
                                    intent.putExtra("signup_email", email);
                                    intent.putExtra("signup_password", password);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this,"Verification Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(SignUpActivity.this,"SignUp Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}