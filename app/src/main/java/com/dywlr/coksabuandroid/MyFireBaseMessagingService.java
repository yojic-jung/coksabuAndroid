package com.dywlr.coksabuandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    public static int badgeCount=0;

    @Override
    public void onNewToken(String token){

        Log.d("FCM Log", "Refreshed token: "+token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        if(remoteMessage.getData().size() > 0){

            Log.d("FCM Log","message: "+remoteMessage.getData());
            String messageBody = remoteMessage.getData().get("content");
            String messageTitle = remoteMessage.getData().get("title");

            //푸시알림 링크 설정
            String link = remoteMessage.getData().get("link");
            Intent intent = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("url", link);
            intent.putExtras(bundle);


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent , PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Channel ID";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            //배지숫자 1증가
            badgeCount=badgeCount+1;

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setVibrate(new long[]{1000, 1000})
                    .setLights(Color.BLUE,500,2000)
                    .setContentIntent(pendingIntent)
                            .setNumber(badgeCount)
                    ;

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                String channelName = "Channel Name";
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                channel.setShowBadge(true);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(0, notificationBuilder.build());


            ShortcutBadger.applyCount(this, badgeCount);
        }
    }
}
