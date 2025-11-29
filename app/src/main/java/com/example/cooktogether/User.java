package com.example.cooktogether;

import java.util.ArrayList;

public class User {
    private String Name;

    private String Uid;
    private boolean alarm=false;


    public User(String name, String Uid){
        this.Name=name;
        this.Uid=Uid;
    }


    public String getName() {
        return Name;
    }


    public String getUid() {
        return Uid;
    }

    public void setName(String name) {
        Name = name;
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
