
package com.example.myapplication;

import java.util.Date;

public class Event {

    protected String title;
    protected String description;
    protected String location;
    protected Date date;
    protected int capacity;
    protected User[] users;
    protected int quota;
    protected User hostUser;

    public Event(String title,int quota,String desc,String loc, Date date, User host)
    {
        this.title = title;
        this.description = desc;
        this.location = loc;
        this.date = date;
        this.capacity = 0;
        this.quota = quota;
        this.users = new User[quota];
        this.hostUser = host;
    }

    public void removeUser(User user)
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
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public void setHostUser(User hostUser) {
        this.hostUser = hostUser;
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

    public Date getDate() {
        return date;
    }

    public int getCapacity() {
        return capacity;
    }

    public User[] getUsers() {
        return users;
    }

    public User getHostUser() {
        return hostUser;
    }
}

