package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class verificationCode extends AppCompatActivity {
    protected EditText verif;
    protected Button  verifButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        verif = findViewById(R.id.verificationCode);
        verifButton = findViewById(R.id.ButtonVerify);
        verifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Buraya aslında verification kodu ile diğeri doğru mu değil mi diye kontrol
                // edersiniz abi sonra aciticty başlar
                startActivity(new Intent(verificationCode.this,forgetPasswordCheckPassword.class));
            }
        });
    }
}