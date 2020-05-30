package com.example.notesjava.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.notesjava.R;
import com.example.notesjava.databinding.ActivityMainBinding;
import com.example.notesjava.util.ActivityUtils;
import com.example.notesjava.util.Injection;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getText().toString().equals("Settings")) {
                    Log.d(TAG, "onTabSelected: ");
                } else {
                    FragmentManager fm = getSupportFragmentManager();
                    TaskFragment taskFragment = (TaskFragment) fm.findFragmentById(R.id.container);
                    Log.d(TAG, "onTabSelected: else");
                    if (taskFragment == null) {
                        taskFragment = new TaskFragment();
                        ActivityUtils.addFragmentToActivity(fm, taskFragment, R.id.container);
                        new TaskPresenter(
                                Injection.getTaskRepository(MainActivity.this),

                                taskFragment);
                    }

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
