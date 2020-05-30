package com.example.notesjava.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.notesjava.R;
import com.example.notesjava.db.Task;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements TaskContract.Views {

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }


    @Override
    public void setLoadingIndicator(boolean isActive) {

    }

    @Override
    public void getList(List<Task> list) {

    }

    @Override
    public void showActiveFilterLabel() {

    }

    @Override
    public void showCompletedFilterLabel() {

    }

    @Override
    public void showAllFilterLabel() {

    }

    @Override
    public void showNoTasks() {

    }

    @Override
    public void showLoadingTasksError() {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showAddTask() {

    }

    @Override
    public void showTaskUi(String taskId) {

    }
}
