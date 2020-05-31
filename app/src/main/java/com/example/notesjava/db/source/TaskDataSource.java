package com.example.notesjava.db.source;

import com.example.notesjava.db.Task;

import java.util.List;

public interface TaskDataSource {


    interface LoadTaskCallBack {

        void onTaskLoaded(List<Task> tasks);

        void onDataFailed();
    }

    //not be using this because we are not implementing remote task repository
    interface GetTaskCallBack {


        void onTaskLoaded(Task task);

        void onDataFailed();

    }

    void getTasks(LoadTaskCallBack callBack);

    void getTask(String taskId, GetTaskCallBack callBack);

    void refreshTask();

    void saveTask(Task task);

    void activeTask(Task task);

    void completeTask(Task task);

    void completeTask(String taskId);

    void clearAllCompletedTasks();

    void deleteAllTasks();


}
