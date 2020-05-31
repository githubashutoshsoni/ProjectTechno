package com.example.notesjava.addtask;

import com.example.notesjava.BasePresenter;
import com.example.notesjava.BaseView;

public interface AddTaskContract {

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

    }


    interface Views extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        boolean isActive();


    }

}
