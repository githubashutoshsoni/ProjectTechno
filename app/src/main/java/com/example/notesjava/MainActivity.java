package com.example.notesjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.example.notesjava.databinding.ActivityMainBinding;
import com.example.notesjava.settings.SettingsFragment;
import com.example.notesjava.settings.SettingsPresenter;
import com.example.notesjava.task.TaskFragment;
import com.example.notesjava.task.TaskPresenter;
import com.example.notesjava.util.ActivityUtils;
import com.example.notesjava.util.Injection;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    SettingsFragment settingsFragment;
    public static final String SETTINGS_TAG = "SETTINGS";
    public static final String TASK_TAG = "TASK";
    boolean firstTime = true;
    boolean onTaskTab = true;
    final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar);


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getText().toString().equals("Settings")) {


                    startSettingsFragment();

                } else {

                    startTaskFragment();


                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (firstTime) {
            startTaskFragment();
            firstTime = false;
        }


        if (savedInstanceState != null) {

            onTaskTab = savedInstanceState.getBoolean(ON_WHICH_TAB);
            if (onTaskTab)
                startTaskFragment();
            else
                startSettingsFragment();

        }

    }

    public static final String ON_WHICH_TAB = "ON_WHICH_TAB";


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putBoolean(ON_WHICH_TAB, onTaskTab);
    }

    void startTaskFragment() {
        ActivityUtils.removeALlFragments(fm);
        onTaskTab = true;
        TaskFragment taskFragment = new TaskFragment();
        ActivityUtils.addFragmentToActivity(fm, taskFragment, R.id.container, TASK_TAG);
        new TaskPresenter(
                Injection.getTaskRepository(MainActivity.this),
                taskFragment);
    }

    void startSettingsFragment() {

        ActivityUtils.removeALlFragments(fm);
        onTaskTab = false;
        settingsFragment = new SettingsFragment();
        ActivityUtils.addFragmentToActivity(fm, settingsFragment, R.id.container, SETTINGS_TAG);
        new SettingsPresenter(Injection.getLocalDataSource(MainActivity.this), settingsFragment);

    }

}
