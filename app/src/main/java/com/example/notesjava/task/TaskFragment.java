package com.example.notesjava.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notesjava.R;
import com.example.notesjava.addtask.AddTaskActivity;
import com.example.notesjava.databinding.FragmentTaskBinding;
import com.example.notesjava.databinding.NotesItemBinding;
import com.example.notesjava.db.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements TaskContract.Views {

    public TaskFragment() {
        // Required empty public constructor
    }

    FragmentTaskBinding binding;
    TaskContract.Presenter presenter;
    TaskAdapter mAdapter;


    public static final int TASK_SAVE_REQUEST_CODE = 120;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        binding.lvToDoList.setAdapter(mAdapter);

        binding.fabAdd.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), AddTaskActivity.class);
            startActivityForResult(intent, TASK_SAVE_REQUEST_CODE);

        });

        binding.deleteAll.setOnClickListener(v -> presenter.deleteAllTasks());

        return binding.getRoot();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TaskAdapter(new ArrayList<Task>(0), mItemListener);

    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setLoadingIndicator(boolean isActive) {

    }


    @Override
    public void showTasks(List<Task> list) {
        mAdapter.replaceData(list);
        binding.noItemsText.setVisibility(View.GONE);

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


        binding.noItemsText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingTasksError() {
        Toast.makeText(getContext(), R.string.loading_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessfullySavedMessage() {

        Snackbar.make(getView(), "saved items successfully", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showAddTask() {

    }

    @Override
    public void showTaskUi(String taskId) {

    }

    @Override
    public void deleteAllSuccessfully() {

        Toast.makeText(getContext(), "Successfully deleted all transaction", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setPresenter(TaskContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }


    TaskOnClickListener mItemListener = new TaskOnClickListener() {
        @Override
        public void onActiveTaskClick(Task task) {
            presenter.activeTask(task);
        }

        @Override
        public void onCompleteTask(Task task) {
            presenter.completeTask(task);
        }

        @Override
        public void onTaskClick(Task task) {

            //todo show detail task activity of a user

        }
    };

    interface TaskOnClickListener {

        void onActiveTaskClick(Task task);

        void onCompleteTask(Task task);

        void onTaskClick(Task task);

    }

    class TaskAdapter extends BaseAdapter {

        List<Task> taskList;
        TaskOnClickListener taskOnClickListener;

        public TaskAdapter(List<Task> taskList, TaskOnClickListener taskOnClickListener) {
            this.taskList = taskList;
            this.taskOnClickListener = taskOnClickListener;
        }

        public void replaceData(List<Task> taskList) {
            setTaskList(taskList);
            notifyDataSetChanged();
        }

        public void setTaskList(List<Task> taskList) {

            this.taskList = checkNotNull(taskList);
        }

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Task getItem(int position) {
            return taskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            NotesItemBinding binding = NotesItemBinding.inflate(inflater, parent, false);

            final Task task = getItem(position);

            binding.completed.setText(task.getDescription());
            binding.title.setText(task.getTitle());
            binding.completed.setChecked(task.isCompleted());

            binding.completed.setOnClickListener(v -> {
                if (task.isCompleted()) {
                    taskOnClickListener.onCompleteTask(task);
                } else {
                    taskOnClickListener.onActiveTaskClick(task);
                }
            });

            binding.rootView.setOnClickListener(v -> taskOnClickListener.onTaskClick(task));

            return binding.getRoot();
        }
    }


}
