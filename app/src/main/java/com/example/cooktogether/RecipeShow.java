package com.example.cooktogether;

import android.graphics.Bitmap;

public class RecipeShow {
    private Recipe recipe;
    private Bitmap bitmap;

    public RecipeShow(Recipe recipe, Bitmap bitmap) {
        this.recipe=recipe;this.bitmap=bitmap;
    }
public RecipeShow(){
}
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
