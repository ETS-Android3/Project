package com.example.medicationreminderapplication;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

public class NotificationIntentService extends Service {
    private static final int NOTIFICATION_ID = 2;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        DoNotify();
        return Service.START_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void DoNotify() {
        NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(this, "My Notification");
        builder.setContentTitle("MedicationReminder");
        builder.setContentText("You have Medication(s) to take");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        Intent notifyIntent = new Intent(this, loginNotification.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
