package com.example.cooktogether;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private ArrayList<Recipe> recipes;
    private OnRecipeClickListener listener;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_rv_allrecipes, parent, false);
        return new RecipeViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Random random = new Random();
        int number = random.nextInt(4) + 1;
            if(number==1){
                holder.main.setBackgroundResource(R.drawable.bg_recipe_card);
            }
            else if (number==2) {
                holder.main.setBackgroundResource(R.drawable.bg_recipe_cardsunset);
            }
            else if (number==3) {
                holder.main.setBackgroundResource(R.drawable.bg_recipe_cardpurple);
            }
            else{
            holder.main.setBackgroundResource(R.drawable.bg_recipe_cardblue);
        }
        Recipe recipe = recipes.get(position);
        holder.textRecipeName.setText(recipe.getName());
        holder.textCookTime.setText("זמן הכנה: " + recipe.getCookTime());
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
    public interface OnRecipeClickListener {
        void onRecipeClick(int position);
    }
    public void setOnRecipeClickListener(OnRecipeClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageRecipe,rating1,rating2,rating3,rating4,rating5;
        TextView textRecipeName;
        TextView textCookTime;
        LinearLayout main;
        public RecipeViewHolder(@NonNull View itemView,OnRecipeClickListener listener) {
            super(itemView);
            imageRecipe = itemView.findViewById(R.id.imageRecipe);
            textRecipeName = itemView.findViewById(R.id.textRecipeName);
            textCookTime = itemView.findViewById(R.id.textCookTime);
            rating1=itemView.findViewById(R.id.star1);
            rating2=itemView.findViewById(R.id.star2);
            rating3=itemView.findViewById(R.id.star3);
            rating4=itemView.findViewById(R.id.star4);
            rating5=itemView.findViewById(R.id.star5);
            main=itemView.findViewById(R.id.mainlayout);
             itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRecipeClick(getAdapterPosition());
                }
            });
        }
    }
}

