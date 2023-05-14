package com.example.alldo.data;

import java.time.LocalDateTime;

public class Task {
    private String taskText;
    private LocalDateTime alarm;
    private int repeat;


    private boolean check;

    Task(){}
    Task(String input){
        setTaskText(input);
        setAlarm(null);
        setRepeat(0);
        setCheck(false);
    }
    Task(String taskText,LocalDateTime alarm,int repeat,boolean check){
        setTaskText(taskText);
        setAlarm(alarm);
        setRepeat(repeat);
        setCheck(check);
    }


    //    getter and setters
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

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public LocalDateTime getAlarm() {
        return alarm;
    }

    public void setAlarm(LocalDateTime alarm) {
        this.alarm = alarm;
    }
}
