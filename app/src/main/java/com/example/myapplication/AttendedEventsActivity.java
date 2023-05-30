package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AttendedEventsActivity extends AppCompatActivity {

    ArrayList<Event> events;
    RecyclerView recyclerView;
    EventRecyclerAdapter eventRecyclerAdapter;

    FirebaseDatabase mFireBaseDataBase;

    DatabaseReference reference;
    User usr;

    Activity activity = this;
    FloatingActionButton backButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended_events);

        backButton = findViewById(R.id.floatingActionButton3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AttendedEventsActivity.this,ProfileActivity.class));
                finish();
            }
        });

        events = new ArrayList<>();

        mFireBaseDataBase = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app");
        reference = mFireBaseDataBase.getReference();
        String userEmail =ProfileActivity.currentUser.getEmail();
        //System.out.println(userEmail);
        Context c = this;

        reference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    if(userEmail.equals(snapShot.getValue(User.class).getMail()))
                    {
                        usr = snapShot.getValue(User.class);
                        System.out.println("olsun");
                        String str = snapShot.getValue(User.class).attendedEvents;
                        String[] keys = str.split(",");
                        reference.child("events").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snappshot : snapshot.getChildren())
                                {
                                    if(snappshot.getValue(Event.class).getEventId()!=null)
                                    {
                                        for(int i = 0 ; i<keys.length; i++)
                                        {
                                            if(snappshot.getValue(Event.class).getEventId().equals(keys[i]))
                                            {
                                                Event e = snappshot.getValue(Event.class);
                                                events.add(e);
                                            }
                                        }
                                    }
                                }
                                Collections.sort(events, Collections.reverseOrder());
                                recyclerView = findViewById(R.id.recyclerView);
                                eventRecyclerAdapter = new EventRecyclerAdapter(events);
                                eventRecyclerAdapter.setUser(usr);
                                eventRecyclerAdapter.setActivity(activity);
                                recyclerView.setAdapter(eventRecyclerAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(c));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}