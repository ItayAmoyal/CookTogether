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
    private ArrayList<InstructionItem> instructionList;
    private ImagesOnlyAdapter imagesOnlyAdapter;
    int size=0;


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
        size=0;
        holder.textInstruction.setText(item.getInstructionText());
        ArrayList<String> images = item.getFileName();
        ArrayList<Bitmap> allImagesBitMap=new ArrayList<>();
        if(images != null) {
            for (String documentID : images) {
                DocumentReference docRef = refImages.document(documentID);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Blob blob = documentSnapshot.getBlob("ImageData");
                            if (blob != null) {
                                Log.d("TEST_FIRESTORE", "loading image1111: ");
                                byte[] bytes = blob.toBytes();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                allImagesBitMap.add(bitmap);
                                size++;
                                if(size==images.size()){
                                    holder.rvImageInstructions.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                                    imagesOnlyAdapter = new ImagesOnlyAdapter(context, allImagesBitMap);
                                    holder.rvImageInstructions.setAdapter(imagesOnlyAdapter);
                                }
                            }
                        }
                    }
                });
            }
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
        RecyclerView rvImageInstructions;

        public InstructionViewHolder(@NonNull View itemView) {
            super(itemView);

            textInstruction = itemView.findViewById(R.id.textInstruction);
            rvImageInstructions = itemView.findViewById(R.id.imageInstruction1);
        }
    }
}

