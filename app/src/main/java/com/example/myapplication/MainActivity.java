package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

     ArrayList<Event> events;
     RecyclerView recyclerView;
     EventRecyclerAdapter eventRecyclerAdapter;

    FloatingActionButton addEvent,profileMain;
    FirebaseDatabase mFireBaseDataBase;
    DatabaseReference myRef;
    User usr;
    ArrayList<User> userss = new ArrayList<User>();

    FloatingActionButton btnAddEvent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireBaseDataBase = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = mFireBaseDataBase.getReference();

        events = new ArrayList<>();
        Context c = this;

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    System.out.println("5");
                    usr = (User) snapshot.getValue(User.class);
                    try {
                        userss.add(usr);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }

                try {
                    for(int i = 0; i<userss.size(); i++) {
                        if(userss.get(i).getId().equals(FirebaseAuth.getInstance().getUid())) {
                            System.out.println("6");
                            usr = userss.get(i);
                            eventRecyclerAdapter.setUser(usr);
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while retrieving user data
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });

        myRef.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event e = dataSnapshot.getValue(Event.class);
                    if(!e.isFinished() && !e.isFull())
                    {
                        events.add(e);
                    }
                }
                recyclerView = findViewById(R.id.recyclerView);
                eventRecyclerAdapter = new EventRecyclerAdapter(events);
                eventRecyclerAdapter.setUser(usr);
                recyclerView.setAdapter(eventRecyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(c));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profileMain = findViewById(R.id.btnProfile);
        profileMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddingEventActivity.class));
            }
        });
    }
}