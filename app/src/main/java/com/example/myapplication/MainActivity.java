package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        fillArray();
        viewSettings();

        mFireBaseDataBase = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = mFireBaseDataBase.getReference();



        profileMain = findViewById(R.id.btnProfile);
        profileMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EvaluateParticipantActivity.class));
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

      public void viewSettings(){
        recyclerView = findViewById(R.id.recyclerView);
        eventRecyclerAdapter = new EventRecyclerAdapter(events);
        recyclerView.setAdapter(eventRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

     public void fillArray(){
         events = new ArrayList<>();
        events.add(new Event("voleybol",2,"asd","asdas","12/12/12","23:12","enes","main","dfasd"));
        events.add(new Event("futbol",2,"asd","asdas","12/12/12","23:12","enes","main","dfasd"));
        events.add(new Event("hentbol",2,"asd","asdas","12/12/12","23:12","enes","main","dfasd"));
        events.add(new Event("basketbol",2,"asd","asdas","12/12/12","23:12","enes","main","dfasd"));

    }

}