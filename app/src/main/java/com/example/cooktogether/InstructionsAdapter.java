package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refImages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder> {

    private Context context;
    private ArrayList<InstructionsShow> instructionList;
    private ImagesOnlyAdapter imagesOnlyAdapter;

    public InstructionsAdapter(Context context, ArrayList<InstructionsShow> instructionList) {
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
        int currentPosition = holder.getAdapterPosition();
        if (currentPosition == RecyclerView.NO_POSITION) return;
        InstructionItem item = instructionList.get(position).getInstructionItem();
        holder.textInstruction.setText(item.getInstructionText());
        imagesOnlyAdapter = new ImagesOnlyAdapter(context, instructionList.get(position).getBitmaps());
        holder.rvImageInstructions.setAdapter(imagesOnlyAdapter);
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }


    public static class InstructionViewHolder extends RecyclerView.ViewHolder {

        TextView textInstruction;
        RecyclerView rvImageInstructions;

        public InstructionViewHolder(@NonNull View itemView) {
            super(itemView);

            textInstruction = itemView.findViewById(R.id.textInstruction);
            rvImageInstructions = itemView.findViewById(R.id.imageInstruction1);

            rvImageInstructions.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));
        }
    }
}

