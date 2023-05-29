package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fUser = FirebaseAuth.getInstance().getCurrentUser();

<<<<<<< Updated upstream
                Intent intent;
                     intent= (new Intent(SplashActivity.this,LogInActivity.class));

=======
                //Intent intent;
                /*
                if(fUser == null)
                {
                     intent= (new Intent(SplashActivity.this,LogInActivity.class));
                }
                else
                {
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                }
                */
>>>>>>> Stashed changes


                Intent intent = new Intent(SplashActivity.this,LogInActivity.class);
                startActivity(intent);

            }
        },1000);
    }
}