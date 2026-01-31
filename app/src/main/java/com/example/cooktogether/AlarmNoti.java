package com.example.cooktogether;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmNoti extends BroadcastReceiver {
    private static final String CHANNEL_ID="noti_ID";
    private static final String CHANNEL_NAME="noti_NAME";
    NotificationManager notificationManager;
    NotificationChannel channel;
    private static final int NOTIFICATION_ID=1;
    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Intent openIntent = new Intent(context, CreateRecipe.class);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent=PendingIntent.getActivity(context,1,openIntent,flags);
        NotificationCompat.Builder notiBuilder=new NotificationCompat.Builder(context,CHANNEL_ID).setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("CookTogether").setContentText( "צור מתכונים ושתף אותם באפליקציה!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).addAction(android.R.drawable.ic_menu_close_clear_cancel,"צור מתכון",pendingIntent).setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID,notiBuilder.build());
    }
}