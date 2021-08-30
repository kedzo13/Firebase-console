package com.karlo.zavrsni;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MojFBCMservis extends FirebaseMessagingService {

public static final String TAG =MojFBCMservis.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Token"+token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Notifikacija: "+ remoteMessage.getFrom());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            kanal();
        }

        if(remoteMessage.getNotification()!=null){
            String naslov = remoteMessage.getNotification().getTitle();
            String tijelo = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Naslov notifikacije: "+ naslov + "Tijelo notifikacije: "+ tijelo);
            prikazi(naslov,tijelo);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void kanal (){
        NotificationChannel channel = new NotificationChannel("idKanal", "Notifikacijski kanal", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    private void prikazi(String naslov, String tijelo){
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"idKanal")
                .setContentTitle(naslov)
                .setContentText(tijelo)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        manager.notify(1, builder.build());
    }
}
