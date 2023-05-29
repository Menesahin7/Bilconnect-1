package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddingEventActivity extends AppCompatActivity {
    protected EditText eName,eQuota,eDate,eTime,ePlace,eDesc;

    protected FloatingActionButton backButton;
    protected Button createEventButton;

    String campusAct;
    Calendar calendar;
    String dateFormat;
    SimpleDateFormat simpleDateFormat;
    String currentDate;
    Date selectedDate;
    RadioButton main,east;
    RadioGroup group;
    FirebaseAuth mAuth;
    FirebaseDatabase mFireBaseDataBase;
    DatabaseReference userRef;
    DatabaseReference myRef;
    User user;
    String uid;
    Event event;
    User usr;
    ArrayList<User> userss = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mFireBaseDataBase = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/");
        userRef = mFireBaseDataBase.getReference("users");
        myRef = mFireBaseDataBase.getReference();

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
<<<<<<< Updated upstream
                    usr = (User) snapshot.getValue(User.class);
=======
                    usr = snapshot.getValue(User.class);
                    try {
>>>>>>> Stashed changes
                        userss.add(usr);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while retrieving user data
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });


        calendar = Calendar.getInstance();
        dateFormat = "dd.MM.yyyy";  // Define the desired date format
        simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        currentDate = simpleDateFormat.format(calendar.getTime());

        setContentView(R.layout.activity_adding_event);
        eName = findViewById(R.id.editTextEventName);
        eQuota = findViewById(R.id.editTextQuota);
        eDate = findViewById(R.id.editTextDate);
        eDate.setHint(currentDate);
        eDesc = findViewById(R.id.editTextEventDesc);
        eTime = findViewById(R.id.editTextTime);
        ePlace = findViewById(R.id.editTextEventPlace);
        main = findViewById(R.id.mainCamp);
        east = findViewById(R.id.eastCamp);
        group = findViewById(R.id.rGroupCampusAdd);
        backButton =  findViewById(R.id.floatingActionButton2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddingEventActivity.this,MainActivity.class));
            }
        });

        createEventButton = findViewById(R.id.createEventButton);

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent();
            }
        });

    }

    public void createEvent()
    {
        for(int i = 0; i<userss.size();i++)
        {
            System.out.println(userss.get(i));
        }
        String eventName = eName.getText().toString();
        String eventQuota = eQuota.getText().toString();
        String eventDate = eDate.getText().toString();
        String eventTime = eTime.getText().toString();
        String eventDesc = eDesc.getText().toString();
        String eventPlace = ePlace.getText().toString();
        // Choosing the activity campus


        if(TextUtils.isEmpty(eventName)) {
            eName.setError("Event name cannot be empty.");
            eName.requestFocus();
        } else if (group.getCheckedRadioButtonId() == -1) {
            main.setError("One campus must be chosen");
            System.out.println(2);
            main.requestFocus();
        } else if(TextUtils.isEmpty(eventQuota))
        {
            eQuota.setError("Event quota cannot be empty.");
            eQuota.requestFocus();
        }
        else if(Integer.valueOf(eventQuota)<=1)
        {
            eQuota.setError("Event quota cannot less then 2");
            eQuota.requestFocus();
        }
        else if(TextUtils.isEmpty(eventDate))
        {
            eDate.setError("Event date cannot be empty.");
            eDate.requestFocus();
        }
        else if(!eventDate.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"))
        {
            eDate.setError("Event date should be formatted dd/mm/yyyy.");
            eDate.requestFocus();
        }
        else if(TextUtils.isEmpty(eventTime))
        {
            eTime.setError("Event time cannot be empty.");
            eTime.requestFocus();
        }
        else if(!eventTime.matches("^(?:[01][0-9]|2[0-3]):[0-5][0-9]$"))
        {
            eTime.setError("Event time should be formatted hh.mm or hh:mm .");
            eTime.requestFocus();
        }
        else if(TextUtils.isEmpty(eventDesc))
        {
            eDesc.setError("Event description cannot be empty.");
            eDesc.requestFocus();
        }
        else if(TextUtils.isEmpty(eventPlace))
        {
            ePlace.setError("Event place cannot be empty.");
            ePlace.requestFocus();
        }
        else
        {
            if(main.isSelected())
                campusAct = main.getText().toString();
            else if (east.isSelected())
                campusAct = east.getText().toString();



            String eventId = "";
            for(int i = 0; i<userss.size(); i++) {
                if (userss.get(i).getId().equals(mAuth.getUid())) {
                    eventId = mAuth.getUid() + userss.get(i).getCount();
                }
            }
            Event event = new Event(eventName,Integer.valueOf(eventQuota),eventDesc,eventPlace,eventDate,eventTime,uid,campusAct, eventId);
            myRef.child("events").child(eventId).setValue(event);
            try {
                for(int i = 0; i<userss.size(); i++) {
                    if(userss.get(i).getId().equals(mAuth.getUid())) {
                        User usr = userss.get(i);
                        usr.attendEvent(eventId);
                        myRef.child("users").child(uid).setValue(usr);
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }


            Intent intent = (new Intent(AddingEventActivity.this,MainActivity.class));
            startActivity(intent);
        }
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}