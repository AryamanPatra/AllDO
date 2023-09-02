package com.example.alldo.data.repositories;

import android.database.Observable;

import androidx.annotation.OptIn;

import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.data.sources.local.SimpleTaskDao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.DelicateCoroutinesApi;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class SimpleTaskRepositories {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final CoroutineDispatcher dispatcher = Dispatchers.getDefault();
    private final SimpleTaskDao localDataSource;

    @Inject
    public SimpleTaskRepositories(SimpleTaskDao localDataSource){
        this.localDataSource = localDataSource;
    }

//    to observe all data from local database
    public Flowable<List<SimpleTask>> observeAll(){
        return localDataSource.observeAll();
    }

//    create and add new SimpleTask to local database
    public Future<Completable> createTask(String title, String description){
        Callable<Completable> task = new Callable<Completable>() {
            @Override
            public Completable call() throws Exception {
                // Your background task code
                String taskId = createSimpleTaskID();
                SimpleTask simpleTask = new SimpleTask(taskId,title,description);
                Completable observer = localDataSource.upsert(simpleTask);
                return observer;
            }
        };


        return executor.submit(task);
    }

    public void refresh(){
//        new Thread(){
//
//        }.start();

//        Gonna leave this method alone because I haven't implemented the network           side of it
    }

    public void shutdownExecutor() {
        executor.shutdown();
    }

    private String createSimpleTaskID(){
        return UUID.randomUUID().toString();
    }
}
