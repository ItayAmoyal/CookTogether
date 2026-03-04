package com.example.cooktogether;

import android.net.Uri;

import java.util.ArrayList;

public class InstructionItem {

    private String instructionText;
    private ArrayList<String> fileName;

    public InstructionItem(String instructionText, ArrayList<String> fileName) {
        this.instructionText = instructionText;
        this.fileName = fileName;
    }
    public InstructionItem(){

    }

    public void setInstructionText(String instructionText) {
        this.instructionText = instructionText;
    }

    public void setFileName(ArrayList<String> fileName) {
        this.fileName = fileName;
    }

    public String getInstructionText() {
        return instructionText;
    }

    public ArrayList<String> getFileName() {
        return fileName;
    }
}

