package com.example.alldo.ui.ui_elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.alldo.R;
import com.example.alldo.databinding.ActivitySettingsBinding;
import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ActivitySettingsBinding mainBind;
    int fragLoadSignal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBind = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(mainBind.getRoot());
        fragLoadSignal = getIntent().getIntExtra(getString(R.string.fragLoadOpt),0);
        if(fragLoadSignal==0){
            replaceFragment(new SettingsFragment());
            mainBind.titleSettings.setText(getText(R.string.settings));
        }
        else if(fragLoadSignal==1){
            replaceFragment(new AboutFragment());
            mainBind.titleSettings.setText(getText(R.string.about_us));
        }

        mainBind.navMenuButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBind.drawerLayoutSettings.openDrawer(mainBind.navDrawerViewSettings);
            }
        });

        mainBind.navDrawerViewSettings.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mainBind.navDrawerViewSettings.setCheckedItem(item.getItemId());
                Intent intent = new Intent();
                switch (item.getItemId()){
                    case R.id.nav_draw_home:
                        intent.setClass(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_draw_settings:
                        if(fragLoadSignal==1){
                            replaceFragment(new SettingsFragment());
                            mainBind.titleSettings.setText(getText(R.string.settings));
                            mainBind.navDrawerViewSettings.setCheckedItem(R.id.nav_draw_settings);
                            fragLoadSignal=0;
                        }
                        break;
                    case R.id.nav_draw_info:
                        if(fragLoadSignal==0){
                            replaceFragment(new AboutFragment());
                            mainBind.titleSettings.setText(getText(R.string.about_us));
                            mainBind.navDrawerViewSettings.setCheckedItem(R.id.nav_draw_info);
                            fragLoadSignal=1;
                        }
                        break;
                }
                mainBind.drawerLayoutSettings.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    protected void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentFrameLoaderSettings,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}