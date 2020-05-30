package com.example.notesjava.util;

import android.content.Context;

import com.example.notesjava.db.source.TaskDataSource;
import com.example.notesjava.db.source.TaskRepository;
import com.example.notesjava.db.source.local.AppDatabase;
import com.example.notesjava.db.source.local.DataSource.TaskLocalDataSource;
import com.example.notesjava.db.source.local.TaskDao;
import com.example.notesjava.db.source.remote.TaskRemoteDataSource;

public class Injection {


    public static TaskRepository getTaskRepository(Context context) {

        TaskDao taskDao = AppDatabase.getInstance(context).taskDao();
        AppExecutors appExecutors = new AppExecutors();
        TaskRemoteDataSource remoteDataSource = new TaskRemoteDataSource();
        TaskLocalDataSource localDataSource = new TaskLocalDataSource(taskDao, appExecutors);
        TaskRepository taskRepository = TaskRepository.getInstance(localDataSource, remoteDataSource);

        return taskRepository;

    }

}
