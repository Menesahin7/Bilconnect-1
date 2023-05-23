package com.example.myapplication;

import android.graphics.*;


public class User {

    protected int id;   //unique user code, it contains 6 digit [0-9]
    protected String userName;         // username
    protected String password;         //password
    protected String mail;             //mail




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
