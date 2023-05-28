
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
    protected int userCount;
    protected String usersIdList;
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
        this.userCount = 1;
        this.quota = quota;
        this.Time = time;
        this.campus = campus;
        this.usersIdList = hostId + ",";
        this.hostId = hostId;
        this.active = true;
        this.eventId = eventId;
    }

    public void removeUser(String uid)
    {
        if(usersIdList.contains(uid)) {
            usersIdList = usersIdList.replace((uid + ","), "");
            userCount--;
        }
    }

    public void addUser(String uid)
    {
        if(userCount<quota)
        {
            usersIdList = usersIdList + uid + ",";
            userCount++;
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

        SimpleDateFormat sdf = new SimpleDateFormat("mm/hh");
        String currentTime = sdf.format(new Date());

        int currentHour = Integer.valueOf(currentTime.substring(0,2));
        int currentMinute = Integer.valueOf(currentTime.substring(3,5));

        int eventHour = Integer.valueOf(Time.substring(0,2));
        int eventMinute = Integer.valueOf(Time.substring(3,5));

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
        else if(eventHour>currentHour) {
            this.active = false;
            return true;
        }
        else if(eventMinute>currentMinute) {
            this.active = false;
            return true;
        }
        else {
            this.active = true;
            return false;
        }
    }

    public boolean isFull() {
        if(userCount>=quota)
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


    public void setHostId(String hostUser) {
        this.hostId = hostUser;
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

    public int getUserCount() {return userCount;}

    public String getUsersIdList() {return usersIdList;}

    public String getHostUser() {
        return hostId;
    }
}

