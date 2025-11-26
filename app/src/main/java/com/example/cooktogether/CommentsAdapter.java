package com.example.cooktogether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private ArrayList<Comments> commentsList;

    public CommentsAdapter(Context context, ArrayList<Comments> commentsList) {
        this.context = context;
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_rv_comments, parent, false);   // <-- your xml name
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comments item = commentsList.get(position);

        holder.textWhoComment.setText(item.getWhoCommentUid());
        holder.textComment.setText(item.getComment());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    // ViewHolder class
    static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView textWhoComment;
        TextView textComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textWhoComment = itemView.findViewById(R.id.textWhoComment);
            textComment = itemView.findViewById(R.id.textComment);
        }
    }
}

