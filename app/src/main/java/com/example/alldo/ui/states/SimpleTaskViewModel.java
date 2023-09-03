package com.example.alldo.ui.states;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.data.repositories.SimpleTaskRepository;

import java.util.List;

public class SimpleTaskViewModel extends AndroidViewModel {
    private final SimpleTaskRepository simpleTaskRepo;
    private LiveData<List<SimpleTask>> simpleTaskList;

    public SimpleTaskViewModel(@NonNull Application application) {
        super(application);
        simpleTaskRepo = new SimpleTaskRepository(application);
        simpleTaskList= simpleTaskRepo.getAllData();
    }

    public void insert(SimpleTask simpleTask){
        simpleTaskRepo.insertData(simpleTask);
    }
    public void delete(SimpleTask simpleTask){
        simpleTaskRepo.deleteData(simpleTask);
    }
    public void update(SimpleTask simpleTask){
        simpleTaskRepo.updateData(simpleTask);
    }
    public LiveData<List<SimpleTask>> getAllData(){
        return simpleTaskRepo.getAllData();
    }
}
