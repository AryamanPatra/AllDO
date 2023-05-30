package com.example.alldo.ui.elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.alldo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Thread thread;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] themeStrings = {"Light","Dark","System Default"};
        ListView lv = requireView().findViewById(R.id.themeChangeLV);
        ArrayAdapter<String> ad = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,themeStrings);
        lv.setAdapter(ad);

        sharedPreferences = requireContext().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night",false);

        lv.setOnItemClickListener((adapterView, view1, i, l) -> {
            editor = sharedPreferences.edit();
            switch (i){
                case 0:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean(getString(R.string.night),false);
                    editor.putBoolean(getString(R.string.sdt),false);
                    break;
                case 1:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean(getString(R.string.night),true);
                    editor.putBoolean(getString(R.string.sdt),false);
                    break;
                case 2:
                    editor.putBoolean(getString(R.string.sdt),true);
                    break;
            }
            editor.apply();
        });
    }
}