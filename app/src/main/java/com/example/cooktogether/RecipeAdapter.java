package com.example.cooktogether;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private ArrayList<Recipe> recipes;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_rv_allrecipes, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.textRecipeName.setText(recipe.getName());
        holder.textCookTime.setText("Cook time: " + recipe.getCookTime());

        ImageView[]ratings= {holder.rating1, holder.rating2, holder.rating3, holder.rating4, holder.rating5};
        for (int i = 0; i < 5; i++) {
            ratings[i].setImageResource(R.drawable.emptystar);
        }
        for (int i = 0; i < recipe.getAverageRating(); i++) {
            ratings[i].setImageResource(R.drawable.fullstar);
        }
        String pictureString = recipe.getPicture();
        if (pictureString != null && !pictureString.isEmpty()) {
            Uri uri = Uri.parse(pictureString);
            Glide.with(context)
                    .load(uri)
                    .placeholder(android.R.color.darker_gray)
                    .centerCrop()
                    .into(holder.imageRecipe);
        } else {
            holder.imageRecipe.setImageResource(android.R.color.darker_gray);
        }
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageRecipe,rating1,rating2,rating3,rating4,rating5;
        TextView textRecipeName;
        TextView textCookTime;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRecipe = itemView.findViewById(R.id.imageRecipe);
            textRecipeName = itemView.findViewById(R.id.textRecipeName);
            textCookTime = itemView.findViewById(R.id.textCookTime);
            rating1=itemView.findViewById(R.id.star1);
            rating2=itemView.findViewById(R.id.star2);
            rating3=itemView.findViewById(R.id.star3);
            rating4=itemView.findViewById(R.id.star4);
            rating5=itemView.findViewById(R.id.star5);
        }
    }
}

