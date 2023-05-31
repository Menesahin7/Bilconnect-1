package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.MyViewHolder> {
    ArrayList<User> users;


    User user;
    Activity activity;

    public UserRecyclerAdapter(ArrayList<User> users) {
        this.users = users;
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile_single_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        user = users.get(position);
        holder.textViewBiography.setText(users.get(position).getBio());
        holder.textViewCampus.setText(users.get(position).getCampus());
        holder.textViewUserName.setText(users.get(position).getName());
        holder.textViewUserRating.setText(""+users.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewUserName,textViewUserRating,textViewCampus,textViewBiography;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserRating = itemView.findViewById(R.id.textViewUserRating);
            textViewCampus = itemView.findViewById(R.id.textViewCampus);
            textViewBiography = itemView.findViewById(R.id.textViewBiography);

        }
    }

}
