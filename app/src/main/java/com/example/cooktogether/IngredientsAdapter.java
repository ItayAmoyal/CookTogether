package com.example.cooktogether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private
    Context context;
    private ArrayList<Ingridiants> ingridiants;

    public IngredientsAdapter(Context context, ArrayList<Ingridiants>ingridiants) {
        this.context = context;
        this.ingridiants = ingridiants;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate your row layout
        View view = LayoutInflater.from(context).inflate(R.layout.custom_lv_ingridiants, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingridiants ingridiants1=this.ingridiants.get(position);
        holder.amountText.setText(ingridiants1.getTypeAmount());
        holder.nameText.setText(ingridiants1.getName());
    }

    @Override
    public int getItemCount() {
        return ingridiants.size();
    }

    // ViewHolder inner class
    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView amountText;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.ingrediantNamee);
            amountText = itemView.findViewById(R.id.ingrediantAmount);
        }
    }
}


