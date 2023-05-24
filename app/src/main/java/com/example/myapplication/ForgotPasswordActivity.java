package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button forgetPassButton;
    EditText et_email;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgetPassButton = findViewById(R.id.btnSendCode);
        et_email = findViewById(R.id.EmailAddressForgetPass);

        mAuth = FirebaseAuth.getInstance();

        forgetPassButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                if(TextUtils.isEmpty(email)) {
                    et_email.setError("Email cannot be empty.");
                    et_email.requestFocus();
                }
                else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Email sent, please check your email.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPasswordActivity.this,LogInActivity.class));
                            }
                            else{
                                Toast.makeText(ForgotPasswordActivity.this, "Failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}