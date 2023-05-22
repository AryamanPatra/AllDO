package com.example.alldo.ui.ui_elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.alldo.R;
import com.example.alldo.databinding.ActivityHomeBinding;
import com.example.alldo.databinding.LayoutTaskBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding mainBind;
    LayoutTaskBinding taskBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
        mainBind = ActivityHomeBinding.inflate(getLayoutInflater());
        taskBind = LayoutTaskBinding.inflate(getLayoutInflater());
        setContentView(mainBind.getRoot());

//        SharedPrefs to load the theme
        SharedPreferences sharedPreferences = getSharedPreferences("MODE",Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(getString(R.string.sdt),false)){
            if (sharedPreferences.getBoolean(getString(R.string.night), false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }



        mainBind.navMenuButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBind.drawerLayoutHome.openDrawer(mainBind.navDrawerViewHome);
            }
        });
        mainBind.navDrawerViewHome.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mainBind.navDrawerViewHome.setCheckedItem(item.getItemId());
                Intent intent = new Intent();
                switch (item.getItemId()){
                    case R.id.nav_draw_home:
                        break;
                    case R.id.nav_draw_settings:
                        intent.setClass(context,SettingsActivity.class);
                        intent.putExtra(getString(R.string.fragLoadOpt),0);
                        startActivity(intent);
                        break;
                    case R.id.nav_draw_info:
                        intent.setClass(context,SettingsActivity.class);
                        intent.putExtra(getString(R.string.fragLoadOpt),1);
                        startActivity(intent);
                        break;
                }
                mainBind.drawerLayoutHome.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        if (savedInstanceState==null){
            replaceFragment(new TaskFragment());
            mainBind.navDrawerViewHome.setCheckedItem(R.id.nav_draw_home);
        }

        mainBind.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
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
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    protected void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentFrameLoaderHome,fragment);
        fragmentTransaction.commit();
    }

}