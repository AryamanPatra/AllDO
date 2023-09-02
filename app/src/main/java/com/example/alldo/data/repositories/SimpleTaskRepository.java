package com.example.alldo.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.data.sources.local.AllDoDatabase;
import com.example.alldo.data.sources.local.SimpleTaskDao;

import java.util.List;
import java.util.UUID;

public class SimpleTaskRepository {
    private final SimpleTaskDao simpleTaskDao;
    private final LiveData<List<SimpleTask>> simpleTaskList;

    public SimpleTaskRepository(Application application){
        AllDoDatabase database =  AllDoDatabase.getInstance(application);
        simpleTaskDao = database.simpleTaskDao();
        simpleTaskList = simpleTaskDao.getAllData();
    }

    public void insertData(SimpleTask simpleTask){new InsertTask(simpleTaskDao).execute(simpleTask);}
    public void updateData(SimpleTask simpleTask){new UpdateTask(simpleTaskDao).execute(simpleTask);}
    public void deleteData(SimpleTask simpleTask){new DeleteTask(simpleTaskDao).execute(simpleTask);}
    public LiveData<List<SimpleTask>> getAllData(){
        return simpleTaskList;
    }

    private static class InsertTask extends AsyncTask<SimpleTask,Void,Void>{
        private SimpleTaskDao simpleTaskDao;

        public InsertTask(SimpleTaskDao simpleTaskDao){
            this.simpleTaskDao = simpleTaskDao;
        }

        @Override
        protected Void doInBackground(SimpleTask... simpleTasks) {
            simpleTaskDao.insert(simpleTasks[0]);
            return null;
        }
    }
    private static class UpdateTask extends AsyncTask<SimpleTask,Void,Void>{
        private SimpleTaskDao simpleTaskDao;

        public UpdateTask(SimpleTaskDao simpleTaskDao){
            this.simpleTaskDao = simpleTaskDao;
        }

        @Override
        protected Void doInBackground(SimpleTask... simpleTasks) {
            simpleTaskDao.update(simpleTasks[0]);
            return null;
        }
    }
    private static class DeleteTask extends AsyncTask<SimpleTask,Void,Void>{
        private SimpleTaskDao simpleTaskDao;

        public DeleteTask(SimpleTaskDao simpleTaskDao){
            this.simpleTaskDao = simpleTaskDao;
        }

        @Override
        protected Void doInBackground(SimpleTask... simpleTasks) {
            simpleTaskDao.delete(simpleTasks[0]);
            return null;
        }
    }

    private String createSimpleTaskID(){
        return UUID.randomUUID().toString();
    }
}
