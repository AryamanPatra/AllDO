package com.example.alldo.data.sources.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.alldo.data.models.SimpleTask;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Database(entities = {SimpleTask.class},version = 1,exportSchema = false)
public abstract class AllDoDatabase extends RoomDatabase {

    public SimpleTaskDao simpleTaskDao(){
        return new SimpleTaskDao() {
            @Override
            public Flowable<List<SimpleTask>> observeAll() {
                return null;
            }

            @Override
            public Completable upsert(SimpleTask task) {
                return null;
            }

            @Override
            public Completable upsertAll(List<SimpleTask> taskList) {
                return null;
            }

            @Override
            public Completable updateCompleted(int id, boolean check) {
                return null;
            }

            @Override
            public Completable deleteAll() {
                return null;
            }
        };
    }

}
