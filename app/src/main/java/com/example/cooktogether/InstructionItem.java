package com.example.cooktogether;

import android.net.Uri;

public class InstructionItem {

    private String instructionText;
    private String imageUri;

    public InstructionItem(String instructionText, String imageUri) {
        this.instructionText = instructionText;
        this.imageUri = imageUri;
    }
    public InstructionItem(){

    }

    public void setInstructionText(String instructionText) {
        this.instructionText = instructionText;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    public String getInstructionText() {
        return instructionText;
    }

    public String getImageUri() {
        return imageUri;
    }
}

