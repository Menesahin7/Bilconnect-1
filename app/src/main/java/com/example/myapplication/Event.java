
package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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
    protected boolean active;

    public static String eventId;

    protected String campus;

    public Event(String title,int quota,String desc,String loc, String date, String time,String hostId,String campus, String eventId)
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
        this.active = true;
        this.eventId = eventId;
    }

    public void removeUser(String uid)
    {
        for(int i = 0; i<this.usersIdList.size(); i++)
        {
            if(usersIdList.get(i).equals(uid))
            {

                usersIdList.remove(i);
            }
        }
    }

    public void addUser(String uid)
    {
        if(capacity<this.usersIdList.size())
        {
            usersIdList.add(uid);
            capacity++;
        }
    }

    public boolean isFinished()
    {
        Calendar calendar = Calendar.getInstance();
        String dateFormat = "dd.MM.yyyy";  // Define the desired date format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(calendar.getTime());

        int currentDay = Integer.valueOf(currentDate.substring(0,2));
        int currentMonth = Integer.valueOf(currentDate.substring(3,5));
        int currentYear = Integer.valueOf(currentDate.substring(6,10));

        int eventDay = Integer.valueOf(date.substring(0,2));
        int eventMonth = Integer.valueOf(date.substring(3,5));
        int eventYear = Integer.valueOf(date.substring(6,10));

        if(eventYear>currentYear) {
            this.active = false;
            return true;
        }
        else if(eventMonth>currentMonth) {
            this.active = false;
            return true;
        } else if (eventDay>currentDay) {
            this.active = false;
            return true;
        }
        else {
            this.active = true;
            return false;
        }
    }

    public boolean isFull() {
        if(capacity>=this.usersIdList.size())
        {
            this.active = false;
            return true;
        }
        return false;
    }

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

    public static String getEventId() {return eventId; }

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

