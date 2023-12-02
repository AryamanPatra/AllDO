package com.example.alldo.data.models;

import java.util.Calendar;

public final class WeatherCriteria {
    //    fields that are only provided for test case 1
    private float min_temp;
    private float max_temp;
    private Calendar min_time;
    private Calendar max_time;

//    Getters and Setters
    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public Calendar getMin_time() {
        return min_time;
    }

    public void setMin_time(Calendar min_time) {
        this.min_time = min_time;
    }

    public Calendar getMax_time() {
        return max_time;
    }

    public void setMax_time(Calendar max_time) {
        this.max_time = max_time;
    }

}
