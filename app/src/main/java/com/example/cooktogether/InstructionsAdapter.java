package com.example.cooktogether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder> {

    private Context context;
    private ArrayList<InstructionItem> instructionList;

    public InstructionsAdapter(Context context, ArrayList<InstructionItem> instructionList) {
        this.context = context;
        this.instructionList = instructionList;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_rv_instructions, parent, false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        InstructionItem item = instructionList.get(position);

        holder.textInstruction.setText(item.getInstructionText());

        if (item.getImageUri() != null) {
            Glide.with(context)
                    .load(item.getImageUri())
                    .placeholder(android.R.color.darker_gray)
                    .into(holder.imageInstruction);
        } else {
            holder.imageInstruction.setImageResource(android.R.color.darker_gray);
        }
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    public static class InstructionViewHolder extends RecyclerView.ViewHolder {

        TextView textInstruction;
        ImageView imageInstruction;

        public InstructionViewHolder(@NonNull View itemView) {
            super(itemView);

            textInstruction = itemView.findViewById(R.id.textInstruction);
            imageInstruction = itemView.findViewById(R.id.imageInstruction);
        }
    }
}

