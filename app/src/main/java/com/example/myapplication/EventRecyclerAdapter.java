package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder> {
    ArrayList<Event> events;

    public EventRecyclerAdapter(ArrayList<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_single_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textViewEventName.setText(events.get(position).getTitle());
        holder.editTextDesc.setText(events.get(position).getDescription());
        holder.editTextPlace.setText(events.get(position).getLocation());
        holder.editTextParticipants.setText(events.get(position).getUserCount()+"/"+events.get(position).getQuota() +"");
        holder.editTextDate.setText(events.get(position).getDate());
        holder.editTextTime.setText(events.get(position).getTime());

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
            editTextParticipants = itemView.findViewById(R.id.editTextParticipants);
            editTextDate = itemView.findViewById(R.id.editTextDate);
            editTextTime = itemView.findViewById(R.id.editTextTime);
            editTextPlace = itemView.findViewById(R.id.editTextPlace);
            editTextDesc = itemView.findViewById(R.id.editTextDesc);
            btnAttendEvent = itemView.findViewById(R.id.btnAttendEvent);

        }
    }


}
