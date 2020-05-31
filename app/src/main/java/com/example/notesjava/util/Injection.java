package com.example.notesjava.util;

import android.content.Context;

import com.example.notesjava.db.source.local.DataSource.SettingLocalDataSource;
import com.example.notesjava.db.source.local.SettingsDao;
import com.example.notesjava.db.source.repositories.TaskRepository;
import com.example.notesjava.db.source.local.AppDatabase;
import com.example.notesjava.db.source.local.DataSource.TaskLocalDataSource;
import com.example.notesjava.db.source.local.TaskDao;
import com.example.notesjava.db.source.remote.TaskRemoteDataSource;

/*
 *
 * Basic Injection that provides instances of repository and data source to fragments hosting
 *
 *
 * */
public class Injection {


    public static TaskRepository getTaskRepository(Context context) {

        TaskDao taskDao = AppDatabase.getInstance(context).taskDao();
        AppExecutors appExecutors = new AppExecutors();
        TaskRemoteDataSource remoteDataSource = new TaskRemoteDataSource();
        TaskLocalDataSource localDataSource = TaskLocalDataSource.getINSTANCE(taskDao, appExecutors);
        TaskRepository taskRepository = TaskRepository.getInstance(localDataSource, remoteDataSource);
        return taskRepository;

    }


    public static SettingLocalDataSource getLocalDataSource(Context context) {
        SettingsDao settingsDao = AppDatabase.getInstance(context).settingsDao();
        AppExecutors appExecutors = new AppExecutors();
        SettingLocalDataSource localDataSource = SettingLocalDataSource.newInstance(appExecutors, settingsDao);
        return localDataSource;

    }


}
