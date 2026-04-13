package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAllChats;
import static com.example.cooktogether.FBRef.refAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatsActivity extends AppCompatActivity {
    Button btnSend;
    ImageButton btnBack;
    EditText textSend;
    Boolean chatExits=false;
    User currentUser, otherUser;
    RecyclerView rvChat;
    ArrayList<Message>allMessages=new ArrayList<>();
    MessageAdapter messageAdapter;
    Chat currentChat=new Chat();
    User user1=new User(),user2=new User();
    FirebaseUser user=refAuth.getCurrentUser();
    TextView tvChat;
    ValueEventListener chatListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        btnSend=findViewById(R.id.btnSend);
        tvChat=findViewById(R.id.txtChatWith);
        rvChat=findViewById(R.id.rvChats);
        btnBack=findViewById(R.id.backButton);
        textSend=findViewById(R.id.editMessage);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        user1=(User) getIntent().getSerializableExtra("user1", User.class);
        user2=(User) getIntent().getSerializableExtra("user2", User.class);
        if(user1.getUid().equals(user.getUid())){
            currentUser=user1;
            otherUser=user2;
            tvChat.setText(user2.getName());
        }
        else{
            currentUser=user2;
            otherUser=user1;
            tvChat.setText(user1.getName());
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsActivity.this, AllChatsActivity.class);
                startActivity(intent);
            }
        });
        messageAdapter=new MessageAdapter(allMessages,currentUser.getUid());
        rvChat.setAdapter(messageAdapter);
        refAllChats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    Chat chat=data.getValue(Chat.class);
                     if((chat.getUser1Uid().equals(user1.getUid())&&chat.getUser2Uid().equals(user2.getUid()))||
                            (chat.getUser1Uid().equals(user2.getUid())&&chat.getUser2Uid().equals(user1.getUid())))
                    {
                        currentChat=chat;
                        chatExits=true;
                    }
                }
                if(chatExits==false){
                    allMessages.clear();
                    currentChat.setUser1Uid(user1.getUid());
                    currentChat.setUser2Uid(user2.getUid());
                    currentChat.setChatId(refAllChats.push().getKey());
                    currentChat.setAllMessages(allMessages);
                    refAllChats.child(currentChat.getChatId())
                            .setValue(currentChat)
                            .addOnSuccessListener(unused -> startChatListener());
                }
                if(chatExits==true){
                    allMessages.clear();
                    if(currentChat.getAllMessages()!=null){
                        allMessages.addAll(currentChat.getAllMessages());
                    }
                    messageAdapter.notifyDataSetChanged();
                    if(!allMessages.isEmpty()){
                    rvChat.scrollToPosition(allMessages.size() - 1);
                    }
                    startChatListener();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(textSend.getText().toString().equals(""))) {
                    String time = new SimpleDateFormat("dd/MM HH:mm").format(new Date());
                    Message message = new Message(textSend.getText().toString(), currentUser.getUid(),time);
                    textSend.setText("");
                    currentChat.AddMessage(message);
                    refAllChats.child(currentChat.getChatId()).setValue(currentChat);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        if(chatListener!=null){
            refAllChats.removeEventListener(chatListener);
        }
        super.onDestroy();
    }
    public void startChatListener(){

        chatListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Chat chat = snapshot.getValue(Chat.class);

                if(chat.getAllMessages()!=null){

                    allMessages.clear();
                    allMessages.addAll(chat.getAllMessages());

                    messageAdapter.notifyDataSetChanged();

                    if(!allMessages.isEmpty()){
                        rvChat.scrollToPosition(allMessages.size()-1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        refAllChats.child(currentChat.getChatId()).addValueEventListener(chatListener);
    }
}