package com.example.alldo.ui.ui_elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.alldo.R;
import com.example.alldo.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding mainBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBind = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(mainBind.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        replaceFragment(new TaskFragment());
        mainBind.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.simpleTask:
                        replaceFragment(new TaskFragment());
                        break;
                    case R.id.weatherTask:
                        replaceFragment(new WeatherFragment());
                        break;
                }
                return true;
            }
        });
    }

    protected void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentFrameLoader,fragment);
        fragmentTransaction.commit();
    }
}