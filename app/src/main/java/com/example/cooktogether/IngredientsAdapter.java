package com.example.cooktogether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private
    Context context;
    private String[] nameList;
    private String[] amountList;

    public IngredientsAdapter(Context context, String[] nameList, String[] amountList) {
        this.context = context;
        this.nameList = nameList;
        this.amountList = amountList;
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
        String name = nameList[position];
        String amount = amountList[position];

        holder.nameText.setText(name);
        holder.amountText.setText(amount);
    }

    @Override
    public int getItemCount() {
        return nameList.length;
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


