package com.example.alldo.ui.elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.alldo.R;
import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    GLOBAL VARIABLES
    ActivityHomeBinding binding;
    Context context;
    TaskFragment taskFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//          when opening app for first time

            taskFragment = new TaskFragment();
            replaceFragment(taskFragment);
            binding.navDrawerViewHome.setCheckedItem(R.id.nav_draw_home);


//        SharedPrefs to load the theme

        SharedPreferences sharedPreferences = getSharedPreferences("MODE",Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(getString(R.string.sdt),false)){
            if (sharedPreferences.getBoolean(getString(R.string.night), false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }


//        For Opening the Drawer
        binding.navMenuButtonHome.setOnClickListener(view -> binding.drawerLayoutHome.openDrawer(binding.navDrawerViewHome));

//        For selecting inside the drawer
        binding.navDrawerViewHome.setNavigationItemSelectedListener(item -> {
            binding.navDrawerViewHome.setCheckedItem(item.getItemId());
            Intent intent = new Intent();
            switch (item.getItemId()){
                case R.id.nav_draw_home:
                    break;
                case R.id.nav_draw_settings:
                    intent.setClass(context,SettingsActivity.class);
                    intent.putExtra(getString(R.string.fragLoadOpt),0);
                    startActivity(intent);
                    break;
                case R.id.nav_draw_info:
                    intent.setClass(context,SettingsActivity.class);
                    intent.putExtra(getString(R.string.fragLoadOpt),1);
                    startActivity(intent);
                    break;
            }
            binding.drawerLayoutHome.closeDrawer(GravityCompat.START);
            return true;
        });

//        For selecting Items at Bottom Nav Bar
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.simpleTask:
                    taskFragment = new TaskFragment(binding);
                    replaceFragment(taskFragment);
                    break;
                case R.id.weatherTask:
                    replaceFragment(new WeatherFragment());
                    break;
            }
            return true;
        });

//        For adding task in clicking FAB
        binding.addTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog addTaskDialog = new Dialog(context);
                addTaskDialog.setContentView(R.layout.add_task_dialog);
                addTaskDialog.show();
                Button cancel = addTaskDialog.findViewById(R.id.btnCancelAddTask);
                Button add = addTaskDialog.findViewById(R.id.btnAddAddTask);
                EditText addTaskDate = addTaskDialog.findViewById(R.id.addTaskDate);
                ImageButton datePickerButton = addTaskDialog.findViewById(R.id.addTaskDatePicker);
                Calendar calendar = Calendar.getInstance();
                final boolean[] calendarUsed = {false};
                addTaskDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int[] date = {calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)};
                        DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this,R.style.datePickerCustomStyle,new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date[0]=year;
                                date[1]=month;
                                date[2]=day;
                                calendar.set(Calendar.YEAR,year);
                                calendar.set(Calendar.MONTH,month);
                                calendar.set(Calendar.DAY_OF_MONTH,day);
                                calendarUsed[0] = true;


                                final String[] time = new String[1];
                                Dialog timePickerDialog = new Dialog(context);
                                timePickerDialog.setContentView(R.layout.custom_timepicker_dialog);
                                timePickerDialog.show();
                                TimePicker timePicker = timePickerDialog.findViewById(R.id.timePicker);
                                Button timePickerOk = timePickerDialog.findViewById(R.id.timePickerOkBtn);
                                Button timePickerCancel = timePickerDialog.findViewById(R.id.timePickerCancelBtn);
                                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                                    @Override
                                    public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                                        time[0] = hour+":"+min;
                                        calendar.set(Calendar.HOUR_OF_DAY,hour);
                                        calendar.set(Calendar.MINUTE,min);
                                    }
                                });
                                timePickerOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String display = ""+date[2]+"/"+(date[1]+1)+"/"+date[0]+", "+time[0];
                                        addTaskDate.setText(display);
                                        timePickerDialog.dismiss();
                                    }
                                });
                                timePickerCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        timePickerDialog.dismiss();
                                    }
                                });
                            }
                        },date[0],date[1],date[2]);
                        datePickerDialog.show();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = ((EditText)addTaskDialog.findViewById(R.id.addTaskTitle)).getText().toString();
                        String desc = ((EditText)addTaskDialog.findViewById(R.id.addTaskDescription)).getText().toString();
                        int repeat = Integer.parseInt(((EditText)addTaskDialog.findViewById(R.id.addTaskRepeat)).getText().toString());
                        if(!title.equals("")){
                            if(calendarUsed[0]){
                                SimpleTask task = new SimpleTask(title,desc,calendar,repeat,false,false);
                                taskFragment.updateTaskList(task);

//                                Work from here
                                scheduleNotification(task,calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
//                                to here
                            }
                            else
                                taskFragment.updateTaskList(new SimpleTask(title,desc));
                        }
                        addTaskDialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addTaskDialog.dismiss();
                    }
                });
            }
        });
    }

/*
        UTIL Methods
*/

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    protected void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentFrameLoaderHome,fragment);
        fragmentTransaction.commit();
    }

    private void scheduleNotification (SimpleTask task , long delay) {
        Intent notificationIntent = new Intent( this, ReminderReceiver.class ) ;
        notificationIntent.putExtra(ReminderReceiver.NOTIFICATION_ID , (int)task.getId() ) ;
        Gson gson = new Gson();
        String json = gson.toJson(task);
        notificationIntent.putExtra(ReminderReceiver.NOTIFICATION,json);

        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, (int)task.getId() , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP , delay , pendingIntent) ;
    }

}