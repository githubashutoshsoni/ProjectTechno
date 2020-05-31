package com.example.notesjava.db.source.remote;

import com.example.notesjava.db.Task;
import com.example.notesjava.db.source.TaskDataSource;

/**
 * To handle remote data source transaction and to handle server side transactions.
 */
public class TaskRemoteDataSource implements TaskDataSource {


    /**
     * @param callBack will be used to get the retrofit server instance and show fetched tasks to the user.
     */

    @Override
    public void getTasks(LoadTaskCallBack callBack) {

    }


    /**
     * @param taskId
     * @param callBack
     */
    @Override
    public void getTask(String taskId, GetTaskCallBack callBack) {

    }

    @Override
    public void refreshTask() {

    }

    /**
     * @param task
     */
    @Override
    public void saveTask(Task task) {

    }

    /**
     * @param task
     */
    @Override
    public void activeTask(Task task) {

    }

    /**
     * @param task
     */
    @Override
    public void completeTask(Task task) {

    }

    @Override
    public void completeTask(String taskId) {

    }

    @Override
    public void clearAllCompletedTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }
}
