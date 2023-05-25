package com.example.myapplication;

import java.util.ArrayList;

public class User {
    protected String userName;         // username
    protected String password;         //password
    protected String mail;             //mail
    protected double rating; // User's rating when creating user it is 0.
    protected int count; // This is the number of rating that user get.
    protected String bio;
    protected ArrayList<Event> attendedEvents;

    public User(String name, String mail, String passW)
    {
        this.userName = name;
        this.mail = mail;
        this.password = passW;
        this.rating = 0;
        this.count = 0;
        this.bio = "Hi! i am Bilkent student.";
        this.attendedEvents = new ArrayList<Event>();
    }

    public void evalRating(int num)
    {
        double sum = this.rating*count;
        sum += num;
        this.count++;
        this.rating = sum / count;
    }

    public void attendEvent(Event e)
    {
        if(!attendedEvents.contains(e))
        {
            attendedEvents.add(e);
        }
    }

    public void removeEvent(Event e)
    {
        if(attendedEvents.contains(e))
        {
            attendedEvents.remove(e);
        }
    }

    protected void setUserName(String userName) {
        this.userName = userName;
    }
    protected void setPassword(String password) {
        this.password = password;
    }
    protected void setMail(String mail) {
        this.mail = mail;
    }

    protected void setBio(String biography)
    {
        this.bio = biography;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }
}

