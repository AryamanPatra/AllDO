package com.example.alldo.ui.elements;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.alldo.R;
import com.example.alldo.data.models.SimpleTask;
import com.google.gson.Gson;

public class ReminderReceiver extends BroadcastReceiver {
    public static final String CHANNEL_NAME = "Task Reminder";
    public static final String NOTIFICATION_CHANNEL_ID = "1101";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;

//        getting task from intent
        Gson gson = new Gson();
        String json = intent.getStringExtra(NOTIFICATION);
        SimpleTask task = gson.fromJson(json, SimpleTask.class);
        String title = task.getTitle();
        String text = task.getDetails()==null || task.getDetails().equals("") ? "No description provided": task.getDetails();

//        creating notification
        notification = new NotificationCompat.Builder(context.getApplicationContext(),ReminderReceiver.NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.pencil_20)
                .setColor(context.getColor(R.color.c1))
                .setChannelId(ReminderReceiver.NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

//        setting noti_channel
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,CHANNEL_NAME,importance);
        assert notificationManager != null;
        notificationChannel.setDescription("For showing reminders for tasks");
        notificationManager.createNotificationChannel(notificationChannel);

//        setting notification
        int id = intent.getIntExtra(NOTIFICATION_ID,0);
        notificationManager.notify(id,notification);

    }
}
