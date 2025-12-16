package com.example.cooktogether;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cooktogether.R;

import java.util.ArrayList;

public class ImagesOnlyAdapter extends RecyclerView.Adapter<ImagesOnlyAdapter.ImageVH> {

    private final Context context;
    private final ArrayList<Uri> images;

    public ImagesOnlyAdapter(Context context, ArrayList<Uri> images) {
        this.context = context;
        this.images = (images != null) ? images : new ArrayList<>();
    }

    @NonNull
    @Override
    public ImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.custom_rv_only_images, parent, false);
        return new ImageVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVH holder, int position) {
        holder.imgItem.setImageURI(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImageVH extends RecyclerView.ViewHolder {
        ImageView imgItem;

        ImageVH(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
        }
    }
}
