package com.example.alldo;


import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static org.junit.Assert.assertEquals;

import androidx.room.Room;
import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.data.sources.local.AllDoDatabase;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import io.reactivex.observers.TestObserver;

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


    }
}
