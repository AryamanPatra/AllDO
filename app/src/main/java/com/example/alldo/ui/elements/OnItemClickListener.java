package com.example.alldo.ui.elements;

import android.widget.CheckBox;
import android.widget.TextView;

import com.example.alldo.data.models.SimpleTask;

public interface OnItemClickListener {
    void onItemTaskCheckClick(CheckBox checkBox, SimpleTask simpleTask, TextView textView);
    void onItemTaskBodyClick(SimpleTask simpleTask,TextView textView);
    void onItemTaskMarkImpClick(CheckBox checkBox, TextView textView);
}
