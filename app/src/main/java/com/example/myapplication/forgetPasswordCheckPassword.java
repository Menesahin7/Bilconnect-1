package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class forgetPasswordCheckPassword extends AppCompatActivity {

    protected EditText pass;
    protected EditText confirmPass;
    protected Button changePassButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_check_password);
        pass = findViewById(R.id.newPassword);
        confirmPass = findViewById(R.id.confirmPassword);
        changePassButton = findViewById(R.id.btnChangePassword);
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abi buraya da aynı şekilde iki password birbiriyle uyumlu mu değil mi diye
                // Kontrol edersin uyumluysa user.editpassworddan şifresini değiştirirsin sonra sing
                // in page e yollarsın. Passwordlar boş mu değil mi diye de kontrol ederiz herhalde.
                startActivity(new Intent(forgetPasswordCheckPassword.this,LogInActivity.class));
            }
        });
    }
}