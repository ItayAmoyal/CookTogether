package com.example.cooktogether;

import java.util.ArrayList;

public class recipe {
    private String name;
    private String Uid;
    private ArrayList<InstructionItem> Instructions;
    private String CookTime;
    private String picture;
    private Filters[] FilterArray;
    private double averageRatingSum;
    private double averageRating;
   private int averageratingCount;
    private ArrayList<Ingridiants> ingridiantsArrayList;

    public recipe(String name, String MadeRecipe) {
        this.name = name;
        this.Uid = MadeRecipe;
        averageRatingSum = 0;
        averageratingCount = 0;
        averageRating = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getAverageRatingSum() {
        return averageRatingSum;
    }

    public int getAverageratingCount() {
        return averageratingCount;
    }

    public String getCookTime() {
        return CookTime;
    }

    public ArrayList<Ingridiants> getIngridiantsArrayList() {
        return ingridiantsArrayList;
    }


    public ArrayList<InstructionItem> getInstructions() {
        return Instructions;
    }

    public Filters[] getFilterArray() {
        return FilterArray;
    }

    public double getAverageRating() {
        return averageRating;
    }


    public void setCookTime(String cookTime) {
        CookTime = cookTime;
    }

    public String getPicture() {
        return picture;
    }
}
