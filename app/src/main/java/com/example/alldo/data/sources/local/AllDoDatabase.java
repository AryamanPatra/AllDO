package com.example.alldo.data.sources.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.alldo.R;
import com.example.alldo.data.models.SimpleTask;


@Database(entities = {SimpleTask.class},version = 2,exportSchema = false)
public abstract class AllDoDatabase extends RoomDatabase {
    private static AllDoDatabase instance;

    public abstract SimpleTaskDao simpleTaskDao();
    public static synchronized AllDoDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext()
            ,AllDoDatabase.class,context.getResources().getString(R.string.database_name)).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
