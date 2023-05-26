package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EvaluateParticipantActivity extends AppCompatActivity {
    RatingBar eval;
    Button evaluateUser;
    ListView listView;
    float rateValue;
    ArrayList<String> users = new ArrayList<>();
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_participant);

        eval = findViewById(R.id.ratingBarStars);
        evaluateUser = findViewById(R.id.evaluateButton);
        listView = findViewById(R.id.listViewParticipants);
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(EvaluateParticipantActivity.this, android.R.layout.activity_list_item,users);

        mRef = FirebaseDatabase.getInstance().getReference();
        evaluateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();
                startActivity(new Intent(EvaluateParticipantActivity.this,MainActivity.class));
            }
        });
        eval.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue = ratingBar.getRating();
                //  User ratingi artıcak firebaseden current userı alıp ona göre userdaki metodu kullanacaz

            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String name = snapshot.getValue(String.class);
                users.add(name);
                arrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void evaluate()
    {
        // this method will evaluate the current users rating.
    }
}