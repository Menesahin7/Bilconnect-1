package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    FirebaseUser fUser;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://"+getPackageName()+"/"+ R.raw.video2;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fUser = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent;

                intent= (new Intent(SplashActivity.this,LogInActivity.class));

                startActivity(intent);
            }
        },2200);
    }
}
