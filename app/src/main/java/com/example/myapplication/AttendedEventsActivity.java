package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class AttendedEventsActivity extends AppCompatActivity {

    ArrayList<Event> events;
    RecyclerView recyclerView;
    EventRecyclerAdapter eventRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended_events);
        events = new ArrayList<>();
        events.add(new Event("asd",3,"asd","asd","asd","asd","asd","asd","as"));
        events.add(new Event("asd",3,"asd","asd","asd","asd","asd","asd","as"));
        events.add(new Event("asd",3,"asd","asd","asd","asd","asd","asd","as"));

        Context c = this;
        recyclerView = findViewById(R.id.recyclerView);
        eventRecyclerAdapter = new EventRecyclerAdapter(events);
        recyclerView.setAdapter(eventRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
    }
}