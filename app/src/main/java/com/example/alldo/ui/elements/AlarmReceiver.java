package com.example.alldo.ui.elements;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.alldo.R;
import com.example.alldo.data.models.SimpleTask;
import com.google.gson.Gson;

public class AlarmReceiver extends BroadcastReceiver {
    public final static String CHANNEL_ID = "Position";
    public final static String TASK_ID = "Task";

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskJson = intent.getStringExtra(TASK_ID);
        final int NOTIFICATION_ID = intent.getIntExtra(CHANNEL_ID,0);
        Gson gson = new Gson();
        SimpleTask task = gson.fromJson(taskJson,SimpleTask.class);
        simpleTaskNotify(context,task,NOTIFICATION_ID);
        Log.d("Notification","Notification should be showing by now");
    }

    private void simpleTaskNotify(Context context, SimpleTask simpleTask,final int NOTIFICATION_ID) {

        Intent notificationIntent = new Intent(context, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        String details;
        if(simpleTask.getDetails()==null || simpleTask.getDetails().equals("")){
            details = "No details";
        }
        else{
            details = simpleTask.getDetails();
        }
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.pencil_20)
                .setContentTitle(simpleTask.getTitle())
                .setContentText(details)
                .setContentIntent(contentIntent)
                .setColor(context.getColor(R.color.c1))
                .build();

        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Notify me", NotificationManager.IMPORTANCE_HIGH);
        mChannel.setDescription("1st channel");
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mChannel.setShowBadge(false);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(mChannel);
        manager.notify(NOTIFICATION_ID, notification);

    }
}