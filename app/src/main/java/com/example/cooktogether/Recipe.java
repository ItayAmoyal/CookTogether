package com.example.cooktogether;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String Uid;
    private String recipeID;
    private ArrayList<InstructionItem> Instructions;
    private String CookTime;
    private String picture;
    private int averageRatingSum;
    private int averageRating;
    ArrayList<String> uidRated=new ArrayList<>();
   private int averageratingCount;
    private ArrayList<Ingridiants> ingridiantsArrayList;



    public Recipe(){
    }
    public Boolean AddRating(int num,String uid){
        int count=0;
        if(uidRated==null){
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

    public void setUidRated(ArrayList<String> uidRated) {
        this.uidRated = uidRated;
    }

    public Recipe(Recipe r) {
        this.name = r.name;
        this.Uid = r.Uid;
        this.recipeID = r.recipeID;

        this.Instructions = new ArrayList<>(r.Instructions);

        this.ingridiantsArrayList = new ArrayList<>(r.ingridiantsArrayList);

        this.CookTime = r.CookTime;
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
        return recipeID;
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
        return CookTime;
    }

    public ArrayList<Ingridiants> getIngridiantsArrayList() {
        return ingridiantsArrayList;
    }


    public ArrayList<InstructionItem> getInstructions() {
        return Instructions;
    }


    public int getAverageRating() {
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

    public void setAverageRatingSum(int averageRatingSum) {
        this.averageRatingSum = averageRatingSum;
    }

}
