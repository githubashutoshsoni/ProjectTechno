package com.example.notesjava.addtask;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesjava.R;
import com.example.notesjava.databinding.FragmentAddTaskBinding;
import com.example.notesjava.db.Task;
import com.example.notesjava.task.TaskContract;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class AddTaskFragment extends Fragment implements AddTaskContract.Views {

    FragmentAddTaskBinding binding;
    AddTaskContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);

        if (getActivity() instanceof AddTaskActivity) {

            ((AddTaskActivity) getActivity()).setSupportActionBar(binding.toolBar);

        }

        return binding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.save.setOnClickListener(v -> presenter.saveTask(
                binding.textTitle.getText().toString()
                , binding.textDescription.getText().toString()));

    }

    @Override
    public void showEmptyTaskError() {


        Toast.makeText(getContext(), "can not save task", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTasksList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
        this.presenter = presenter;
    }
}