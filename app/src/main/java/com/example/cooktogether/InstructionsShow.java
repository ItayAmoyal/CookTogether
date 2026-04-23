package com.example.cooktogether;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class InstructionsShow {
    private InstructionItem instructionItem;
    private ArrayList<Bitmap>bitmaps;
    public InstructionsShow(InstructionItem instructionItem1,ArrayList<Bitmap>bitmaps1){
        instructionItem=instructionItem1;
        bitmaps=bitmaps1;
    }

    public InstructionItem getInstructionItem() {
        return instructionItem;
    }

    public void setInstructionItem(InstructionItem instructionItem) {
        this.instructionItem = instructionItem;
    }

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
}
