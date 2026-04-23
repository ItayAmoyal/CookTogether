package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAllChats;
import static com.example.cooktogether.FBRef.refAuth;
import static com.example.cooktogether.FBRef.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllChatsActivity extends AppCompatActivity {
    RecyclerView rvAllChats;
    ImageButton btnBack;
    FirebaseAuth refAuth;
    FirebaseUser user;
    ArrayList<ChatShow>showChats=new ArrayList<>() ;
    String otherUid="";
    String otherName="";
    ArrayList<Chat> chatList=new ArrayList<>();
    User user1=new User(),user2=new User();
    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chats);
        refAuth = FirebaseAuth.getInstance();
        user = refAuth.getCurrentUser();
        rvAllChats = findViewById(R.id.recyclerAllChats);
        btnBack = findViewById(R.id.backButton);
        chatAdapter = new ChatAdapter(this, showChats);
        rvAllChats.setLayoutManager(new LinearLayoutManager(this));
        rvAllChats.setAdapter(chatAdapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllChatsActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
        if (showChats != null) {
            chatAdapter.setOnChatClickListener(position -> {
                Chat chat = showChats.get(position).getChat();
                getUsers(chat);
            });
            refAllChats.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    chatList.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Chat chat = data.getValue(Chat.class);
                        if (chat.getUser2Uid().equals(user.getUid()) || chat.getUser1Uid().equals(user.getUid())) {
                            chatList.add(chat);
                        }
                    }
                    showChats.clear();
                   getOtherName();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public  void getUsers(Chat chat){
        refUsers.child(chat.getUser1Uid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                        user1 = snapshot.getValue(User.class);
                }
                refUsers.child(chat.getUser2Uid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            user2 = snapshot.getValue(User.class);
                            Intent intent=new Intent(AllChatsActivity.this,ChatsActivity.class);
                            intent.putExtra("user1", user1);
                            intent.putExtra("user2", user2);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getOtherName(){
        int[]loaded={0};
        for(Chat chat: chatList) {
            otherName = "";
            otherUid = "";
            if (chat.getUser2Uid().equals(user.getUid())) {
                otherUid =chat.getUser1Uid();
            } else {
                otherUid = chat.getUser2Uid();
            }
            String finalOtherUid = otherUid;
            refUsers.child(otherUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        User user =snapshot.getValue(User.class);
                            otherName = user.getName();
                            loaded[0]++;
                            showChats.add(new ChatShow(chat,otherName));
                            if(loaded[0]==chatList.size()){
                                chatAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}