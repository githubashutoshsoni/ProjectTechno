package com.example.notesjava.db.source.local.DataSource;

import com.example.notesjava.db.Task;
import com.example.notesjava.db.source.TaskDataSource;
import com.example.notesjava.db.source.local.TaskDao;
import com.example.notesjava.util.AppExecutors;

import java.util.List;

/**
 * A local database to store and retrieve tasks from the users
 */
public class TaskLocalDataSource implements TaskDataSource {


    private static volatile TaskLocalDataSource INSTANCE;
    private TaskDao taskDao;
    private AppExecutors appExecutors;

    public TaskLocalDataSource(TaskDao taskDao, AppExecutors appExecutors) {

        this.appExecutors = appExecutors;
        this.taskDao = taskDao;
    }

    public static TaskLocalDataSource getINSTANCE(TaskDao taskDao, AppExecutors appExecutors) {

        if (INSTANCE == null) {
            synchronized (TaskLocalDataSource.class) {
                if (INSTANCE == null)
                    INSTANCE = new TaskLocalDataSource(taskDao, appExecutors);
            }
        }

        return INSTANCE;
    }


    @Override
    public void getTasks(final LoadTaskCallBack callBack) {


        appExecutors.diskIO().execute(() -> {
            final List<Task> tasks = taskDao.getAllTasks();

            try {
                appExecutors.mainThread().execute(() -> {
                    callBack.onTaskLoaded(tasks);
                });
            } catch (Exception e) {

                callBack.onDataFailed();

            }


        });


    }

    @Override
    public void getTask(final String taskId, final GetTaskCallBack callBack) {

        appExecutors.diskIO().execute(() -> {

            final Task task = taskDao.getTask(taskId);

            if (task != null)
                appExecutors.mainThread().execute(() -> callBack.onTaskLoaded(task));
            else
                callBack.onDataFailed();

        });

    }

    @Override
    public void refreshTask() {

    }

    @Override
    public void saveTask(final Task task) {

        appExecutors.diskIO().execute(() -> taskDao.insert(task));

    }

    @Override
    public void activeTask(final Task task) {
        Runnable activateRunnable = () -> taskDao.updateCompleted(task.getmId(), false);
        appExecutors.diskIO().execute(activateRunnable);

    }

    @Override
    public void completeTask(final Task task) {

        appExecutors.diskIO().execute(() -> taskDao.updateCompleted(task.getmId(), true));

    }

    @Override
    public void completeTask(final String taskId) {


    }

    @Override
    public void clearAllCompletedTasks() {

        appExecutors.diskIO().execute(() -> taskDao.deleteCompleted());

    }

    @Override
    public void deleteAllTasks() {

        appExecutors.diskIO().execute(() -> taskDao.deleteTasks());

    }
}
