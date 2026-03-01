package com.example.cooktogether;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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

import java.sql.Blob;
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

        ArrayList<String> images = item.getImageUri();

        if (images != null && !images.isEmpty()) {

            Uri uri = Uri.parse(images.get(0));

            Glide.with(context)
                    .load(uri)
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.color.holo_red_dark)
                    .into(holder.imageInstruction);

        } else {
            holder.imageInstruction.setImageResource(android.R.color.darker_gray);
        }
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    public void addInstruction(InstructionItem instruction) {
        instructionList.add(instruction);
        notifyItemInserted(instructionList.size() - 1);
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

