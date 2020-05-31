package com.example.notesjava.addtask;

import com.example.notesjava.db.Task;
import com.example.notesjava.db.source.TaskDataSource;

public class AddTaskPresenter implements AddTaskContract.Presenter {


    AddTaskContract.Views views;

    TaskDataSource taskDataSource;


    public AddTaskPresenter(AddTaskContract.Views views, TaskDataSource taskDataSource) {
        this.views = views;
        this.taskDataSource = taskDataSource;
        this.views.setPresenter(this);
    }

    @Override
    public void saveTask(String title, String description) {


        if (title.isEmpty() || description.isEmpty()) {
            views.showEmptyTaskError();
        } else {

            taskDataSource.saveTask(new Task(title, description));
            views.showTasksList();
        }

    }

    @Override
    public void start() {

    }
}
