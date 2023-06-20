package com.example.alldo.ui.states;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.alldo.data.SimpleTask;
import java.util.List;

public class TaskViewModel extends ViewModel {
    MutableLiveData<List<SimpleTask>> liveDataSet;
}
