
package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Event {
    protected String title;
    protected String description;
    protected String location;
    protected String date;
    protected int capacity;
    protected ArrayList<String> usersIdList;
    protected int quota;
    protected String hostId;
    protected String Time;

    public static int eventId;

    protected String campus;

    public Event(String title,int quota,String desc,String loc, String date, String time,String hostId,String campus)
    {
        this.title = title;
        this.description = desc;
        this.location = loc;
        this.date = date;
        this.capacity = 0;
        this.quota = quota;
        this.Time = time;
        this.campus = campus;
        this.usersIdList = new ArrayList<String>();
        this.hostId = hostId;
    }

    /*public void removeUser(User user)
    {
        boolean isIn = false;
        int index = -1;
        for(int i = 0; i<this.users.length; i++)
        {
            if(users[i] == user)
            {
                isIn = true;
                index = i;
            }
        }
        if(isIn)
        {
            users[index] = null;
        }
    }

    public void addUser(User user)
    {
        if(capacity<this.users.length)
        {
            users[capacity] = user;
            capacity++;
        }
    }*/

    /*public boolean isFinished()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        // These are the current time values.
        int year = Integer.parseInt(dateFormat.format(date).substring(0,4));
        int month = Integer.parseInt(dateFormat.format(date).substring(5,7));
        int day = Integer.parseInt(dateFormat.format(date).substring(8,10));
        int hour = Integer.parseInt(dateFormat.format(date).substring(11,13));
        int minute = Integer.parseInt(dateFormat.format(date).substring(14));
        // These are the event time values.
        int yearE = Integer.parseInt(getDate().substring(0,4));
        int monthE = Integer.parseInt(getDate().substring(5,7));
        int dayE = Integer.parseInt(getDate().substring(8,10));
        int hourE = Integer.parseInt(getTime().substring(0,2));
        int minuteE = Integer.parseInt(getTime().substring(3));
        // I will compare the event time and current time and return a boolean value

        System.out.println(dateFormat.format(date));
        return true; // düzeltcem
    }*/

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return Time;
    }

    public String getHostId() {
        return hostId;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCampus() {
        return campus;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUsersIdList(ArrayList<String> usersIdList) {
        this.usersIdList = usersIdList;
    }

    public void setHostId(String hostUser) {
        this.hostId = hostUser;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<String> getUsersIdList() {
        return usersIdList;
    }

    public String getHostUser() {
        return hostId;
    }
}

