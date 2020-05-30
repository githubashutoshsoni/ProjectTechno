package com.example.notesjava.db.source.local.DataSource;

import com.example.notesjava.db.Task;
import com.example.notesjava.db.source.TaskDataSource;
import com.example.notesjava.db.source.local.TaskDao;
import com.example.notesjava.util.AppExecutors;

import java.util.List;

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

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = taskDao.getAllTasks();


                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        //the task list is empty and can not be fetched...
                        if (tasks.isEmpty())
                            callBack.onDataFailed();

                        callBack.onTaskLoaded(tasks);
                    }
                });

            }
        });


    }

    @Override
    public void getTask(final String taskId, final GetTaskCallBack callBack) {

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {

                final Task task = taskDao.getTask(taskId);

                if (task != null)
                    appExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onTaskLoaded(task);
                        }
                    });
                else
                    callBack.onDataFailed();

            }
        });

    }

    @Override
    public void refreshTask() {

    }

    @Override
    public void saveTask(final Task task) {

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insert(task);
            }
        });

    }

    @Override
    public void activeTask(final Task task) {
        Runnable activateRunnable = new Runnable() {
            @Override
            public void run() {
                taskDao.updateCompleted(task.getmId(), false);
            }
        };
        appExecutors.diskIO().execute(activateRunnable);

    }

    @Override
    public void completeTask(final Task task) {

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateCompleted(task.getmId(), true);
            }
        });

    }

    @Override
    public void completeTask(final String taskId) {


//       no need remote does it

    }

    @Override
    public void clearAllCompletedTasks() {

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteCompleted();
            }
        });

    }

    @Override
    public void deleteAllTasks() {

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTasks();
            }
        });

    }
}
