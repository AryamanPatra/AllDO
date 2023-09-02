package com.example.alldo;


import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static org.junit.Assert.assertEquals;
import android.util.Log;

import androidx.room.Room;
import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.data.sources.local.AllDoDatabase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class SimpleTaskDaoTest {
    private AllDoDatabase database;

    @Before
    public void initDb(){
        database = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                AllDoDatabase.class
        ).allowMainThreadQueries().build();
    }

    @Test
    public void insertTaskAndGetTask(){

        SimpleTask task = new SimpleTask("34","title","description", Calendar.getInstance(),1,false);

        database.simpleTaskDao().upsert(task);

        TestObserver<List<SimpleTask>> testObserver = new TestObserver<>();
        database.simpleTaskDao().upsert(task)
                .andThen(database.simpleTaskDao().observeAll())
                .firstElement()
                .toObservable()
                .subscribe(testObserver);

        // Wait for the testObserver to complete or timeout
        testObserver.awaitCount(1);

        // Assert the emitted values
        testObserver.assertValue(taskList -> taskList.size() == 1);

    }
}
