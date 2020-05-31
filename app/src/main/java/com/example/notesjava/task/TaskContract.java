package com.example.notesjava.task;

import androidx.annotation.NonNull;

import com.example.notesjava.BasePresenter;
import com.example.notesjava.BaseView;
import com.example.notesjava.db.Task;

import java.util.List;

public interface TaskContract {

    interface Presenter extends BasePresenter {


        void result(int requestCode, int resultCode);

        void loadTask(boolean forceUpdate);

        void addNewTask();

        void openTaskDetails(@NonNull Task requestedTask);

        void completeTask(@NonNull Task task);

        void activeTask(@NonNull Task task);

        void setFiltering();

        void deleteCompletedTasks();

        void deleteAllTasks();

    }

    interface Views extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isActive);

        void showTasks(List<Task> list);

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoTasks();

        void showLoadingTasksError();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showAddTask();

        void showTaskUi(String taskId);

        void deleteAllSuccessfully();


    }

}
