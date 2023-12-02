package com.example.alldo.ui.elements;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.data.models.WeatherTask;

public interface OnWeatherItemClickListener {
    void onItemTaskCheckClick(CheckBox checkBox, WeatherTask weatherTask, LinearLayout ll);
    void onItemTaskBodyClick(WeatherTask weatherTask,LinearLayout ll);
    void onItemTaskMarkImpClick(WeatherTask weatherTask, CheckBox checkBox,LinearLayout ll);
}
