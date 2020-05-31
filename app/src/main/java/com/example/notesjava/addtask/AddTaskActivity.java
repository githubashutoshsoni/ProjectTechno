package com.example.notesjava.addtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.notesjava.R;
import com.example.notesjava.databinding.ActivityAddTaskBinding;
import com.example.notesjava.util.ActivityUtils;
import com.example.notesjava.util.Injection;

public class AddTaskActivity extends AppCompatActivity {


    ActivityAddTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fm = getSupportFragmentManager();
        AddTaskFragment addTaskFragment = (AddTaskFragment) fm.findFragmentById(R.id.container);

        if (addTaskFragment == null) {
            addTaskFragment = new AddTaskFragment();
            ActivityUtils.addFragmentToActivity(fm, addTaskFragment, R.id.container,addTaskFragment.getTag());
            new AddTaskPresenter(addTaskFragment, Injection.getTaskRepository(this));
        }

    }
}