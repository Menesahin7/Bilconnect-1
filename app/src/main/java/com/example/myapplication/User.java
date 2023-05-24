package com.example.myapplication;

public class User {

    protected int id;   //unique user code, it contains 6 digit [0-9]
    protected String userName;         // username
    protected String password;         //password
    protected String mail;             //mail
    protected double rating; // User's rating when creating user it is 0.
    protected int count; // This is the number of rating that user get.


    public User(int id, String name, String mail, String passW)
    {
        this.id = id;
        this.userName = name;
        this.mail = mail;
        this.password = passW;
        this.rating = 0;
        this.count = 0;
    }

    public void evalRating(int num)
    {
        double sum = this.rating*count;
        sum += num;
        this.count++;
        this.rating = sum / count;
    }

    protected void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
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

