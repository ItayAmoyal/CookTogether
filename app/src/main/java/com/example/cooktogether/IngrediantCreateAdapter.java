package com.example.cooktogether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngrediantCreateAdapter extends RecyclerView.Adapter<IngrediantCreateAdapter.IngredientViewHolder> {

    private ArrayList<Ingredient> ingredients;
    private Context context;

    public IngrediantCreateAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_makeingridiants, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.name.setText(ingredient.getName());
        holder.amount.setText(ingredient.getAmount());
        holder.unit.setText(ingredient.getUnit());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        notifyItemInserted(ingredients.size() - 1);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView name,amount,unit;
        ImageButton btnDelete;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.IngredientsName);
            amount = itemView.findViewById(R.id.IngredientsAmount);
            unit = itemView.findViewById(R.id.Unit);
            btnDelete = itemView.findViewById(R.id.btnDeleteIngredient);

            btnDelete.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    ingredients.remove(pos);
                    notifyItemRemoved(pos);
                }
            });
        }
    }
}