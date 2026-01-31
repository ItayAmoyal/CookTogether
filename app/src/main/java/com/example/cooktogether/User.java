package com.example.cooktogether;

import java.util.ArrayList;

public class User {
    private String Name;

    private String Uid;
    private String pass;
    private int numOfRecepies;
    private String email;
    private boolean alarm=false;


    public User(String name, String Uid,String pass,String email){
        this.Name=name;
        this.Uid=Uid;
        this.email=email;
        this.pass=pass;
        this.numOfRecepies=0;
    }
    public User(){

    }

    public int getNumOfRecepies() {
        return numOfRecepies;
    }

    public void setNumOfRecepies(int numOfRecepies) {
        this.numOfRecepies = numOfRecepies;
    }
    public void addNumOfRecipes(){
        numOfRecepies++;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
