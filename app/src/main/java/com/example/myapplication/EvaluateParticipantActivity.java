package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluateParticipantActivity extends AppCompatActivity {
    RatingBar eval;
    Button evaluateUser;
    FloatingActionButton backButton;
    ListView listView;
    float rateValue;
    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<String> users = new ArrayList<String>();
    FirebaseDatabase db;
    DatabaseReference mRef;
    Event eventt;
    String eventId;
    String usersId;
    Event event;
    User userToEvaluate;
    ArrayAdapter<String> arrAdapter;
    ArrayList<User> userArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_participant);
        userArray = new ArrayList<User>();

        eval = findViewById(R.id.ratingBarStars);
        evaluateUser = findViewById(R.id.evaluateButton);
        listView = findViewById(R.id.listViewParticipants);
        backButton= findViewById(R.id.floatingActionButtonclose);
        arrAdapter  = new ArrayAdapter<String>(EvaluateParticipantActivity.this,android.R.layout.simple_list_item_1,users);
        listView.setAdapter(arrAdapter);
        eventId = getIntent().getStringExtra(eventId);

        db = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/");
        mRef = db.getReference();

        mRef.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    eventt = snapshot.getValue(Event.class);
                    usersId = eventt.getUsersIdList();
                    if(eventt.getEventId() != null && !eventt.getEventId().isEmpty())
                    {
                        System.out.println(eventt.getEventId());
                        if (eventt.getEventId().equals(eventId)) {
                                mRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                                        {
                                            String userId = snapshot.getKey();
                                            System.out.println(userId);
                                            for(int i = 0; i<eventt.usersIdList.length();i++)
                                            {
                                                String sUserId = eventt.usersIdList.substring(i,eventt.usersIdList.indexOf(","));
                                                System.out.println(sUserId);
                                                if(userId.equals(sUserId) && !userId.equals(FirebaseAuth.getInstance().getUid()))
                                                {
                                                    User usr = (User) snapshot.getValue(User.class);
                                                    String userName = snapshot.child("name").getValue(String.class);
                                                    users.add(userName);
                                                    userArray.add(usr);
                                                    System.out.println(6);
                                                    arrAdapter.notifyDataSetChanged();
                                                }
                                                i = eventt.usersIdList.indexOf(",");
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Error occurred while retrieving user data
                                        Log.e("firebase", "Error getting user data", databaseError.toException());
                                    }
                                });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while retrieving event data
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });


        arrAdapter.notifyDataSetChanged();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userToEvaluate = userArray.get(position);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EvaluateParticipantActivity.this,AttendedEventsActivity.class));
            }
        });
    }


    public void evaluate()
    {
        try {
            userToEvaluate.evalRating(rateValue);
            mRef.child("users").child(userToEvaluate.getId()).setValue(userToEvaluate);
        }
        catch (Exception e) {
            Toast.makeText(EvaluateParticipantActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        // this method will evaluate the current users rating.

    }
}