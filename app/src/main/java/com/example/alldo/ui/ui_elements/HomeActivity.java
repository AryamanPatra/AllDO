package com.example.alldo.ui.ui_elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
        super.onCreate(savedInstanceState);
        mainBind = ActivityHomeBinding.inflate(getLayoutInflater());
        taskBind = LayoutTaskBinding.inflate(getLayoutInflater());
        setContentView(mainBind.getRoot());

        mainBind.navMenuButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBind.drawerLayout.openDrawer(mainBind.navDrawerView);
            }
        });

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrameLoader,new TaskFragment()).commit();
            mainBind.navDrawerView.setCheckedItem(R.id.nav_draw_home);
        }

        replaceFragment(new TaskFragment());
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    protected void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentFrameLoader,fragment);
        fragmentTransaction.commit();
    }

}