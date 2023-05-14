package com.example.alldo.ui.ui_elements;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.alldo.databinding.ActivityHomeBinding;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding mainBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBind = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(mainBind.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}