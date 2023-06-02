package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserInfoPage extends AppCompatActivity {
    private ArrayList<User> userss;

    private FirebaseDatabase mFireBaseDataBase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private Activity activity = this;
    private RecyclerView recyclerView;
    private User us;
    private UserRecyclerAdapter userRecyclerAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_page);

        searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });

        userss = new ArrayList<>();
        Context c = this;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        ArrayList<String> keys = new ArrayList<>();

        mFireBaseDataBase = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = mFireBaseDataBase.getReference();

        myRef.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event e = dataSnapshot.getValue(Event.class);
                    if (e.getUsersIdList().contains(userId)) {
                        String[] userIdArr = e.getUsersIdList().split(",");
                        if (userIdArr.length > 1) {
                            myRef.child("users").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        us = dataSnapshot.getValue(User.class);
                                        String userKey = dataSnapshot.getKey();

                                        if (!userKey.equals(userId)) {
                                            for (int i = 0; i < userIdArr.length; i++) {
                                                if (!userIdArr[i].equals(userId)) {
                                                    boolean cont = userss.contains(us);
                                                    boolean isV = keys.contains(userKey);
                                                    System.out.println(cont);
                                                    if (!cont && !isV) {
                                                        System.out.println(us.getName());
                                                        keys.add(userKey);
                                                        userss.add(us);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    recyclerView = findViewById(R.id.recyclerAdam);
                                    userRecyclerAdapter = new UserRecyclerAdapter(userss);
                                    userRecyclerAdapter.setActivity(activity);
                                    recyclerView.setAdapter(userRecyclerAdapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(c));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void filterList(String text)
    {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for(User u: userss)
        {
            if(u.getName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredUsers.add(u);
            }
        }

        if(filteredUsers.isEmpty())
        {
            Toast.makeText(this,"No such user found",Toast.LENGTH_SHORT).show();
        }
        else
        {
            userRecyclerAdapter.setFilteredUsers(filteredUsers);
        }
    }
}