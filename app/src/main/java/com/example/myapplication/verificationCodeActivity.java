package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class verificationCodeActivity extends AppCompatActivity {
    protected Button  nextButton;
    protected Button  resendButton;
    protected FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        nextButton = findViewById(R.id.buttonNext);
        resendButton = findViewById(R.id.buttonResend);

        mAuth = FirebaseAuth.getInstance();
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(verificationCodeActivity.this,"Email resent successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(verificationCodeActivity.this,LogInActivity.class));
                        }
                        else {
                            Toast.makeText(verificationCodeActivity.this,"SignUp Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isEmailVerified = mAuth.getCurrentUser().isEmailVerified();
                if (isEmailVerified) {
                    // User has verified their email
                    Toast.makeText(verificationCodeActivity.this, "Email verified", Toast.LENGTH_SHORT).show();
                    //isim falan yazma sayfasına geçecek...
                } else {
                    // User has not verified their email
                    Toast.makeText(verificationCodeActivity.this, "You have not verified your email yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}