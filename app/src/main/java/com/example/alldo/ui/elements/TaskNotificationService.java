package com.example.alldo.ui.elements;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.system.Os;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.alldo.data.models.SimpleTask;
import com.google.gson.Gson;

import java.util.Calendar;

public class TaskNotificationService extends Service {
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ShortAlarm")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Gson gson = new Gson();
        String taskJson = intent.getStringExtra(AlarmReceiver.TASK_ID);
        int channelID = intent.getIntExtra(AlarmReceiver.CHANNEL_ID,0);

        SimpleTask task = gson.fromJson(taskJson,SimpleTask.class);
        Calendar calendar = task.getAlarm();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this,AlarmReceiver.class);
        alarmIntent.putExtra(AlarmReceiver.TASK_ID,taskJson);
        alarmIntent.putExtra(AlarmReceiver.CHANNEL_ID,channelID);

        // we call broadcast using pendingIntent
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), channelID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long time = calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000);
        if (System.currentTimeMillis() > time) {
            // setting time as AM and PM
            if (calendar.get(Calendar.AM_PM) == Calendar.AM)
                time = time + (1000 * 60 * 60 * 12);
            else
                time = time + (1000 * 60 * 60 * 24);
        }


        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,time,
                pendingIntent);

        Log.d("Notification","Notification onStartCommand() executed");
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
