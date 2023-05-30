package com.example.alldo.data.sources.local;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;
import com.example.alldo.data.models.SimpleTask;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface SimpleTaskDao {

    @Query("SELECT * FROM simpleTask")
    public Flowable<List<SimpleTask>> observeAll();

    @Upsert
    public Completable upsert(SimpleTask task);

    @Upsert
    public Completable upsertAll(List<SimpleTask> taskList);

    @Query("UPDATE simpleTask SET `check`=:check WHERE `id`=:id")
    public Completable updateCompleted(int id, boolean check);

    @Query("DELETE FROM simpleTask")
    public Completable deleteAll();

}
