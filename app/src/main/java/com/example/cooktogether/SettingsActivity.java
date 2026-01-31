package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAuth;
import static com.example.cooktogether.FBRef.refUsers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    TextView name,numOfrecipes,email,password;
    Switch btnAlarm;
    String uid;
    User userCurrent;
    Boolean isActive;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name=findViewById(R.id.tvName);
        email=findViewById(R.id.tvEmail);
        password=findViewById(R.id.tvPassword);
        numOfrecipes=findViewById(R.id.tvRecipesCount);
        btnAlarm=findViewById(R.id.switchReminder);
       FirebaseUser user =refAuth.getCurrentUser();

        uid=user.getUid();
       refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (DataSnapshot data:snapshot.getChildren()){
                  String str=data.getKey();
                  if(uid.equals(str)){
                      userCurrent=data.getValue(User.class);
                  }
              }
              name.setText(userCurrent.getName());
              email.setText(userCurrent.getEmail());
              password.setText(userCurrent.getPass());
              numOfrecipes.setText(Integer.toString(userCurrent.getNumOfRecepies()));
              isActive=userCurrent.getAlarm();
               if(isActive){
                   btnAlarm.setChecked(true);
               }
              flag=1;

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
           btnAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
               if (isChecked&&flag==1) {
                   Toast.makeText(this, "התזכורת הופעלה", Toast.LENGTH_SHORT).show();
                   userCurrent.setAlarm(true);
                   refUsers.child(userCurrent.getUid()).setValue(userCurrent);
                   Calendar calendar = Calendar.getInstance();
                   Intent intent = new Intent(SettingsActivity.this, AlarmNoti.class);
                   alarmIntent = PendingIntent.getBroadcast(SettingsActivity.this, 1, intent, PendingIntent.FLAG_IMMUTABLE);
                   alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                   alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 10 * 1000, alarmIntent);
               }
               else if(flag==1) {
                   if (alarmManager != null) {
                       alarmManager.cancel(alarmIntent);
                   }
                   userCurrent.setAlarm(false);
                   refUsers.child(userCurrent.getUid()).setValue(userCurrent);
                   Toast.makeText(this, "התזכורת בוטלה", Toast.LENGTH_SHORT).show();
               }
           });
       }
    }
