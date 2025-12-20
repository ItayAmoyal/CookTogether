package com.example.cooktogether;

import android.net.Uri;

import java.util.ArrayList;

public class InstructionItem {

    private String instructionText;
    private ArrayList<String> imageUri;

    public InstructionItem(String instructionText, ArrayList<String> imageUri) {
        this.instructionText = instructionText;
        this.imageUri = imageUri;
    }
    public InstructionItem(){

    }

    public void setInstructionText(String instructionText) {
        this.instructionText = instructionText;
    }

    public void setImageUri(ArrayList<String> imageUri) {
        this.imageUri = imageUri;
    }

    public String getInstructionText() {
        return instructionText;
    }

    public ArrayList<String> getImageUri() {
        return imageUri;
    }
}

