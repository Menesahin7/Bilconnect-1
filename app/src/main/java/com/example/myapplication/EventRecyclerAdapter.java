package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder> {
    ArrayList<Event> events;
    Event event;
    User user;
    Activity activity;

    public EventRecyclerAdapter(ArrayList<Event> events) {
        this.events = events;
        System.out.println("7");
    }

    public void setUser(User user) {
        if(!(user == null)) {
            this.user = user;
        }
    }

    public void setFilteredEvent(ArrayList<Event> filteredE)
    {
        this.events = filteredE;
        notifyDataSetChanged();
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_single_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        event = events.get(position);
        holder.textViewEventName.setText(events.get(position).getTitle());
        holder.editTextDesc.setText(events.get(position).getDescription());
        holder.editTextPlace.setText(events.get(position).getLocation());
        holder.editTextParticipants.setText(events.get(position).getUserCount()+"/"+events.get(position).getQuota() +"");
        holder.editTextDate.setText(events.get(position).getDate());
        holder.editTextTime.setText(events.get(position).getTime());

        if(event.isFinished()) {
            holder.btnAttendEvent.setText("Evaluate");
            holder.editTextDate.setTextColor(Color.RED);
            holder.editTextTime.setTextColor(Color.RED);
        }
        else if(event.getUsersIdList().contains(FirebaseAuth.getInstance().getUid())){
            holder.btnAttendEvent.setText("Leave");
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewEventName,editTextParticipants, editTextDate,editTextTime,editTextPlace,editTextDesc;
        Button btnAttendEvent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.textViewEventName);
            editTextParticipants = itemView.findViewById(R.id.textViewBiography);
            editTextDate = itemView.findViewById(R.id.editTextDate);
            editTextTime = itemView.findViewById(R.id.editTextTime);
            editTextPlace = itemView.findViewById(R.id.textViewUserRating);
            editTextDesc = itemView.findViewById(R.id.editTextDesc);
            btnAttendEvent = itemView.findViewById(R.id.btnAttendEvent);

            btnAttendEvent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        Event clickedEvent = events.get(position);

                        System.out.println(clickedEvent.getTitle());


                        if (btnAttendEvent.getText().equals("Leave")) {
                            if (user.removeEvent(clickedEvent.getEventId())) {
                                clickedEvent.removeUser(FirebaseAuth.getInstance().getUid());
                                FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users").child(user.getId()).setValue(user);
                                FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("events").child(clickedEvent.getEventId()).setValue(clickedEvent);
                                Toast.makeText(itemView.getContext(), "Successfull", Toast.LENGTH_SHORT).show();
                                if(activity != null) {
                                    activity.recreate();
                                }
                            }
                        } else if (btnAttendEvent.getText().equals("Evaluate")) {
                            Intent intent = new Intent(itemView.getContext(), EvaluateParticipantActivity.class);
                            intent.putExtra("eventId", clickedEvent.getEventId());
                            itemView.getContext().startActivity(intent);
                        } else {
                            System.out.println("3" + user.getId());
                            if (user.attendEvent(clickedEvent.getEventId())) {
                                System.out.println("4" + user.getId());
                                clickedEvent.addUser(FirebaseAuth.getInstance().getUid());
                                FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users").child(user.getId()).setValue(user);
                                FirebaseDatabase.getInstance("https://bilconnect-96cde-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("events").child(clickedEvent.getEventId()).setValue(clickedEvent);
                                Toast.makeText(itemView.getContext(), "Successfull", Toast.LENGTH_SHORT).show();
                                if(activity != null) {
                                    activity.recreate();
                                }
                            }
                        }
                    }
                }
            });
        }


    }



}
