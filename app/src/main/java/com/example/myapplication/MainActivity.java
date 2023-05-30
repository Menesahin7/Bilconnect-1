package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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