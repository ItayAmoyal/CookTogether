package com.example.cooktogether;

import java.util.ArrayList;

public class recipe {
    private String name;
    private String MadeRecipeUid;
    private ArrayList<String> Instructions;
    private int cookTime;
    private String CookTimeParameter;
    private String picture;
    Filters[]FilterArray;
    ArrayList<Comments> commentsArrayList;
    double averageRating;
    ArrayList<Integer> RatingsArray;
    ArrayList<Ingridiants> ingridiantsArrayList;
    public recipe(String name,String MadeRecipe){
        this.name=name;
        this.MadeRecipeUid=MadeRecipe;
        averageRating=0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAverageRating() {
        double sum=0;
        int count=0;
       for(int rating:this.RatingsArray){
           count++;
           sum=sum+rating;
       }
        this.averageRating=(sum/count);
       averageRating=averageRating*10;
       if(((averageRating%10)>=0&&(averageRating%10)<3)  ||  ((averageRating%10)>=5&&(averageRating%10)<8)){
          averageRating=averageRating-(averageRating%10);
           averageRating=averageRating/10;
       }
       else{
           averageRating=averageRating+(5-averageRating%10);
       }
    }

    public ArrayList<Comments> getCommentsArrayList() {
        return commentsArrayList;
    }

    public ArrayList<Ingridiants> getIngridiantsArrayList() {
        return ingridiantsArrayList;
    }

    public ArrayList<Integer> getRatings() {
        return RatingsArray;
    }

    public ArrayList<String> getInstructions() {
        return Instructions;
    }

    public Filters[] getFilterArray() {
        return FilterArray;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getCookTime() {
        return cookTime;
    }

    public String getMadeRecipeUid() {
        return MadeRecipeUid;
    }

    public String getCookTimeParameter() {
        return CookTimeParameter;
    }

    public String getPicture() {
        return picture;
    }
}
