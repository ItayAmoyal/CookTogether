package com.example.cooktogether;

import java.util.ArrayList;

public class User {
    private String Name;
    private String Email;
    private String Password;

    private String Uid;
    private boolean alarm=false;


    public User(String name, String Uid, String Email, String password){
        this.Name=name;
        this.Uid=Uid;
        this.Password=password;
        this.Email=Email;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public String getUid() {
        return Uid;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public boolean getAlarm() {
        return alarm;
    }
}
