package com.example.framgia.alarmclock.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.model.Alarm;

public class NotificationUtils {
    public static void showNotification(Context context, Alarm alarm) {
        NotificationManager notificationManager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.bell_ring)
            .setContentTitle(alarm.getNote())
            .setStyle(new NotificationCompat.BigTextStyle().bigText(alarm.getNote()))
            .setPriority(Notification.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentText(alarm.getFormattedTime());
        notificationManager.cancel(alarm.getId());
        notificationManager.notify(alarm.getId(), builder.build());
    }
}