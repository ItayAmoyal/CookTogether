package com.example.cooktogether;

import android.net.Uri;

public class InstructionItem {

    private String instructionText;
    private Uri imageUri;

    public InstructionItem(String instructionText, Uri imageUri) {
        this.instructionText = instructionText;
        this.imageUri = imageUri;
    }

    public String getInstructionText() {
        return instructionText;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}

