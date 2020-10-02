package com.example.digitalcontracts;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;


public class NotificationService extends FirebaseMessagingService {
    private Intent i = null;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        if (remoteMessage.getData().size() > 0) {
//            Map<String, String> data = remoteMessage.getData();
//            try {
//
//                JSONObject object = new JSONObject(data);
//                String requestDetailId = object.getString("requestDetailId");
//                String type = object.getString("type");
//                String from_user = object.getString("from_user");
//                String to_restaurant = object.getString("to_restaurant");
//                String requestId = object.getString("requestId");
//                String msg = object.getString("msg");
//
//
//                if (type.equals("createBargainRequest")) {
//                    i = new Intent(getApplicationContext(), My_Adds.class);
//                }
//
//                if (type.equals("acceptBargainRequest")) {
//                    i = new Intent(getApplicationContext(), My_Adds.class);
//                }
//
//                if (type.equals("counterOffer")) {
//                    i = new Intent(getApplicationContext(), My_Adds.class);
//                }
//
//                if (type.equals("rejectBargainRequest")) {
//                    i = new Intent(getApplicationContext(), My_Adds.class);
//                }
//
//                if (type.equals("acceptCounterOffer")) {
//                    i = new Intent(getApplicationContext(), My_Adds.class);
//                }
//                else {
//                    i=new Intent(getApplicationContext(),My_Adds.class);
//                }
//
//
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                sendNotification(msg, i, "PCMA Trade");
//
//            } catch (Exception e) {
//            }
//
//        }

        try {
            String title=remoteMessage.getNotification().getTitle();
            String mainmsg=remoteMessage.getNotification().getBody();
            SharedPreferences sharedPreferences=getSharedPreferences("Token",MODE_PRIVATE);
            if(sharedPreferences.getString("Token","11111").matches("11111"))
            {
                i=new Intent(getApplicationContext(), Login_screen.class);
            }
            else
            {
                i=new Intent(getApplicationContext(), MainDisplay.class);
            }
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendNotification(mainmsg, i, title);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void sendNotification(String messageBody, Intent intent, String title) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Uri uri= Uri.parse("android.resource://"+getPackageName()+"/raw/soft_alert.wav");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.soft_alert);

        String channelId = "123";
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(messageBody))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(uri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(uri, att);
                if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        if (notificationManager != null) {
//            int color = 0x008000;
//            notificationBuilder.setColor(color);
            notificationManager.notify((int) Calendar.getInstance().getTime().getTime() , notificationBuilder.setSmallIcon(R.mipmap.ic_launcher).setColor(Color.WHITE).build());
            //notificationManager.notify(Constants.PUSH_ID / ID of notification /, notificationBuilder.build());
        }



    }
}
