package com.example.cooktogether;

public class Message {
    private  String SentMessage;
    private String text;
    private String time;

    public Message(){

    }
    public  Message(String text,String sentMessage,String time){
        this.SentMessage=sentMessage;
        this.text=text;
        this.time=time;
    }

    public String getSentMessage() {
        return SentMessage;
    }

    public void setSentMessage(String sentMessage) {
        SentMessage = sentMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
