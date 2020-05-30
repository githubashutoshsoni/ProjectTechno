package com.example.notesjava.db.source.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesjava.db.Task;

import java.util.List;

@Dao
public interface TaskDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    @Query("Select * from task")
    List<Task> getAllTasks();

    @Delete
    void deleteTask(Task task);

    @Query("select * from task where mId=:task")
    Task getTask(String task);

    @Update
    int updateTask(Task task);

    //update tasks if completed
    @Query("update task set completed= :isCompleted where mId=:taskId ")
    void updateCompleted(String taskId, boolean isCompleted);

    //delete all tasks
    @Query("DELETE FROM task")
    void deleteTasks();

    @Query("DELETE FROM task WHERE completed=1")
    int deleteCompleted();

}
