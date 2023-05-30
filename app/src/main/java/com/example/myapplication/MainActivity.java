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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity {

     ArrayList<Event> events;
     RecyclerView recyclerView;
     EventRecyclerAdapter eventRecyclerAdapter;

    FloatingActionButton addEvent,profileMain;
    FirebaseDatabase mFireBaseDataBase;
    DatabaseReference myRef;
    User usr;
    Activity activity = this;
    ArrayList<User> userss = new ArrayList<User>();

    FloatingActionButton btnAddEvent;

    private SearchView searchView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireBaseDataBase = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = mFireBaseDataBase.getReference();
        searchView = findViewById(R.id.search_bar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return false;
            }
        });
        events = new ArrayList<>();
        Context c = this;

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
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
                            usr = userss.get(i);
                            eventRecyclerAdapter.setUser(usr);
                            eventRecyclerAdapter.setActivity(activity);
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
                    if(!e.isFinished() && !e.isFull() && !e.getUsersIdList().contains(FirebaseAuth.getInstance().getUid()))
                    {
                        events.add(e);
                    }
                }
                Collections.sort(events);
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

    public void filterlist(String newText) {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for(Event e : events)
        {
            if(e.getTitle().toLowerCase().contains(newText.toLowerCase()))
            {
                filteredEvents.add(e);
            }
        }

        if(filteredEvents.isEmpty())
        {
            System.out.println("fdsafdas");
            Toast.makeText(this,"No such event found",Toast.LENGTH_SHORT).show();
        }
        else
        {
            eventRecyclerAdapter.setFilteredEvent(filteredEvents);
        }
    }
}