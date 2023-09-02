package com.example.alldo.data.sources.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.alldo.data.models.SimpleTask;
import java.util.List;

@Dao
public interface SimpleTaskDao {

    @Insert
    void insert(SimpleTask simpleTask);

    @Update
    void update(SimpleTask simpleTask);

    @Delete
    void delete(SimpleTask simpleTask);

    @Query("SELECT * FROM simpleTask")
    LiveData<List<SimpleTask>> getAllData();

}
