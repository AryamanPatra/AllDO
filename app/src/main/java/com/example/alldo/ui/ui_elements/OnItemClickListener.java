package com.example.alldo.ui.ui_elements;

import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.alldo.data.SimpleTask;

public interface OnItemClickListener {
    void onItemTaskClick(CheckBox checkBox, TextView textView);
}
