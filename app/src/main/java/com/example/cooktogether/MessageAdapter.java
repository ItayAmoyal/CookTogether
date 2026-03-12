package com.example.cooktogether;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    ArrayList<Message> messageList;
    String sentUid;

    public MessageAdapter(ArrayList<Message> messageList,String sentUid){
        this.messageList=messageList;
        this.sentUid=sentUid;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message,parent,false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Message message=messageList.get(position);

        holder.txtMessage.setText(message.getText());
        holder.txtTime.setText(message.getTime());

        if(message.getSentMessage().equals(sentUid)){

            holder.layoutMessage.setGravity(Gravity.START);
            holder.cardMessage.setCardBackgroundColor(Color.parseColor("#DCF8C6"));

        }else{

            holder.layoutMessage.setGravity(Gravity.END);
            holder.cardMessage.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView txtMessage,txtTime;
        LinearLayout layoutMessage;
        CardView cardMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMessage=itemView.findViewById(R.id.txtMessage);
            layoutMessage=itemView.findViewById(R.id.layoutMessage);
            cardMessage=itemView.findViewById(R.id.cardMessage);
            txtTime=itemView.findViewById(R.id.txtTime);
        }
    }
}
