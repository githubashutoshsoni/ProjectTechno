package com.example.notesjava.task;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.notesjava.db.Task;
import com.example.notesjava.db.source.TaskDataSource;
import com.example.notesjava.db.source.repositories.TaskRepository;

import java.util.List;


import static com.google.common.base.Preconditions.checkNotNull;

public class TaskPresenter implements TaskContract.Presenter {


    TaskRepository taskRepository;
    TaskContract.Views taskViews;


    public TaskPresenter(TaskRepository taskRepository, TaskContract.Views taskViews) {

        this.taskRepository = checkNotNull(taskRepository, "Task repo can  not be null");
        this.taskViews = checkNotNull(taskViews, "taskViews can not be null");
        this.taskViews.setPresenter(this);

    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (requestCode == TaskFragment.TASK_SAVE_REQUEST_CODE && Activity.RESULT_OK == resultCode) {
            taskViews.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void loadTask(boolean forceUpdate) {

        loadTask(forceUpdate || mFirstTime, true);
        mFirstTime = false;

    }

    private boolean mFirstTime = true;
    private static final String TAG = "TaskPresenter";

    private void loadTask(boolean forceUpdate, boolean showLoading) {

        taskViews.setLoadingIndicator(showLoading);

        if (forceUpdate)
            taskRepository.refreshTask();

        taskRepository.getTasks(new TaskDataSource.LoadTaskCallBack() {
            @Override
            public void onTaskLoaded(List<Task> tasks) {


                taskViews.showTasks(tasks);
                if (tasks.isEmpty()) {
                    taskViews.showNoTasks();
                }


            }

            @Override
            public void onDataFailed() {

                Log.d(TAG, "onDataFailed: ");
                taskViews.showLoadingTasksError();
            }
        });

    }


    @Override
    public void addNewTask() {
        taskViews.showAddTask();
    }

    @Override
    public void openTaskDetails(@NonNull Task requestedTask) {

    }

    @Override
    public void completeTask(@NonNull Task task) {

        checkNotNull(task, "task can not be null");
        taskRepository.completeTask(task);
        loadTask(false, false);

    }

    @Override
    public void activeTask(@NonNull Task task) {
        checkNotNull(task, "task can not be null");
        taskRepository.activeTask(task);
        loadTask(false, false);
    }

    @Override
    public void setFiltering() {

    }

    @Override
    public void deleteCompletedTasks() {
        taskRepository.clearAllCompletedTasks();
        loadTask(false, false);
    }

    @Override
    public void deleteAllTasks() {

        taskRepository.deleteAllTasks();
        taskViews.deleteAllSuccessfully();
        loadTask(false, false);

    }

    @Override
    public void start() {
        loadTask(false);
    }
}
