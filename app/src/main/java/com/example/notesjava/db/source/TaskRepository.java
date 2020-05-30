package com.example.notesjava.db.source;

import com.example.notesjava.db.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class TaskRepository implements TaskDataSource {

    private static TaskRepository INSTANCE = null;

    private final TaskDataSource localDataSource;

    private final TaskDataSource remoteDataSource;


    Map<String, Task> mCachedTask;

    boolean mCacheIsDirty;

    public TaskRepository(TaskDataSource localDataSource, TaskDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static TaskRepository getInstance(TaskDataSource localDataSource, TaskDataSource remoteDataSource) {

        if (INSTANCE == null) {

            INSTANCE = new TaskRepository(localDataSource, remoteDataSource);

        }

        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getTasks(final LoadTaskCallBack callBack) {

        if (mCachedTask != null && !mCacheIsDirty) {
            callBack.onTaskLoaded(new ArrayList<>(mCachedTask.values()));
            return;
        }

        if (mCacheIsDirty) {
            getTasksFromRemote(callBack);
        } else {
            localDataSource.getTasks(new LoadTaskCallBack() {
                @Override
                public void onTaskLoaded(List<Task> tasks) {

                    refreshCache(tasks);
                    callBack.onTaskLoaded(new ArrayList<Task>(mCachedTask.values()));

                }

                @Override
                public void onDataFailed() {

                    callBack.onDataFailed();

                }
            });
        }

    }


    void getTasksFromRemote(final LoadTaskCallBack callBack) {

        remoteDataSource.getTasks(new LoadTaskCallBack() {
            @Override
            public void onTaskLoaded(List<Task> tasks) {


                refreshCache(tasks);
                refreshLocalDatabase(tasks);
                callBack.onTaskLoaded(tasks);

            }

            @Override
            public void onDataFailed() {

                callBack.onDataFailed();
            }
        });

    }

    void refreshCache(List<Task> tasks) {

        if (mCachedTask == null)
            mCachedTask = new HashMap<>();
        mCachedTask.clear();

        for (Task task : tasks) {
            mCachedTask.put(task.getmId(), task);
        }

        mCacheIsDirty = false;

    }

    void refreshLocalDatabase(List<Task> tasks) {
        localDataSource.deleteAllTasks();
        for (Task task : tasks
        ) {
            localDataSource.saveTask(task);
        }

    }

    @Override
    public void getTask(String taskId, GetTaskCallBack callBack) {

        checkNotNull(taskId, "Task id can not be null");
        checkNotNull(callBack, "callback canote be null");
        localDataSource.getTask(taskId, callBack);

    }

    @Override
    public void refreshTask() {
        mCacheIsDirty = true;
    }

    @Override
    public void saveTask(Task task) {
        localDataSource.saveTask(task);
        remoteDataSource.saveTask(task);
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.put(task.getmId(), task);

    }

    @Override
    public void activeTask(Task task) {

        localDataSource.activeTask(task);
    }

    @Override
    public void completeTask(Task task) {
        localDataSource.completeTask(task);
    }

    @Override
    public void completeTask(String taskId) {

    }

    @Override
    public void clearAllCompletedTasks() {
        localDataSource.clearAllCompletedTasks();
    }

    @Override
    public void deleteAllTasks() {
        localDataSource.deleteAllTasks();
    }
}
