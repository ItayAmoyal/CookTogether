package com.example.cooktogether;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InstructionCreateAdapter extends RecyclerView.Adapter<InstructionCreateAdapter.InstructionVH> {

    public interface OnInstructionDeleteListener {
        void onInstructionDelete(int position);
    }

    private final Context context;
    private final ArrayList<String> instructionsText;
    private final ArrayList<ArrayList<Uri>> instructionImages;
    private final OnInstructionDeleteListener deleteListener;

    public InstructionCreateAdapter(Context context,
                                   ArrayList<String> instructionsText,
                                   ArrayList<ArrayList<Uri>> instructionImages,
                                   OnInstructionDeleteListener deleteListener) {
        this.context = context;
        this.instructionsText = (instructionsText != null) ? instructionsText : new ArrayList<>();
        this.instructionImages = (instructionImages != null) ? instructionImages : new ArrayList<>();
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public InstructionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.custom_rv_createinstructions, parent, false);
        return new InstructionVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionVH holder, int position) {
        holder.tvInstruction.setText(instructionsText.get(position));

        holder.rvImages.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        );
        holder.rvImages.setAdapter(
                new ImagesOnlyAdapter(context, instructionImages.get(position))
        );

        holder.btnDeleteInstruction.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            instructionsText.remove(pos);
            instructionImages.remove(pos);
            notifyItemRemoved(pos);

            if (deleteListener != null) deleteListener.onInstructionDelete(pos);
        });
    }

    @Override
    public int getItemCount() {
        return instructionsText.size();
    }

    static class InstructionVH extends RecyclerView.ViewHolder {
        TextView tvInstruction;
        ImageButton btnDeleteInstruction;
        RecyclerView rvImages;

        InstructionVH(@NonNull View itemView) {
            super(itemView);
            tvInstruction = itemView.findViewById(R.id.tvInstruction);
            btnDeleteInstruction = itemView.findViewById(R.id.btnDeleteInstruction);
            rvImages = itemView.findViewById(R.id.rvInstructionImages);
        }
    }
}



