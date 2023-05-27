package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!= null)
        {

        }

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mFireBaseDataBase = FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app");
        userRef = mFireBaseDataBase.getReference("users");
        myRef = mFireBaseDataBase.getReference();
        user = new User("deneme","deneme","bio","uid","main");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    //user = ds.child(uid).getValue(User.class);
                    //user = new User(dataSnapshot.child(uid).getValue(User.class).getName(),dataSnapshot.child(uid).getValue(User.class).getMail(),dataSnapshot.child(uid).getValue(User.class).getBio(),uid,dataSnapshot.child(uid).getValue(User.class).getCampus());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
       eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

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

            String eventId = (uid + "" + user.attendedEvents.size());
            Event event = new Event(eventName,Integer.valueOf(eventQuota),eventDesc,eventPlace,eventDate,eventTime,uid,campusAct, eventId);
            myRef.child("events").child(eventId).setValue(event);
            Intent intent = (new Intent(AddingEventActivity.this,MainActivity.class));
            startActivity(intent);
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        Calendar selectedDateCalendar = Calendar.getInstance();
                        selectedDateCalendar.set(Calendar.YEAR, year);
                        selectedDateCalendar.set(Calendar.MONTH, month);
                        selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        selectedDate = selectedDateCalendar.getTime();
                    }
                },
                year,
                month,
                dayOfMonth);

        datePickerDialog.show();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}