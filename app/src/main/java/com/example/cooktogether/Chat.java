package com.example.cooktogether;

import java.util.ArrayList;

public class Chat {
    private String user1Uid;
    private String user2Uid;
    private String chatId;
    ArrayList<Message>allMessages;
    public Chat(){

    }
    public  Chat(String uid1,String uid2,String chatId1){
        user1Uid= uid1;
        user2Uid=uid2;
        chatId=chatId1;
        allMessages=new ArrayList<>();
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public ArrayList<Message> getAllMessages() {
        return allMessages;
    }

    public String getUser1Uid() {
        return user1Uid;
    }

    public String getUser2Uid() {
        return user2Uid;
    }

    public void setAllMessages(ArrayList<Message> allMessages) {
        this.allMessages = allMessages;
    }

    public void setUser1Uid(String user1Uid) {
        this.user1Uid = user1Uid;
    }

    public void setUser2Uid(String user2Uid) {
        this.user2Uid = user2Uid;
    }
    public void AddMessage(Message message){
        if(allMessages!=null) {
            allMessages.add(message);
        }
        else{
            allMessages=new ArrayList<>();
            allMessages.add(message);
        }
    }
}
