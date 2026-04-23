package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAuth;
import static com.example.cooktogether.FBRef.refUsers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private ArrayList<ChatShow> chatList;
    private Context context;
    private ChatAdapter.OnChatClickListener listener;
    public ChatAdapter(Context context, ArrayList<ChatShow> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_chat, parent, false);

        return new ChatViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.txtName.setText("...");
        FirebaseUser user=refAuth.getCurrentUser();
        Chat chat = chatList.get(position).getChat();
        String otherUid;

                     holder.txtName.setText(chatList.get(position).getOtherName());
                        if(chat.getAllMessages() != null && !chat.getAllMessages().isEmpty()) {
                            Message lastMessage = chat.getAllMessages().get(chat.getAllMessages().size() - 1);
                            String text = lastMessage.getText();
                            String time=lastMessage.getTime();
                            holder.txtTime.setText(time);
                            holder.txtLastMessage.setText(text);
                        }
                    }

    public interface OnChatClickListener {
        void onChatClick(int position);
    }
    public void setOnChatClickListener(ChatAdapter.OnChatClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtLastMessage;
        TextView txtTime;


        public ChatViewHolder(@NonNull View itemView,OnChatClickListener listener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtLastMessage = itemView.findViewById(R.id.txtLastMessage);
            txtTime = itemView.findViewById(R.id.txtTime);
            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onChatClick(getAdapterPosition());
                }
            });
        }
    }
}
