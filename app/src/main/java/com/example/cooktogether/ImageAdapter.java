package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refImages;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.BitSet;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageVH> {

    public interface OnDeleteListener {
        void onDelete(int position, Bitmap bitmap);
    }

    private Context context;
    private ArrayList<Bitmap> images;
    private ArrayList<String> imagesID;
    private OnDeleteListener deleteListener;

    public ImageAdapter(Context context,
                        ArrayList<Bitmap> images, ArrayList<String> imagesID,
                        OnDeleteListener deleteListener) {
        this.context = context;
        this.images = (images != null) ? images : new ArrayList<>();
        this.imagesID = (images != null) ? imagesID : new ArrayList<>();
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_rv_imageinstructions, parent, false);
        return new ImageVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVH holder, int position) {
        Bitmap bitmap = images.get(position);
        String fileName=imagesID.get(position);

        holder.imgItem.setImageBitmap(bitmap);


        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION)
                return;
            if (pos < 0 || pos >= images.size())
                return;

            Bitmap removed = images.remove(pos);
            notifyItemRemoved(pos);

            if (deleteListener != null) {
                DocumentReference docRef=refImages.document(fileName);
                docRef.delete();

                deleteListener.onDelete(pos, removed);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addImage(Bitmap bitmap,String imageID) {
        if (bitmap == null) return;
        images.add(bitmap);
        if (imageID == null) return;
        imagesID.add(imageID);
        notifyItemInserted(imagesID.size() - 1);
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    static class ImageVH extends RecyclerView.ViewHolder {
        ImageView imgItem;
        ImageButton btnDelete;

        ImageVH(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}



