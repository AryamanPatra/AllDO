package com.example.alldo.ui.elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.alldo.R;
import com.example.alldo.data.SimpleTask;
import com.example.alldo.databinding.ActivityHomeBinding;
import com.example.alldo.databinding.LayoutTaskBinding;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    GLOBAL VARIABLES
    ActivityHomeBinding mainBind;
    Context context;
    TaskFragment taskFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        super.onCreate(savedInstanceState);
        mainBind = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(mainBind.getRoot());

//          when opening app for first time
        if (savedInstanceState==null){
            taskFragment = new TaskFragment();
            replaceFragment(taskFragment);
            mainBind.navDrawerViewHome.setCheckedItem(R.id.nav_draw_home);
        }

//        SharedPrefs to load the theme
        SharedPreferences sharedPreferences = getSharedPreferences("MODE",Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(getString(R.string.sdt),false)){
            if (sharedPreferences.getBoolean(getString(R.string.night), false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }


//        For Opening the Drawer
        mainBind.navMenuButtonHome.setOnClickListener(view -> mainBind.drawerLayoutHome.openDrawer(mainBind.navDrawerViewHome));

//        For selecting inside the drawer
        mainBind.navDrawerViewHome.setNavigationItemSelectedListener(item -> {
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
        });

//        For selecting Items at Bottom Nav Bar
        mainBind.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.simpleTask:
                    taskFragment = new TaskFragment(mainBind);
                    replaceFragment(taskFragment);
                    break;
                case R.id.weatherTask:
                    replaceFragment(new WeatherFragment());
                    break;
            }
            return true;
        });

//        For adding task in clicking FAB
        mainBind.addTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("New Task");
                EditText editText = new EditText(getApplicationContext());
                builder.setView(editText);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        String title = editText.getText().toString();
                        taskFragment.updateTaskList(new SimpleTask(title));
                    }
                });
                builder.create().show();
            }
        });
    }

/*
        UTIL Methods
*/

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