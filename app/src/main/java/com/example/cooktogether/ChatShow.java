package com.example.cooktogether;

public class ChatShow {
    private Chat chat;
    private String otherName;
    public ChatShow(Chat chat,String s){
        this.chat=chat;
        otherName=s;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }
}
