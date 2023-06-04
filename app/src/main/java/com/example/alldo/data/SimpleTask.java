package com.example.alldo.data;

import android.view.View;

import java.time.LocalDateTime;

public class SimpleTask {
    private String title;
    private String details;
    private LocalDateTime alarm;
    private int repeat;
    private boolean check;

    SimpleTask(){}
    public SimpleTask(String input){
        setTitle(input);
        setAlarm(null);
        setRepeat(0);
        setCheck(false);
    }
    SimpleTask(String title, LocalDateTime alarm, int repeat, boolean check){
        setTitle(title);
        setAlarm(alarm);
        setRepeat(repeat);
        setCheck(check);
    }


    //    getter and setters
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
    public int getRepeat() {
        return repeat;
    }
    
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getAlarm() {
        return alarm;
    }

    public void setAlarm(LocalDateTime alarm) {
        this.alarm = alarm;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
    }
}
