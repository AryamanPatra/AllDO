package com.example.alldo.di;

import android.content.Context;

import androidx.room.Room;
import com.example.alldo.data.sources.local.AllDoDatabase;
import com.example.alldo.data.sources.local.SimpleTaskDao;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule{

    @Singleton
    @Provides
    AllDoDatabase provideDatabase(@ApplicationContext Context context){
        return Room.databaseBuilder(context.getApplicationContext(),AllDoDatabase.class,"Tasks.db").build();
    }

    @Provides
    SimpleTaskDao provideSimpleTaskDao(AllDoDatabase database) {
        return database.simpleTaskDao();
    }
}
