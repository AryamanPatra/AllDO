package com.example.alldo.data.models;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;

@Entity(
        tableName = "simpleTask"
)
final public class SimpleTask {
    @PrimaryKey (autoGenerate = true)
    private long id;
    private String title;
    private String details;
    @TypeConverters(CalendarConverter.class)
    private Calendar alarm;
    private int repeat;
    private boolean check;
    private boolean markImp;


    public SimpleTask(){}

    public SimpleTask(String input){
        setTitle(input);
        setDetails("");
        setAlarm(null);
        setRepeat(0);
        setCheck(false);
        setMarkImp(false);
    }

    public SimpleTask(String title, String details, Calendar alarm, int repeat, boolean check, boolean markImp){
        setTitle(title);
        setDetails(details);
        setAlarm(alarm);
        setRepeat(repeat);
        setCheck(check);
        setMarkImp(markImp);
    }

    public SimpleTask(String title, String desc) {
        setTitle(title);
        setDetails(desc);
    }


    //    getter and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getAlarm() {
        return alarm;
    }

    public void setAlarm(Calendar alarm) {
        this.alarm = alarm;
    }
    public boolean isMarkImp() {
        return markImp;
    }

    public void setMarkImp(boolean markImp) {
        this.markImp = markImp;
    }
}
