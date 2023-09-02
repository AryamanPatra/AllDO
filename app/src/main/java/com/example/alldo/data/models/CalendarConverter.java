package com.example.alldo.data.models;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.Calendar;

public class CalendarConverter {

//    From extracting from DB
    @TypeConverter
    public Calendar stringToCalendar(String value){
        Gson gson = new Gson();

        if(value.equals("null"))
            return null;

        Calendar calendar;
        try{
            calendar = gson.fromJson(value,Calendar.class);
        }catch (Exception e){
            Log.d("Data Model","not able to convert data string to calendar, might wrong format of Json; returning null");
            return null;
        }

        return calendar;
    }

//    To store in DB
    @TypeConverter
    public String calendarToString(Calendar calendar){
        Gson gson = new Gson();
        if (calendar==null)
            return "null";
        else
            return gson.toJson(calendar);
    }
}
