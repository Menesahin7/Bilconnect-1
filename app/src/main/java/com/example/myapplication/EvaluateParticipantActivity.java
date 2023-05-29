package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    String usersId;
    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_participant);

        eval = findViewById(R.id.ratingBarStars);
        evaluateUser = findViewById(R.id.evaluateButton);
        listView = findViewById(R.id.listViewParticipants);
        backButton= findViewById(R.id.floatingActionButtonclose);

        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(EvaluateParticipantActivity.this, android.R.layout.activity_list_item,users);

        db = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/");
        mRef = db.getReference();

        mRef.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    eventt = (Event) snapshot.getValue(Event.class);
                    usersId = eventt.getUsersIdList();
                    try {
                        events.add(event);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while retrieving user data
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });

        for(int i = 0; i<events.size(); i++) {
            if(events.get(i).getEventId().equals("KHTzjJLt35ZCORNF4EQIhthF4xL20")) {
                event = events.get(0);
            }
        }

        //if (usersId.substring(0, usersId.indexOf(",")).equals(FirebaseAuth.getInstance().getUid())) {
        users.add(usersId.substring(0, usersId.indexOf(",")));
        usersId = usersId.substring(usersId.indexOf(",") + 1);
        //}

        for (int i = 1; i < event.getUserCount(); i++) {
            if(usersId.length()>2) {
                if (usersId.substring(0, usersId.indexOf(",")).equals(FirebaseAuth.getInstance().getUid())) {
                    users.add(usersId.substring(0, usersId.indexOf(",")));
                    usersId = usersId.substring(usersId.indexOf(",") + 1);
                }
            }

        }



        // Update the ListView adapter after adding users
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



        /* bug var
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(EvaluateParticipantActivity.this,MainActivity.class));
            }
        });*/
    }



    public void evaluate()
    {
        // this method will evaluate the current users rating.
    }
}