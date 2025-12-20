package com.example.cooktogether;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String uid;
    private String recipeID;
    private ArrayList<InstructionItem> instructions;
    private String cookTime;
    private String picture;
    private String difficulty;
    private int averageRatingSum;
    private int averageRating;
    ArrayList<String> uidRated;
   private int averageratingCount;
    private ArrayList<Ingredient> ingridiantsArrayList;



    public Recipe(){
    }
    public Boolean AddRating(int num,String uid){
        int count=0;
        if(uidRated==null){
            uidRated = new ArrayList<>();
            averageRatingSum = num + averageRatingSum;
            averageratingCount++;
            averageRating = averageRatingSum / averageratingCount;
            uidRated.add(uid);
            return true;
        }
        else {
            for (String uid1 : uidRated) {
                if (uid1.equals(uid))
                    count++;
            }
            if (count == 0) {
                averageRatingSum = num + averageRatingSum;
                averageratingCount++;
                averageRating = averageRatingSum / averageratingCount;
                uidRated.add(uid);
                return true;
            } else return false;
        }
    }

    public ArrayList<String> getUidRated() {
        return uidRated;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setUidRated(ArrayList<String> uidRated) {
        this.uidRated = uidRated;
    }

    public Recipe(Recipe r) {
        this.name = r.name;
        this.uid = r.uid;
        this.recipeID = r.recipeID;

        this.instructions = new ArrayList<>(r.instructions);

        this.ingridiantsArrayList = new ArrayList<>(r.ingridiantsArrayList);
        this.uidRated=r.uidRated;
        this.cookTime = r.cookTime;
        this.picture = r.picture;
        this.averageRatingSum = r.averageRatingSum;
        this.averageRating = r.averageRating;
        this.averageratingCount = r.averageratingCount;
    }
    public Recipe(String name, String MadeRecipe) {
        this.name = name;
        this.recipeID = MadeRecipe;
        averageRatingSum = 0;
        averageratingCount = 0;
        averageRating = 0;
        uidRated=new ArrayList<>();
        cookTime="";
        difficulty="";
        uid="";
        instructions=new ArrayList<>();
        ingridiantsArrayList=new ArrayList<>();
        picture="";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getRecipeID() {
        return recipeID;
    }
    public void setPicture(String picture){
        this.picture=picture;
    }

    public void setIngridiantsArrayList(ArrayList<Ingredient> ingridiantsArrayList) {
        this.ingridiantsArrayList = ingridiantsArrayList;
    }

    public void setInstructions(ArrayList<InstructionItem> instructions) {
        this.instructions = instructions;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public int getAverageRatingSum() {
        return averageRatingSum;
    }

    public int getAverageratingCount() {
        return averageratingCount;
    }

    public String getCookTime() {
        return cookTime;
    }

    public ArrayList<Ingredient> getIngridiantsArrayList() {
        return ingridiantsArrayList;
    }


    public ArrayList<InstructionItem> getInstructions() {
        return instructions;
    }


    public int getAverageRating() {
        return averageRating;
    }


    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getPicture() {
        return picture;
    }
    public void setAverageratingCount(int averageratingCount) {
        this.averageratingCount = averageratingCount;
    }

    public void setAverageRatingSum(int averageRatingSum) {
        this.averageRatingSum = averageRatingSum;
    }

}
