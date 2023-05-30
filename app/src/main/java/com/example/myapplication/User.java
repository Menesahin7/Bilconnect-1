package com.example.myapplication;

import java.util.ArrayList;

public class User {
    protected String name;         // username

    protected String mail;             //mail
    protected String id;
    protected String campus; //east campus or main campus
    protected double rating; // User's rating when creating user it is 0.
    protected int count; // This is the number of events that user attended.
    protected int ratingCount;
    protected String bio;
    protected String attendedEvents;

    public User()
    {

    }

    public User(String name, String mail, String bio, String uid, String campus)
    {
        this.name = name;
        this.mail = mail;
        this.bio = bio;
        this.id = uid;
        this.campus = campus;  //east or main
        this.rating = 0;
        this.count = 0;
        this.attendedEvents = "";
    }
    public void evalRating(double num)
    {
        double sum = this.rating*ratingCount;
        sum += num;
        this.ratingCount++;
        this.rating = sum / ratingCount;
    }

    public boolean attendEvent(String e)
    {
        if(!attendedEvents.contains(e)) {
            attendedEvents = attendedEvents + e +",";
            count++;
            return true;
        }
        return false;
    }

    public boolean removeEvent(String e)
    {
        if(attendedEvents.contains(e))
        {
            attendedEvents = attendedEvents.replace((e + ","), "");
            count--;
            return true;
        }
        return false;
    }

    protected void setName(String name) {
        this.name = name;
    }
    protected void setMail(String mail) {
        this.mail = mail;
    }

    protected void setBio(String biography)
    {
        this.bio = biography;
    }

    public void setCampus(String campus) {this.campus = campus;}

    public String getName() {
        return name;
    }
    public String getBio() {return bio; }

    public String getId() {return id; }

    public String getMail() {
        return mail;
    }

    public double getRating() {
        return rating;
    }


    public String getCampus() {return campus; }

    public String getAttendedEvents() {return attendedEvents;}

    public int getCount() {return count;}

    public int getRatingCount() {
        return ratingCount;
    }

    public String toString()
    {
        return getName();
    }
}

