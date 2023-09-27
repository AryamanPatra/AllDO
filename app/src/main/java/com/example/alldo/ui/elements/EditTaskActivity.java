package com.example.alldo.ui.elements;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.alldo.R;
import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.databinding.ActivityEditTaskBinding;
import com.example.alldo.ui.states.SimpleTaskViewModel;

import java.util.List;
import java.util.Objects;

public class EditTaskActivity extends AppCompatActivity {

    ActivityEditTaskBinding binding;
    private SimpleTaskViewModel simpleTaskViewModel;
    private SimpleTask chosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

//        getting chosenID from intent
        Intent intent = getIntent();
        long chosenId = intent.getLongExtra("chosenTaskID",0);

//        retrieving chosenTask
        simpleTaskViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(SimpleTaskViewModel.class);
        simpleTaskViewModel.getAllData().observe(this, new Observer<List<SimpleTask>>() {
            @Override
            public void onChanged(List<SimpleTask> simpleTasks) {
                for(SimpleTask task : simpleTasks){
                    if(task.getId()==chosenId){
                        chosenTask = task;
                        binding.editTaskTitle.setText(chosenTask.getTitle());
                        if(chosenTask.getDetails()!=null)
                            binding.editTaskDesc.setText(chosenTask.getDetails());
                    }
                }
            }
        });

        binding.editTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                chosenTask.setTitle(editable.toString());
            }
        });
        binding.editTaskDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                chosenTask.setDetails(editable.toString());
            }
        });

        binding.editTaskSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleTaskViewModel.update(chosenTask);
                finish();
            }
        });
    }
}