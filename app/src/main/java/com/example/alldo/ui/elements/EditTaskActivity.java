package com.example.alldo.ui.elements;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.alldo.R;
import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.databinding.ActivityEditTaskBinding;
import com.example.alldo.ui.states.SimpleTaskViewModel;

import java.util.Calendar;
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
                        if(chosenTask.getAlarm()!=null){
                            Calendar cal = chosenTask.getAlarm();
                            String display = cal.get(Calendar.DAY_OF_MONTH)+ "/"
                                            + (cal.get(Calendar.MONTH)+1) + "/"
                                            + cal.get(Calendar.YEAR) + ", "
                                            + cal.get(Calendar.HOUR_OF_DAY) + ":"
                                            + cal.get(Calendar.MINUTE);
                            binding.editTaskDate.setText(display);
                        }
                        binding.editTaskRepeat.setText(String.valueOf(chosenTask.getRepeat()));
                        binding.editTaskCheck.setChecked(chosenTask.isCheck());
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

        binding.editTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final boolean[] calendarUsed = new boolean[1];
                final int[] date = {calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)};
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskActivity.this,R.style.datePickerCustomStyle,new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date[0]=year;
                        date[1]=month;
                        date[2]=day;
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        calendarUsed[0] = true;


                        final String[] time = new String[1];
                        Dialog timePickerDialog = new Dialog(EditTaskActivity.this);
                        timePickerDialog.setContentView(R.layout.custom_timepicker_dialog);
                        timePickerDialog.show();
                        TimePicker timePicker = timePickerDialog.findViewById(R.id.timePicker);
                        Button timePickerOk = timePickerDialog.findViewById(R.id.timePickerOkBtn);
                        Button timePickerCancel = timePickerDialog.findViewById(R.id.timePickerCancelBtn);
                        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                                time[0] = hour+":"+min;
                                calendar.set(Calendar.HOUR_OF_DAY,hour);
                                calendar.set(Calendar.MINUTE,min);
                            }
                        });
                        timePickerOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String display = ""+date[2]+"/"+(date[1]+1)+"/"+date[0]+", "+time[0];
                                binding.editTaskDate.setText(display);
                                timePickerDialog.dismiss();
                            }
                        });
                        timePickerCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                timePickerDialog.dismiss();
                            }
                        });
                    }
                },date[0],date[1],date[2]);
                datePickerDialog.show();

                chosenTask.setAlarm(calendar);
            }
        });

        binding.editTaskRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(""))
                    chosenTask.setRepeat(Integer.parseInt(editable.toString()));
                else
                    chosenTask.setRepeat(0);
            }
        });

        binding.editTaskCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenTask.setCheck(binding.editTaskCheck.isChecked());
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