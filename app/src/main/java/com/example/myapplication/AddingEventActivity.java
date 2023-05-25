package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddingEventActivity extends AppCompatActivity {
    protected EditText eName,eQuota,eDate,eTime,ePlace,eDesc;

    protected FloatingActionButton backButton;
    protected Button createEventButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_event);
        eName = findViewById(R.id.editTextEventName);
        eQuota = findViewById(R.id.editTextQuota);
        eDate = findViewById(R.id.editTextDate);
        eDesc = findViewById(R.id.editTextEventDesc);
        eTime = findViewById(R.id.editTextTime);
        ePlace = findViewById(R.id.editTextEventPlace);
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
        String eventName = eName.getText().toString();
        String eventQuota = eQuota.getText().toString();
        String eventDate = eDate.getText().toString();
        String eventTime = eTime.getText().toString();
        String eventDesc = eDesc.getText().toString();
        String eventPlace = ePlace.getText().toString();

        if(TextUtils.isEmpty(eventName)) {
            eName.setError("Event name cannot be empty.");
            eName.requestFocus();
        }
        else if(TextUtils.isEmpty(eventQuota))
        {
            eQuota.setError("Event quota cannot be empty.");
            eQuota.requestFocus();
        }
        else if(TextUtils.isEmpty(eventDate))
        {
            eDate.setError("Event date cannot be empty.");
            eDate.requestFocus();
        }
        else if(!eventDate.matches("(.*)/(.*)/(.*)"))
        {
            eDate.setError("Event date should be formatted dd/mm/yyyy.");
            eDate.requestFocus();
        }
        else if(TextUtils.isEmpty(eventTime))
        {
            eTime.setError("Event time cannot be empty.");
            eTime.requestFocus();
        }
        else if(!eventTime.matches("(.*).(.*)"))
        {
            eTime.setError("Event time should be formatted hh.mm .");
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

        }
    }
}