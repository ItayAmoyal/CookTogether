package com.example.cooktogether;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String Uid;
    private String RecipeID;
    private ArrayList<InstructionItem> Instructions;
    private String CookTime;
    private String picture;
    private double averageRatingSum;
    private double averageRating;
   private int averageratingCount;
    private ArrayList<Ingridiants> ingridiantsArrayList;



    public Recipe(){

    }
    public Recipe(String name, String MadeRecipe) {
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

    public String getRecipeID() {
        return RecipeID;
    }
    public void setPicture(String picture){
        this.picture=picture;
    }

    public void setIngridiantsArrayList(ArrayList<Ingridiants> ingridiantsArrayList) {
        this.ingridiantsArrayList = ingridiantsArrayList;
    }

    public void setInstructions(ArrayList<InstructionItem> instructions) {
        Instructions = instructions;
    }

    public void setRecipeID(String recipeID) {
        RecipeID = recipeID;
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


    public double getAverageRating() {
        return averageRating;
    }


    public void setCookTime(String cookTime) {
        CookTime = cookTime;
    }

    public String getPicture() {
        return picture;
    }
    public void setAverageratingCount(int averageratingCount) {
        this.averageratingCount = averageratingCount;
    }

    public void setAverageRatingSum(double averageRatingSum) {
        this.averageRatingSum = averageRatingSum;
    }

}
