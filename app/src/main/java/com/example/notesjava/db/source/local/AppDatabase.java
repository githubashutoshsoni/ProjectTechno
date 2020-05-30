package com.example.notesjava.db.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notesjava.db.Settings;
import com.example.notesjava.db.Task;


@Database(entities = {Settings.class, Task.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TaskDao taskDao();



    private static final Object sLock = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "Tasks.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
